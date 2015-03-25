package com.jbrown.ui;
 
public class XCommand {
	private String _command;
	private String _shortcut;

	public void setCommand(String command) {
		_command = command;
	}
	
	public String getCommand() {
		return _command;
	}
	
	public void setShortcut(String shortcut) {
		_shortcut = shortcut;
	}
	
	public String getShortcut() {
		return _shortcut;
	}
	
	public boolean typeOf(String command) {
		if (command != null && this.getCommand().equals(command)) {
			return true;
		}

		return false;
	}
}