package com.jbrown.jni.keystroke;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


class KeyboardHook {
	private boolean _altPressed;
	private boolean _shiftPressed;
	private boolean _ctrlPressed;
	private boolean _extendedKey;

	private EventProcedure _procedure;
	
	private List<KeyEvent> _buffer;
	
	static{
		System.loadLibrary("KeyboardHook"); 
		System.out.println("Hello");
	}
	
	public KeyboardHook() {
		_altPressed = false;
		_shiftPressed = false;
		_ctrlPressed = false;
		_extendedKey = false;
		
		_procedure = new EventProcedure(this);
		
		_buffer = Collections.synchronizedList(new LinkedList<KeyEvent>());
		
		if (Native.load()) {
			_procedure.start();
		}
	}
	
	public KeyEvent popEvent(){
		if(_buffer.isEmpty()){
			return null;
		}
		
		return _buffer.remove(0);
	}

	void processKey(boolean transitionState, int virtualKeyCode,
			GlobalKeyListener listener) {
		processControlKeys(transitionState, virtualKeyCode);
		_buffer.add(new KeyEvent(this, listener, transitionState,
				virtualKeyCode, _altPressed, _shiftPressed, _ctrlPressed,
				_extendedKey));
	}

	native void registerHook(GlobalKeyListener listener);

	native void unregisterHook();

	void processControlKeys(boolean transitionState, int virtualKeyCode) {
		switch (virtualKeyCode) {
		case KeyEvent.VK_RWIN:
			_extendedKey = transitionState;
			break;
		case KeyEvent.VK_RMENU:
			_extendedKey = transitionState;
		case KeyEvent.VK_MENU:
		case KeyEvent.VK_LMENU:
			_altPressed = transitionState;
			break;
		case KeyEvent.VK_RSHIFT:
			_extendedKey = transitionState;
		case KeyEvent.VK_SHIFT:
		case KeyEvent.VK_LSHIFT:
			_shiftPressed = transitionState;
			break;
		case KeyEvent.VK_RCONTROL:
			_extendedKey = transitionState;
		case KeyEvent.VK_CONTROL:
		case KeyEvent.VK_LCONTROL:
			_ctrlPressed = transitionState;
			break;
		}
	}
}

public class PoolHook extends Thread {
	private KeyboardHook _hook;
	private GlobalKeyListener _listener;

	public PoolHook(GlobalKeyListener listener) {
		_listener = listener;
		this.setDaemon(true);
	}

	public void run() {
		_hook = new KeyboardHook();
		_hook.registerHook(_listener);
	}
	
	
	public static void main(String[] args){
		KeyboardHook hook = new KeyboardHook();
		
		while(true){
			System.out.println(hook.popEvent());
		}
	}
}

class EventProcedure extends Thread {
	private KeyboardHook hook;

	EventProcedure(KeyboardHook hook) {
		this.setDaemon(true);
		this.hook = hook;
	}

	@Override
	public void run() {
		while (true) {
			KeyEvent event = hook.popEvent();
			
			if (event != null) {
				GlobalKeyListener listener = event.getGlobalKeyListener();
				
				if (event.getTransitionState()){
					listener.keyPressed(event);
				}
				else{
					listener.keyReleased(event);
				}
			} else
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
	}
}
