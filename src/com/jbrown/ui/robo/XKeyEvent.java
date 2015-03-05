package com.jbrown.ui.robo;

import java.awt.Robot;

import de.ksquared.system.keyboard.KeyEvent;
import de.ksquared.system.mouse.MouseEvent;

public class XKeyEvent extends XEvent {
	private int _keyCode;

	public XKeyEvent(int keyCode, EventE eventE){
		super(new BrownSpot(-1, -1, -1), eventE);
		_keyCode = keyCode;
	}
	
	public int getKeyCode(){
		return _keyCode;
	}
}
