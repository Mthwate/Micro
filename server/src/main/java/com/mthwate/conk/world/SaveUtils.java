package com.mthwate.conk.world;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jme3.math.Vector3f;
import com.jme3.network.HostedConnection;
import com.mthwate.conk.PropUtils;
import com.mthwate.conk.block.Block;
import com.mthwate.conk.block.BlockStore;
import com.mthwate.conk.user.User;
import com.mthwate.datlib.IOUtils;
import com.mthwate.datlib.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * @author mthwate
 */
public class SaveUtils {

	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	private static final Logger log = LoggerFactory.getLogger(SaveUtils.class);

	private static final File USER_DIR = new File(PropUtils.getUserDir());

	public static Chunk loadChunk(File file) {
		Timer timer = new Timer();
		SaveFormat save = null;
		Reader reader = null;
		try {
			reader = new FileReader(file);
			save = gson.fromJson(reader, SaveFormat.class);
		} catch (FileNotFoundException e) {
			log.warn("Filed to load chunk from {}", file.getPath());
		} finally {
			IOUtils.close(reader);
		}

		Chunk chunk = null;

		if (save != null) {
			chunk = new Chunk();
			for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
				for (int y = 0; y < Chunk.CHUNK_SIZE; y++) {
					for (int z = 0; z < Chunk.CHUNK_SIZE; z++) {
						BlockSaveFormat blockSave = save.blocks[x][y][z];
						if (blockSave != null) {
							Block block = BlockStore.getBlock(blockSave.name);
							chunk.set(block, x, y, z);
						}
					}
				}
			}
		}

		log.info("Loaded chunk from {} in {} ns", file, timer.getNano());

		return chunk;
	}

	public static void saveChunk(Chunk chunk, File file) {
		file.getParentFile().mkdirs();

		SaveFormat save = new SaveFormat();
		for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
			for (int y = 0; y < Chunk.CHUNK_SIZE; y++) {
				for (int z = 0; z < Chunk.CHUNK_SIZE; z++) {
					Block block = chunk.get(x, y, z);
					if (block != null) {
						BlockSaveFormat blockSave = new BlockSaveFormat();
						blockSave.name = block.getName();
						save.blocks[x][y][z] = blockSave;
					}
				}
			}
		}

		Writer writer = null;
		try {
			writer = new FileWriter(file);
			gson.toJson(save, writer);
		} catch (IOException e) {
			log.warn("Failed to save chunk to {}", file.getPath());
		} finally {
			IOUtils.close(writer);
		}
	}

	public static void saveUser(User user) {
		File file = new File(USER_DIR, user.getName());

		USER_DIR.mkdirs();

		Writer writer = null;
		try {
			writer = new FileWriter(file);
			gson.toJson(user.getPosition(), writer);
		} catch (IOException e) {
			log.warn("Failed to save user to {}", file.getPath());
		} finally {
			IOUtils.close(writer);
		}
	}

	public static User loadUser(String username, HostedConnection connection) {
		File file = new File(USER_DIR, username);

		User user = null;

		Vector3f pos = null;
		Reader reader = null;
		try {
			reader = new FileReader(file);
			pos = gson.fromJson(reader, Vector3f.class);
		} catch (FileNotFoundException e) {
			log.warn("Filed to load user from {}", file.getPath());
		} finally {
			IOUtils.close(reader);
		}

		if (pos != null) {
			user = new User(username, connection);
			user.setPosition(pos);
		}

		return user;
	}
}
