package com.mthwate.conk.state;

import com.mthwate.conk.world.Dimension;
import com.mthwate.conk.world.DimensionStore;
import com.mthwate.datlib.math.vector.Vector3i;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author mthwate
 */
public class WorldUpdateAppState extends TimedAppState {

	private final Dimension dim = DimensionStore.getDimension();//TODO remove this

	private static final int RADIUS = 3;//TODO remove this

	private final Map<String, Vector3i> cache = new HashMap<>();

	private static final Logger log = LoggerFactory.getLogger(WorldUpdateAppState.class);

	private final ExecutorService executor = Executors.newSingleThreadExecutor();

	private Future<Queue<WorldUpdate>> future;

	private long lastTime = 0;

	@Override
	public void timedUpdate(float tpf) {
		if (future == null) {
			statistics.set("futureValue", false);
			long now = System.nanoTime();
			future = executor.submit(new WorldUpdateProcessor(cache, dim, lastTime, RADIUS));
			lastTime = now;
		} else {
			statistics.set("futureValue", true);
			if (future.isDone()) {
				try {
					Queue<WorldUpdate> updates = future.get();
					statistics.set("updates", updates.size());
					for (WorldUpdate update : updates) {
						update.getConnection().send(update.getMessage());
					}
					future = null;
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	protected long getMaxTime() {
		return 1000000;
	}

}
