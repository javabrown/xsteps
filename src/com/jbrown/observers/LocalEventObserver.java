package com.jbrown.observers;

import java.awt.AWTEvent;
import java.util.Observable;
import java.util.Observer;

public class LocalEventObserver implements Observer {
	private boolean _isLocalEvent;
	private AWTEvent _event;

	@Override
	public void update(Observable o, Object obj) {
		if (obj != null && obj instanceof AWTEvent) {
			_event = (AWTEvent) obj;
			_isLocalEvent = true;
		}
	}

	public boolean isLocalEvent() {
		boolean ret = _isLocalEvent;
		_isLocalEvent = false;
		return ret;
	}
	
	public AWTEvent getLocalEvent(){
		AWTEvent localEvent = _event;
		_event = null;
		return localEvent;
	}
}