package com.jbrown.util;

public enum XStepCommandMode {
	PAUSE("Pause", "Recording Paused"), 
	RESUME_PAUSE("Resume Pause", "Recording On"),
	RECORD("Record", "Recording On"), 
	NONE("None", "");

	String _name;
	String _desc;
	
	XStepCommandMode(String name, String desc) {
		_name = name;
		_desc = desc;
	}

	public String getName() {
		return _name;
	}
	
	public String getDesc() {
		return _desc;
	}

	public boolean typeOf(XStepCommandMode command) {
		return command.getName().equalsIgnoreCase(_name);
	}
}
