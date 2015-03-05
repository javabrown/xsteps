package com.jbrown.ui.robo;

import java.awt.Robot;

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
		return _button;
	}
	
	public int getButtons(){
		return _buttons;
	}
}
