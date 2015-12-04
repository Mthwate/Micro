package com.mthwate.conk.block;

import com.mthwate.conk.command.Exec;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author mthwate
 */
public class BlockStore {

	private static final Map<String, Block> blocks = Collections.synchronizedMap(new HashMap<String, Block>());

	private static final Logger log = LoggerFactory.getLogger(BlockStore.class);

	private synchronized static void init() {
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

	private synchronized static void tryInit() {
		if (blocks.isEmpty()) {
			init();
		}
	}

	public synchronized static Block getBlock(String name) {
		tryInit();
		return blocks.get(name);
	}

	public  synchronized static List<Block> getAllBlocks() {
		tryInit();
		List<Block> blockList = new ArrayList<>();
		for (Block block : blocks.values()) {
			blockList.add(block);
		}
		return blockList;
	}

}
