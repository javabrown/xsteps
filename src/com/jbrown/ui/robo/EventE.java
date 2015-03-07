package com.jbrown.ui.robo;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public enum EventE {
	MOUSE_MOVE("mouseMoved"),
	MOUSE_PRESS("mousePressed"),
	MOUSE_RELEASE("mouseReleased"),
	KEY_PRESSED("keyPressed"), 
	KEY_RELEASE("keyReleased");

	private String _name;
	
	EventE(String name) {
		_name = name;
	}
	
	public boolean typeOf(EventE e) {
		if (this.getName().equalsIgnoreCase(e.getName())) {
			return true;
		}

		return false;
	}
	
	public EventE findEvent(String name) {
		for (EventE event : this.values()) {
			if (event.getName().equalsIgnoreCase(name)) {
				return event;
			}
		}

		return null;
	}
	
	public void trigger(BrownRobot r, XEvent event){
		EventE eventE = event.getEvent();
		
		if(eventE.typeOf(MOUSE_MOVE)){
			XMouseEvent mouse = ((XMouseEvent)event);
			r.mouseMove(mouse.getX(), mouse.getY());
			return;
		}
		
		if(eventE.typeOf(MOUSE_PRESS)){
			r.mousePress(((XMouseEvent)event).getButton());
			return;
		}
		
		if(eventE.typeOf(MOUSE_RELEASE)){
			r.mouseRelease(((XMouseEvent)event).getButton());
			return;
		}
		
		if(eventE.typeOf(KEY_PRESSED)){
			r.doKeyPress(((XKeyEvent)event));
			return;
		}
		
		if(eventE.typeOf(KEY_RELEASE)){
			r.doKeyRelease(((XKeyEvent)event));
			return;
		}
	}
	
	private String getName(){
		return _name;
	}
}
