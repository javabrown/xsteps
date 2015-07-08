package com.jbrown.robo.impl;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import com.jbrown.core.events.BrownEventFactory;
import com.jbrown.core.events.BrownEventsI;
import com.jbrown.observers.BrownObserverI;
import com.jbrown.observers.LocalEventObserver;
import com.jbrown.robo.KeysI;
import com.jbrown.robo.XEventI;
import com.jbrown.robo.XScenarioI;
import com.jbrown.ui.XDialog;
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

	private BrownEventsI _brownEvents;

	public XSystemEventScanner0(XScenarioI scenarioStorage) {
		_isScanRunning = false;
		_liveXEventQueue = new ConcurrentLinkedDeque<XEventI>();
		_brownEvents = BrownEventFactory.getInstance()
				.getAlternateEventHandler();

		_events = new EventRecorder0(scenarioStorage);
	}

	void startListener() {
		if (!_isScanRunning) {
			_isScanRunning = true;
			_brownEvents.addEventObservable(_events);
			_brownEvents.setEnable(true);
			
			XDialog.setTitle("XStep Recording On");
			XDialog.start();
		}
	}

	void stopListener() {
		_brownEvents.setEnable(false);
		_isScanRunning = false;
		_brownEvents.removeEventObservable(_events);
		XDialog.setTitle("XStep Recording Stopped");
		XDialog.stop();
	}

	protected boolean isScanRunning() {
		return _isScanRunning;
	}

	XEventI getLiveStatus() {
		return _liveXEventQueue.poll();
	}

	class EventRecorder0 implements BrownObserverI {
		private XScenarioI _xScenario;
		private XStepOperatorCommandWatch _commandWatch;
		
		public EventRecorder0(XScenarioI xScenario) {
			_xScenario = xScenario;
			_commandWatch = new XStepOperatorCommandWatch();
		}

		private void addEvent(XEventI event) {
			_commandWatch.addCommand(event);
			
			if(_commandWatch.isPauseCommand()){
				XDialog.showMsg("Pause", "Pause Command Detected!!");
			}
			else {
				addEvent0(event);
			}
		}
		
		private void addEvent0(XEventI eventI) {
			_xScenario.addEvent(eventI);
			_liveXEventQueue.add(eventI);
			//System.out.println("XScanner=" + eventI);
			XDialog.setTitle(eventI.getEvent().name());
		}

		public void update(Observable o, Object arg) {
			if (arg instanceof XEventI) {
				addEvent((XEventI) arg);
			} else {
				System.out.println("Unknown Observer..");
			}
		}
	}
	

//	class EventEntryPipe {
//		private Queue<XEventI> _queue;
//		private XStepOperatorCommandWatch _watch;
//
//		public EventEntryPipe() {
//			_queue = new LinkedList<XEventI>();
//		}
//
//		public void push(XEventI event) {
//			_queue.add(event);
//
//			if (event instanceof XKeyEvent) {
//				XKeyEvent key = (XKeyEvent) event;
//				if (key.getEvent().typeOf(EventE.KEY_RELEASE)) {
//					_watch.addCommand(key.getKeyCode());
//				}
//			}
//		}
//
//		public XEventI poll() {
//			return _queue.poll();
//		}
//
//		public boolean isPauseCommand() {
//			return _watch.isPauseCommand();
//		}
//	}
}
