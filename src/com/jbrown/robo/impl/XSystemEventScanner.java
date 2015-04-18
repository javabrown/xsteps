package com.jbrown.robo.impl;

import java.util.Arrays;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

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

public abstract class XSystemEventScanner {
	private EventRecorder _events;
	private GlobalKeyListener _keyListener;
	private GlobalMouseListener _mouseListener;
	private boolean _isScanRunning;
	private Deque<XEventI> _liveXEventQueue;

	// Ignorable local event
	private XStepLocalEventScanner _localEventScanner;
	private LocalEventObserver _localEventObserver;

	// Operator command
	private XStepOperatorCommandWatch _operatorWatch;

	public XSystemEventScanner() {
		_events = null;
		_keyListener = null;
		_mouseListener = null;
		_isScanRunning = false;
		_liveXEventQueue = new ConcurrentLinkedDeque<XEventI>();
		_operatorWatch = new XStepOperatorCommandWatch();

		_localEventScanner = new XStepLocalEventScanner();
		_localEventObserver = new LocalEventObserver();
		_localEventScanner.addObserver(_localEventObserver);
	}

	private void lazyInit() {
		if (_events == null) {
			_events = new EventRecorder(this.getXScenarioEntry(),
					_localEventObserver, _operatorWatch);
			_keyListener = new GlobalKeyListener();
			_mouseListener = new GlobalMouseListener();
		}
	}

	void startListener() {
		this.lazyInit();
		_keyListener.addKeyListener(_events);
		_mouseListener.addMouseListener(_events);
		_isScanRunning = true;

		_localEventScanner.start(); // start ignorable local event on xstep
									// frame
	}

	void stopListener() {
		_keyListener.removeKeyListener(_events);
		_mouseListener.removeMouseListener(_events);
		_isScanRunning = false;

		_localEventScanner.stop();// stop ignorable local event on xstep frame
	}

	protected boolean isScanRunning() {
		return _isScanRunning;
	}

	XEventI getLiveStatus() {
		return _liveXEventQueue.poll();
	}

	XStepOperatorCommandWatch getOperatorCommand() {
		return _operatorWatch;
	}

	protected abstract XScenarioI getXScenarioEntry();

	private class EventRecorder implements KeyListener, MouseListener {
		private XScenarioI _xScenario;
		private LocalEventObserver _localIgnorableEvent;

		public EventRecorder(XScenarioI xScenario,
				LocalEventObserver ignorableEvent,
				XStepOperatorCommandWatch operatorWatch) {
			_xScenario = xScenario;
			_localIgnorableEvent = ignorableEvent;
			_operatorWatch = operatorWatch;
		}

		private void addEvent(XEventI eventI) {
			if (_localIgnorableEvent.isLocalEvent()|| _operatorWatch.isPauseCommand()) { // When any event
														// triggered on XStep UI
				System.out.println("Ignored Local Event/paused event Capture.");
				return;
			}

			_xScenario.addEvent(eventI);
			_liveXEventQueue.add(eventI);
			// System.out.println("XScanner=" + eventI);
		}

		@Override
		public void mouseMoved(MouseEvent event) {
			// System.out.println(event);Toolkit.getDefaultToolkit().beep();
			XEventI eventI = getMouseEvent(event, EventE.MOUSE_MOVE);
			this.addEvent(eventI);
		}

		@Override
		public void mousePressed(MouseEvent event) {
			// System.out.println(event);Toolkit.getDefaultToolkit().beep();
			XEventI eventI = getMouseEvent(event, EventE.MOUSE_PRESS);
			this.addEvent(eventI);
		}

		@Override
		public void mouseReleased(MouseEvent event) {
			// System.out.println(event);
			XEventI eventI = getMouseEvent(event, EventE.MOUSE_RELEASE);
			this.addEvent(eventI);
		}

		@Override
		public void keyPressed(KeyEvent event) {
			// System.out.println(event);
			XEventI eventI = getKeyEvent(event, EventE.KEY_PRESSED);
			this.addEvent(eventI);
		}

		@Override
		public void keyReleased(KeyEvent event) {
			// System.out.println(event);
			XEventI eventI = getKeyEvent(event, EventE.KEY_RELEASE);
			this.addEvent(eventI);

			// xcode operator command like pause, add scenario
			_operatorWatch.addCommand(((XKeyEvent) eventI).getKeyCode());
		}

		private XEventI getMouseEvent(MouseEvent mouseEvent, EventE eventE) {
			XEventI xEvent = new XMouseEvent(mouseEvent.getButton(),
					mouseEvent.getButtons(), mouseEvent.getX(),
					mouseEvent.getY(), eventE);
			return xEvent;
		}

		private XEventI getKeyEvent(KeyEvent keyEvent, EventE eventE) {
			int keyCode = keyEvent.getVirtualKeyCode();

			XEventI xEvent = new XKeyEvent(keyCode, keyEvent.isAltPressed(),
					keyEvent.isCtrlPressed(), keyEvent.isShiftPressed(),
					new String(new char[] { (char) keyCode, }), eventE);

			return xEvent;
		}
	}

}
