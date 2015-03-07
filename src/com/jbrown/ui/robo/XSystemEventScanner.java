package com.jbrown.ui.robo;

import java.awt.Toolkit;

import de.ksquared.system.keyboard.GlobalKeyListener;
import de.ksquared.system.keyboard.KeyAdapter;
import de.ksquared.system.keyboard.KeyEvent;
import de.ksquared.system.keyboard.KeyListener;
import de.ksquared.system.mouse.GlobalMouseListener;
import de.ksquared.system.mouse.MouseAdapter;
import de.ksquared.system.mouse.MouseEvent;
import de.ksquared.system.mouse.MouseListener;

public abstract class XSystemEventScanner { 
	private EventRecorder _events;
	private GlobalKeyListener _keyListener;
	private GlobalMouseListener _mouseListener;
	private boolean _isScanRunning; 
	
	public XSystemEventScanner(){
		_events = null;
		_keyListener = null;
		_mouseListener = null;
		_isScanRunning = false;
	}
	
	private void lazyInit(){
		if(_events == null){
			_events = new EventRecorder(this.getXScenarioEntry());
			_keyListener = new GlobalKeyListener();
			_mouseListener = new GlobalMouseListener();
		}
	}
	
	void startListener() {
		this.lazyInit();
		_keyListener.addKeyListener(_events);
		_mouseListener.addMouseListener(_events);
		_isScanRunning = true;
	}

	void stopListener() {
		_keyListener.removeKeyListener(_events);
		_mouseListener.removeMouseListener(_events);
		_isScanRunning = false;
	}
	
	protected boolean isScanRunning(){
		return _isScanRunning;
	}

    protected abstract XScenarioI getXScenarioEntry();
 
    private class EventRecorder implements KeyListener, MouseListener {
    	private XScenarioI _xScenario;
    	
    	public EventRecorder(XScenarioI xScenario){
    		_xScenario = xScenario;
    	}
    	
		@Override
		public void mouseMoved(MouseEvent event) {
			//System.out.println(event);Toolkit.getDefaultToolkit().beep();
			XEventI eventI = getMouseEvent(event, EventE.MOUSE_MOVE);
			_xScenario.addEvent(eventI);
		}

		@Override
		public void mousePressed(MouseEvent event) {
			System.out.println(event);Toolkit.getDefaultToolkit().beep();
			XEventI eventI = getMouseEvent(event, EventE.MOUSE_PRESS);
			_xScenario.addEvent(eventI);
		}

		@Override
		public void mouseReleased(MouseEvent event) {
			System.out.println(event);
			XEventI eventI = getMouseEvent(event, EventE.MOUSE_RELEASE);
			_xScenario.addEvent(eventI);
		}

		@Override
		public void keyPressed(KeyEvent event) {
			System.out.println(event);
			XEventI eventI = getKeyEvent(event, EventE.KEY_PRESSED);
			_xScenario.addEvent(eventI);
		}

		@Override
		public void keyReleased(KeyEvent event) {
			System.out.println(event);
			XEventI eventI = getKeyEvent(event, EventE.KEY_RELEASE);
			_xScenario.addEvent(eventI);
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
    
//    class KeyboardEventScanner implements LowLevelEvents {
//		@Override
//		public XScenarioI getXEvent(final XScenarioI _xScenario) {
//			new GlobalKeyListener().addKeyListener(new KeyAdapter() {
//					public void keyPressed(KeyEvent event) {
//						_xScenario.addEventSequence(null);
//					}
//					
//					public void keyReleased(KeyEvent event) {
//						_xScenario.addEventSequence(null);
//						if(event.getVirtualKeyCode()==KeyEvent.VK_ADD && event.isCtrlPressed()){
//							System.out.println("CTRL+ADD was just released (CTRL is still pressed)");
//						}
//					}
//			});
//			return null;
//		}
//    }
//
//    class MouseEventScanner implements LowLevelEvents {
//		@Override
//		public XScenarioI getXEvent(final XScenarioI _xScenario) {
//			new GlobalMouseListener().addMouseListener(new MouseAdapter(){
//				@Override
//				public void mouseMoved(MouseEvent event) {
//					_xScenario.addEventSequence(null);
//				}
//
//				@Override
//				public void mousePressed(MouseEvent event) {
//					_xScenario.addEventSequence(null);
//				}
//
//				@Override
//				public void mouseReleased(MouseEvent event) {
//					_xScenario.addEventSequence(null);
//				}
//			});
//			return null;
//		}
//    }
}


