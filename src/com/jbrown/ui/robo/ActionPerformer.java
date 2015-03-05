package com.jbrown.ui.robo;

import static java.awt.event.InputEvent.BUTTON1_MASK;
import static java.awt.event.InputEvent.BUTTON2_MASK;

import java.awt.AWTException;
import java.awt.Robot;

public class ActionPerformer extends Robot implements BrownActionI {

	public ActionPerformer() throws AWTException {
		super();
	}

	@Override
	public void click(BrownSpot spot) {
		this.mouseMove(spot.getX(), spot.getY());
		this.mousePress(BUTTON1_MASK);
		this.mouseRelease(BUTTON1_MASK);
	}

	@Override
	public void doubleClick(BrownSpot spot) {
		click(spot);
		click(spot);
	}

	@Override
	public void rightClick(BrownSpot spot) {
		this.mouseMove(spot.getX(), spot.getY());
		this.mousePress(BUTTON2_MASK);
		this.mouseRelease(BUTTON2_MASK);
	}

	@Override
	public void typeKey(BrownSpot spot, int[] keyCodes) {
		click(spot);
		for(int i=0; i<keyCodes.length; i++){
		  this.keyPress(keyCodes[i]);
		}
	}
}
