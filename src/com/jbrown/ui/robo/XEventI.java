package com.jbrown.ui.robo;

import java.awt.Robot;

public interface XEventI {
	EventE getEvent();
	BrownSpot getBrownSpot();
	void trigger(Robot r);
}
