package com.jbrown.ui.robo;

public enum Event {
	CLICK("click"),
	DOUBLE_CLICKED("double_click"),
	RIGHT_CLICKED("right_click"),
	KEY_TYPED("key_type");
	
	private String _name;
	
	Event(String name){
		_name = name;
	}
	
	boolean typeOf(Event e) {
		for (Event event : this.values()) {
			if (event.getName().equals(e.getName())) {
				return true;
			}
		}
		
		return false;
	}
	
	public Event findEvent(String name) {
		for (Event event : this.values()) {
			if (event.getName().equalsIgnoreCase(name)) {
				return event;
			}
		}

		return null;
	}
	
	private String getName(){
		return _name;
	}
}
