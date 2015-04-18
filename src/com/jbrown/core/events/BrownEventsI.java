package com.jbrown.core.events;

import com.jbrown.observers.BrownObserverI;

public interface BrownEventsI {
	void setEnable(boolean flag);
	boolean isEnabled();
	void addEventObservable(BrownObserverI eventObserver);
	void removeEventObservable(BrownObserverI eventObserver);
}