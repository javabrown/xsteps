package com.jbrown.robo.impl;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.util.Observable;

public class XStepLocalEventScanner extends Observable implements AWTEventListener {
	private Toolkit _toolkit; 
	private long _eventMask;
	
	private static XStepLocalEventScanner _instance;
	
	public static XStepLocalEventScanner getInstance(){
		if(_instance == null){
			_instance = new XStepLocalEventScanner();
		}
		
		return _instance;
	}
	
	private XStepLocalEventScanner() {
		_toolkit = Toolkit.getDefaultToolkit();
		_eventMask = AWTEvent.MOUSE_MOTION_EVENT_MASK
				+ AWTEvent.MOUSE_EVENT_MASK + AWTEvent.KEY_EVENT_MASK;
		
	}
	
	public void start(){
		_toolkit.addAWTEventListener(this, _eventMask);
	}
	
	public void stop(){
		_toolkit.removeAWTEventListener(this);
	}
	
	@Override
	public void eventDispatched(AWTEvent e) {
		System.out.println("local-event " + e.toString());
		setChanged();
		notifyObservers(e);
	}
}