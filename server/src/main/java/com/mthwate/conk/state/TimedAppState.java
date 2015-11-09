package com.mthwate.conk.state;

import com.jme3.app.state.AbstractAppState;
import com.mthwate.datlib.Timer;

/**
 * @author mthwate
 */
public abstract class TimedAppState extends AbstractAppState {

	@Override
	public void update(float tpf) {
		Timer timer = new Timer();
		timedUpdate(tpf);
		if (timer.getNano() > getMaxTime()) {
			System.out.println(this.getClass() + " : " + timer.getNano());
		}
	}

	public abstract void timedUpdate(float tpf);

	public abstract long getMaxTime();

}
