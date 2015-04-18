package com.jbrown.core.events;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.SwingDispatchService;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

import com.jbrown.robo.XEventI;
import com.jbrown.robo.impl.EventE;
import com.jbrown.robo.impl.XKeyEvent;
import com.jbrown.robo.impl.XMouseEvent;
import com.jbrown.util.BrownLogger;
import com.jbrown.util.BrownStream;

public class PrimeBrownEventListener extends BrownEvents implements NativeKeyListener,
		NativeMouseInputListener, NativeMouseWheelListener {
	@Override
	void enableNativeListeners(boolean flag) {
		_isEnabled = flag;
		this.enableNativeDll(flag);
		this.enableKeyboard(flag);
		this.enableMouse(flag);
	}
	
	public PrimeBrownEventListener() {
		System.setOut(new BrownStream(System.out));
		GlobalScreen.setEventDispatcher(new SwingDispatchService());
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent event) {
		System.out.println(event.toString());
		notifyListeners(getKeyEvent(event, EventE.KEY_PRESSED));
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent event) { 
		System.out.println(event.toString());
		notifyListeners(getKeyEvent(event, EventE.KEY_RELEASE));
	}
	
	@Override
	public void nativeKeyTyped(NativeKeyEvent event) {
		//System.out.println(event.toString());
		
	}

	@Override
	public void nativeMouseClicked(NativeMouseEvent event) {
		//System.out.println(event.toString());
		//notifyListeners(new XMouseEvent(event.getButton(), 2, event.getX(),
		//		event.getY(), EventE.MOUSE_C));
		//this.nativeMousePressed(event);
		//this.nativeMouseReleased(event);
	}

	@Override
	public void nativeMousePressed(NativeMouseEvent event) {
		System.out.println(event.toString());
		notifyListeners(getMouseEvent(event, EventE.MOUSE_PRESS));
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent event) {
		System.out.println(event.toString());
		notifyListeners(getMouseEvent(event, EventE.MOUSE_RELEASE));
	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent event) {
		System.out.println(event.toString());
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent event) {
		notifyListeners(getMouseEvent(event, EventE.MOUSE_MOVE));
	}

	@Override
	public void nativeMouseWheelMoved(NativeMouseWheelEvent event) {
		System.out.println(event.toString());

	}

	private void enableNativeDll(boolean start) {
		try {
			if (start) {
				GlobalScreen.registerNativeHook();
			} else {
				GlobalScreen.unregisterNativeHook();
			}
		} catch (NativeHookException ex) {
			BrownLogger.logf("Error: %s\n", ex.getMessage());
		}
	}

	private void enableKeyboard(boolean start) {
		if (start) {
			GlobalScreen.addNativeKeyListener(this);
		} else {
			GlobalScreen.removeNativeKeyListener(this);
		}
	}
	
	private void enableMouse(boolean start) {
		if (start) {
			GlobalScreen.addNativeMouseListener(this);
			GlobalScreen.addNativeMouseWheelListener(this);
			GlobalScreen.addNativeMouseMotionListener(this);
		} else {
			GlobalScreen.removeNativeMouseListener(this);
			GlobalScreen.removeNativeMouseWheelListener(this);
			GlobalScreen.removeNativeMouseMotionListener(this);
		}
	}
	
	private XEventI getMouseEvent(NativeMouseEvent mouseEvent, EventE eventE) {
		int button = mouseEvent.getButton();

		switch(button){
			case 1 : button = 2; break;
			case 2 : button = 4; break;
			default: System.out.println("RK: unknown mouse button pressed!! Fix..");
		}
		  
		XEventI xEvent = new XMouseEvent(button, 2, mouseEvent.getX(),
				mouseEvent.getY(), eventE);
		
		return xEvent;
	}
	
	private XEventI getKeyEvent(NativeKeyEvent keyEvent, EventE eventE) {
		XEventI xEvent = new XKeyEvent(keyEvent.getRawCode(), false, false, false,
				NativeKeyEvent.getKeyText(keyEvent.getKeyCode()), eventE);

		return xEvent;
	}
}