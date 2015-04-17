package com.jbrown.core.listener;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Observable;
import java.util.Observer;
import java.util.regex.Pattern;

import org.jnativehook.GlobalScreen;
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
import com.jbrown.util.BrownStream;

class GlobalBrownListener extends Observable implements NativeKeyListener,
		NativeMouseInputListener, NativeMouseWheelListener {
	public GlobalBrownListener() {
		System.setOut(new BrownStream(System.out));
		GlobalScreen.setEventDispatcher(new SwingDispatchService());
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent event) {
		System.out.println(event.toString());
		System.out.println(event.toString());
		notifyListeners(new XKeyEvent(event.getKeyCode(), false, false, false,
				EventE.KEY_PRESSED));
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent event) {
		System.out.println(event.toString());
		notifyListeners(new XKeyEvent(event.getKeyCode(), false, false, false,
				EventE.KEY_RELEASE));
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
	}

	@Override
	public void nativeMousePressed(NativeMouseEvent event) {
		System.out.println(event.toString());
		notifyListeners(new XMouseEvent(event.getButton(), 2, event.getX(),
				event.getY(), EventE.MOUSE_PRESS));
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent event) {
		System.out.println(event.toString());
		notifyListeners(new XMouseEvent(event.getButton(), 2, event.getX(),
				event.getY(), EventE.MOUSE_RELEASE));
	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent event) {
		System.out.println(event.toString());

	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent event) {
		System.out.println(event.toString());

	}

	@Override
	public void nativeMouseWheelMoved(NativeMouseWheelEvent event) {
		System.out.println(event.toString());

	}
	
	private void notifyListeners(XEventI event){
		setChanged();
		notifyObservers(event);
	}
}