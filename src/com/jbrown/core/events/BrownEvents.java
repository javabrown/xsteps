package com.jbrown.core.events;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import com.jbrown.observers.BrownObserverI;
import com.jbrown.robo.KeysI;
import com.jbrown.robo.XEventI;
import com.jbrown.util.BrownLogger;
import com.jbrown.util.BrownProps;

public abstract class BrownEvents extends Observable implements BrownEventsI {
	protected boolean _isEnabled;
	private IgnorableLocalEvents _ignorableEventOnMe;
	
	public BrownEvents(){
		_ignorableEventOnMe = new IgnorableLocalEvents();
		_ignorableEventOnMe.start();
	}
	
	@Override
	public void addEventObservable(BrownObserverI eventObserver){
		_ignorableEventOnMe.start();
		super.addObserver(eventObserver);
	}
	
	@Override
	public void removeEventObservable(BrownObserverI eventObserver){
		super.deleteObserver(eventObserver);
		_ignorableEventOnMe.stop();
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
		if(_ignorableEventOnMe.isIgnorable()){
			System.out.println("This is ignorable event");
			return;
		}
		
		setChanged();
		notifyObservers(event);
	}
	
	abstract void enableNativeListeners(boolean flag);
}

class IgnorableLocalEvents implements AWTEventListener {
	private Toolkit _toolkit; 
	private long _eventMask;
	boolean _isIgnorable;
	
	public IgnorableLocalEvents() {
		_toolkit = Toolkit.getDefaultToolkit();
		_eventMask = AWTEvent.FOCUS_EVENT_MASK +  AWTEvent.MOUSE_EVENT_MASK;
		_isIgnorable = true;
	}
	
	public void start(){
		_toolkit.addAWTEventListener(this, _eventMask);
	}
	
	public void stop(){
		_toolkit.removeAWTEventListener(this);
	}
	
	@Override
	public void eventDispatched(AWTEvent e) {
	 
		if(e instanceof FocusEvent){
			FocusEvent fEvent = (FocusEvent)e;
			
			if(fEvent.getID() == FocusEvent.FOCUS_GAINED){
				_isIgnorable = true;
			}
			
			if(fEvent.getID() == FocusEvent.FOCUS_LOST){
				_isIgnorable = false;
			}
		}
		else if(e instanceof MouseEvent){
			MouseEvent mEvent = (MouseEvent)e;
			boolean flag = BrownProps.instanceOfLauncherOrModuleApp(mEvent.getSource());
			
			if(mEvent.getID() == MouseEvent.MOUSE_ENTERED){
				 if(flag){
					 _isIgnorable = true;
				 }
			}
			
			if(mEvent.getID() == MouseEvent.MOUSE_EXITED){
				 if(flag){
					 _isIgnorable = false;
				 }
			}
		}
	}
	
	public boolean isIgnorable(){
		return _isIgnorable;
	}
}