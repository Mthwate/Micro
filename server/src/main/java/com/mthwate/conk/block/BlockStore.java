package com.mthwate.conk.block;

import com.mthwate.conk.command.Exec;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author mthwate
 */
public class BlockStore {

	private static final Map<String, Block> blocks = new HashMap<>();

	private static final Logger log = LoggerFactory.getLogger(BlockStore.class);

	private static void init() {
		Reflections reflections = new Reflections();

		Set<Class<? extends Block>> types = reflections.getSubTypesOf(Block.class);

		for (Class<? extends Block> type : types) {
			if (type.isAnnotationPresent(Exec.class)) {
				try {
					Block block = type.newInstance();
					blocks.put(block.getName(), block);
				} catch (ReflectiveOperationException e) {
					log.error("Could not instantiate block {}", type);
				}
			}
		}
	}

	private static void tryInit() {
		if (blocks.isEmpty()) {
			init();
		}
	}

	public static Block getBlock(String name) {
		tryInit();
		Block block = null;
		Class<? extends Block> clazz = blocks.get(name).getClass();
		try {
			 block = clazz.newInstance();
		} catch (ReflectiveOperationException e) {
			log.error("Could not instantiate block {}", clazz);
		}
		return block;
	}

	public static List<Block> getAllBlocks() {
		tryInit();
		List<Block> blockList = new ArrayList<>();
		for (Block block : blocks.values()) {
			Class<? extends Block> clazz = block.getClass();
			try {
				blockList.add(clazz.newInstance());
			} catch (ReflectiveOperationException e) {
				log.error("Could not instantiate block {}", clazz);
			}
		}
		return blockList;
	}

}
