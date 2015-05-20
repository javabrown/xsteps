package com.jbrown.robo.impl;
 
import java.util.Arrays;
import java.util.Deque;
import java.util.Observable;
import java.util.concurrent.ConcurrentLinkedDeque;

import com.jbrown.core.events.BrownEventFactory;
import com.jbrown.core.events.BrownEventsI;
import com.jbrown.observers.BrownObserverI;
import com.jbrown.observers.LocalEventObserver;
import com.jbrown.robo.KeysI;
import com.jbrown.robo.XEventI;
import com.jbrown.robo.XScenarioI;
import com.jbrown.ui.XStepOperatorCommandWatch;

import de.ksquared.system.keyboard.GlobalKeyListener;
import de.ksquared.system.keyboard.KeyEvent;
import de.ksquared.system.keyboard.KeyListener;
import de.ksquared.system.mouse.GlobalMouseListener;
import de.ksquared.system.mouse.MouseEvent;
import de.ksquared.system.mouse.MouseListener;

public abstract class XSystemEventScanner0 {
	private EventRecorder0 _events;
	private boolean _isScanRunning;
	private Deque<XEventI> _liveXEventQueue;

	// Ignorable local event
	//private XStepLocalEventScanner _localEventScanner;
	//private LocalEventObserver _localEventObserver;

	// Operator command
	//private XStepOperatorCommandWatch _operatorWatch;

	
	private BrownEventsI _brownEvents;
	
	public XSystemEventScanner0(XScenarioI scenarioStorage) {
		_isScanRunning = false;
		_liveXEventQueue = new ConcurrentLinkedDeque<XEventI>();
		//_operatorWatch = new XStepOperatorCommandWatch();

		//_localEventScanner = new XStepLocalEventScanner();
		//_localEventObserver = new LocalEventObserver();
		//_localEventScanner.addObserver(_localEventObserver);
		
		_brownEvents = BrownEventFactory.getInstance().getAlternateEventHandler();
		
		_events = new EventRecorder0(scenarioStorage);
	}
 

	void startListener() {
		if(!_isScanRunning){
			_isScanRunning = true;
			//_localEventScanner.start(); // start ignorable local event on xstep frame
			_brownEvents.addEventObservable(_events);
			_brownEvents.setEnable(true);
		}
	}

	void stopListener() {
		_brownEvents.setEnable(false);
		_isScanRunning = false;
		//_localEventScanner.stop();// stop ignorable local event on xstep frame
		_brownEvents.removeEventObservable(_events);
	}

	protected boolean isScanRunning() {
		return _isScanRunning;
	}

	XEventI getLiveStatus() {
		return _liveXEventQueue.poll();
	}

//	XStepOperatorCommandWatch getOperatorCommand() {
//		return _operatorWatch;
//	}
 
	
	class EventRecorder0 implements BrownObserverI {
		private XScenarioI _xScenario;
		//private LocalEventObserver _localIgnorableEvent;

		public EventRecorder0(XScenarioI xScenario) {
			_xScenario = xScenario;
			//_localIgnorableEvent = ignorableEvent;
		}

		private void addEvent(XEventI eventI) {
			//if (_localIgnorableEvent.isLocalEvent()) {
			//	System.out.println("Ignored Local Event/paused event Capture.");
			//	return;
			//}

			_xScenario.addEvent(eventI);
			_liveXEventQueue.add(eventI);
			System.out.println("XScanner=" + eventI);
		}
		
		public void update(Observable o, Object arg) {
			if (arg instanceof XEventI) {
				addEvent((XEventI) arg);
			} else {
				System.out.println("Unknown Observer..");
			}
		}
	}
}

