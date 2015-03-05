package com.jbrown.ui.robo;

public interface XEventQueueI {
	void addEventSequences(XEventSequenceI sequence);
	XEventSequenceI[] getEventSequences();
}
