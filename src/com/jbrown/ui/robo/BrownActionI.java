package com.jbrown.ui.robo;

interface BrownActionI {
	void click(BrownSpot spot);

	void doubleClick(BrownSpot spot);

	void rightClick(BrownSpot spot);

	void typeKey(BrownSpot spot, int[] keyCodes);
}