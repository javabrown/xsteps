package com.jbrown.robo;

import com.jbrown.robo.impl.BrownSpot;

public interface BrownActionI {
	void click(BrownSpot spot);

	void doubleClick(BrownSpot spot);

	void rightClick(BrownSpot spot);

	void typeKey(BrownSpot spot, int[] keyCodes);
}