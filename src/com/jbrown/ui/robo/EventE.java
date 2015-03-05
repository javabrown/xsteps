package com.jbrown.ui.robo;

import java.awt.Robot;

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
		for (EventE event : values()) {
			if (event.getName().equals(e.getName())) {
				return true;
			}
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
	
	public void trigger(Robot r, XEvent event){
		if(this.typeOf(MOUSE_MOVE)){
			XMouseEvent mouse = ((XMouseEvent)event);
			r.mouseMove(mouse.getX(), mouse.getY());
			return;
		}
		
		if(this.typeOf(MOUSE_PRESS)){
			r.mousePress(((XMouseEvent)event).getButton());
			return;
		}
		
		if(this.typeOf(MOUSE_RELEASE)){
			r.mouseRelease(((XMouseEvent)event).getButton());
			return;
		}
		
		if(this.typeOf(KEY_PRESSED)){
			r.keyPress( ((XKeyEvent)event).getKeyCode());
			return;
		}
		
		if(this.typeOf(KEY_RELEASE)){
			r.keyRelease(((XKeyEvent)event).getKeyCode());
			return;
		}
	}
	
	private String getName(){
		return _name;
	}
}
