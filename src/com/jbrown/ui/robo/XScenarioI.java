package com.jbrown.ui.robo;

public interface XScenarioI {
	void addEventSequence(XEventSequence sequence);
	XEventSequence[] getEventSequence();
	boolean hasValidScenario();
}
