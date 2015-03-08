package com.jbrown.robo.impl;

import java.awt.Robot;
import java.awt.event.InputEvent;

import de.ksquared.system.mouse.MouseEvent;

public class XMouseEvent extends XEvent {
	private int _button;
	private int _buttons;
	private int _x;
	private int _y;
	
	public XMouseEvent(int button, int buttons, int x, int y, EventE eventE){
		super(new BrownSpot(x, y, -1), eventE);
		_button = button;
		_buttons = buttons;
		_x = x;
		_y = y;
	}
	
	public int getX(){
		return _x;
	}
	
	public int getY(){
		return _y;
	}
	
	public int getButton(){
		int mouseButtonPresed = -1;
		
		switch(_button){
		case 2: 
			mouseButtonPresed = InputEvent.BUTTON1_MASK;
			break;
		case 4: 
			mouseButtonPresed = InputEvent.BUTTON3_MASK;
			break;
		}
		
		return mouseButtonPresed;
	}
	
	public int getButtons(){
		return _buttons;
	}
}
