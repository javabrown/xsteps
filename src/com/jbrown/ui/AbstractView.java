package com.jbrown.ui;

public abstract class AbstractView extends XTemplate implements XTemplate.XStepView {
	private WindowsSystemTray _windowsProcessTray;
	private AbstractEventManager _eventManager;
	
	public void setSystemTray(WindowsSystemTray windowsProcessTray) {
		_windowsProcessTray = windowsProcessTray;
	}
	
	public WindowsSystemTray getSystemTray() {
		return _windowsProcessTray;
	}
	
	public void setEventManager(AbstractEventManager eventManager) {
		_eventManager = eventManager;
	}
	
	public AbstractEventManager getEventManager() {
		return _eventManager;
	}
}
