package com.jbrown.core.events;

import java.util.Observable;
import java.util.Observer;

import com.jbrown.observers.BrownObserverI;
import com.jbrown.robo.XEventI;
import com.jbrown.util.BrownLogger;

public abstract class BrownEvents extends Observable implements BrownEventsI {
	protected boolean _isEnabled;
	@Override
	public void addEventObservable(BrownObserverI eventObserver){
		super.addObserver(eventObserver);
	}
	
	@Override
	public void removeEventObservable(BrownObserverI eventObserver){
		super.deleteObserver(eventObserver);
	}
	
	@Override
	public boolean isEnabled(){
		return _isEnabled;
	}
	
	@Override
	public void setEnable(boolean flag) {
		_isEnabled = flag;
		this.enableNativeListeners(flag);
		
		BrownLogger.logf("Native Listeners ? %s", 
				flag ? "started" : "stopped");
	}
	
	
	void notifyListeners(XEventI event) {
		setChanged();
		notifyObservers(event);
	}
	
	abstract void enableNativeListeners(boolean flag);
}
