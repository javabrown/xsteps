package com.jbrown.robo;

import com.jbrown.robo.impl.BrownRobot;

public interface XEventSequenceI {
	int getSequenceNumber();
	XEventI getEvent();
	long getDuration(); 
	void repeat(final BrownRobot r);
}
