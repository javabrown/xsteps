package com.jbrown.robo;

import com.jbrown.robo.impl.XEventSequence;

public interface XScenarioI {
	void addEvent(XEventI event);
	XEventSequence[] getEventSequence();
	boolean hasValidScenario();
	public int nEvent();
	public void resetXScenario();
}
