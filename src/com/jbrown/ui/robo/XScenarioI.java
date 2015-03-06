package com.jbrown.ui.robo;

public interface XScenarioI {
	void addEvent(XEventI event);
	XEventSequence[] getEventSequence();
	boolean hasValidScenario();
	public int nEvent();
}
