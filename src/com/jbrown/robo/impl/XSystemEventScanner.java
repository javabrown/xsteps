package com.jbrown.robo.impl;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.util.Deque;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentLinkedDeque;

import sun.security.x509.KeyIdentifier;

import com.jbrown.robo.KeysI;
import com.jbrown.robo.XEventI;
import com.jbrown.robo.XScenarioI;
import com.sun.xml.internal.bind.annotation.XmlLocation;

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
	
	//Ignorable local event
	private XStepLocalEventScanner _localEventScanner;
	private LocalEventObserver _localEventObserver;
	
	public XSystemEventScanner(){
		_events = null;
		_keyListener = null;
		_mouseListener = null;
		_isScanRunning = false;
		_liveXEventQueue = new ConcurrentLinkedDeque<XEventI>();	
		
		_localEventScanner = new XStepLocalEventScanner();
		_localEventObserver = new LocalEventObserver();
		_localEventScanner.addObserver(_localEventObserver);
	}
	
	private void lazyInit(){
		if(_events == null){
			_events = new EventRecorder(this.getXScenarioEntry(), _localEventObserver);
			_keyListener = new GlobalKeyListener();
			_mouseListener = new GlobalMouseListener();
		}
	}
	
	void startListener() {
		this.lazyInit();
		_keyListener.addKeyListener(_events);
		_mouseListener.addMouseListener(_events);
		_isScanRunning = true;
		
		_localEventScanner.start(); //start ignorable local event on xstep frame
	}

	void stopListener() {
		_keyListener.removeKeyListener(_events);
		_mouseListener.removeMouseListener(_events);
		_isScanRunning = false;
		
		_localEventScanner.stop();//stop ignorable local event on xstep frame
	}
	
	protected boolean isScanRunning(){
		return _isScanRunning;
	}
	
	XEventI getLiveStatus(){
		 return _liveXEventQueue.poll();
	}
	
    protected abstract XScenarioI getXScenarioEntry();
 
    private class EventRecorder implements KeyListener, MouseListener {
    	private XScenarioI _xScenario;
    	private LocalEventObserver _localIgnorableEvent;
    	
    	public EventRecorder(XScenarioI xScenario, LocalEventObserver ignorableEvent){
    		_xScenario = xScenario;
    		_localIgnorableEvent = ignorableEvent;
    	}
    	
    	private void addEvent(XEventI eventI) {
    		if(_localIgnorableEvent.isLocalEvent()){ //When any event triggered on XStep UI
    			System.out.println("Ignored Local Event Capture.");
    			return;
    		}
    		
    		_xScenario.addEvent(eventI);
			_liveXEventQueue.add(eventI);
			//System.out.println("XScanner=" + eventI);
    	}
    	
		@Override
		public void mouseMoved(MouseEvent event) {
			//System.out.println(event);Toolkit.getDefaultToolkit().beep();
			XEventI eventI = getMouseEvent(event, EventE.MOUSE_MOVE);
			this.addEvent(eventI);
		}

		@Override
		public void mousePressed(MouseEvent event) {
			//System.out.println(event);Toolkit.getDefaultToolkit().beep();
			XEventI eventI = getMouseEvent(event, EventE.MOUSE_PRESS);
			this.addEvent(eventI);
		}

		@Override
		public void mouseReleased(MouseEvent event) {
			//System.out.println(event);
			XEventI eventI = getMouseEvent(event, EventE.MOUSE_RELEASE);
			this.addEvent(eventI);
		}

		@Override
		public void keyPressed(KeyEvent event) {
			//System.out.println(event);
			XEventI eventI = getKeyEvent(event, EventE.KEY_PRESSED);
			this.addEvent(eventI);
		}

		@Override
		public void keyReleased(KeyEvent event) {
			//System.out.println(event);
			XEventI eventI = getKeyEvent(event, EventE.KEY_RELEASE);
			this.addEvent(eventI);
		}
		
		private XEventI getMouseEvent(MouseEvent mouseEvent, EventE eventE) {
			XEventI xEvent = new XMouseEvent(mouseEvent.getButton(),
					mouseEvent.getButtons(), mouseEvent.getX(), mouseEvent.getY(), eventE);
			return xEvent;
		}
		
		private XEventI getKeyEvent(KeyEvent keyEvent, EventE eventE) {
			int keyCode = keyEvent.getVirtualKeyCode();
 
			XEventI xEvent = new XKeyEvent(keyCode, keyEvent.isAltPressed(), keyEvent.isCtrlPressed(),
					keyEvent.isShiftPressed(), eventE);
			
			return xEvent;
		}
    }
    
    
}

class PauseCommandWatcher {
	int maxKeyCombindation;
	int i;
	int[] keyCombination;

	public PauseCommandWatcher() {
		i = 0;
		maxKeyCombindation = KeysI.RECORDING_PAUSE_KEY_COMBINATION.length;
		keyCombination = new int[maxKeyCombindation];
	}

	void addCommand(int keyCode) {
		i++;
		if (i > 2) {
			i = 0;
			keyCombination = new int[maxKeyCombindation];
		}
		keyCombination[i] = keyCode;
	}

	boolean isPauseCommand() {
		boolean match = keyCombination.length == KeysI.RECORDING_PAUSE_KEY_COMBINATION.length;
		match &= keyCombination.equals(KeysI.RECORDING_PAUSE_KEY_COMBINATION);

		return match;
	}
}


