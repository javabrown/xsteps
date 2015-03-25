package com.jbrown.robo;

import com.jbrown.robo.impl.BrownRobot;
import com.jbrown.robo.impl.BrownSpot;
import com.jbrown.robo.impl.EventE;

public interface XEventI {
	EventE getEvent();
	BrownSpot getBrownSpot();
	void trigger(BrownRobot r);
	int getGraphMaskValue();
}
