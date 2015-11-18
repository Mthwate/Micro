package com.mthwate.conk.state;

import com.jme3.app.state.AbstractAppState;
import com.mthwate.datlib.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mthwate
 */
public abstract class TimedAppState extends AbstractAppState {

	private static final Logger log = LoggerFactory.getLogger(TimedAppState.class);

	@Override
	public void update(float tpf) {
		Timer timer = new Timer();
		timedUpdate(tpf);
		if (timer.getNano() > getMaxTime()) {
			log.info("Operation performed by {} took longer than expected ({}ns)", this.getClass().getName(), timer.getNano());
		}
	}

	public abstract void timedUpdate(float tpf);

	public abstract long getMaxTime();

}
