package com.jbrown.robo;

public interface XEventQueueI {
	void addEventSequences(XEventSequenceI sequence);
	XEventSequenceI[] getEventSequences();
}
