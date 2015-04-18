package com.jbrown.core.events;

import org.jnativehook.keyboard.NativeKeyEvent;

import com.jbrown.robo.XEventI;
import com.jbrown.robo.impl.EventE;
import com.jbrown.robo.impl.XKeyEvent;
import com.jbrown.robo.impl.XMouseEvent;

import de.ksquared.system.keyboard.GlobalKeyListener;
import de.ksquared.system.keyboard.KeyEvent;
import de.ksquared.system.keyboard.KeyListener;
import de.ksquared.system.mouse.GlobalMouseListener;
import de.ksquared.system.mouse.MouseEvent;
import de.ksquared.system.mouse.MouseListener;

public class AlternativeBrownEventListener extends BrownEvents implements
		KeyListener, MouseListener {
	private GlobalKeyListener _keyListener;
	private GlobalMouseListener _mouseListener;
	
	public AlternativeBrownEventListener(){
		_keyListener = new GlobalKeyListener();
		_mouseListener = new GlobalMouseListener();
	}
	
	@Override
	void enableNativeListeners(boolean flag) {
		if(flag){
			_keyListener.addKeyListener(this);
			_mouseListener.addMouseListener(this);
		}
		else{
			_keyListener.removeKeyListener(this);
			_mouseListener.removeMouseListener(this);
		}
		
		_isEnabled = flag;  
	}
	
	@Override
	public void mouseMoved(MouseEvent event) {
		this.submitMouseEvent(event, EventE.MOUSE_MOVE);
	}

	@Override
	public void mousePressed(MouseEvent event) {
		this.submitMouseEvent(event, EventE.MOUSE_PRESS);
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		this.submitMouseEvent(event, EventE.MOUSE_RELEASE);
	}

	@Override
	public void keyPressed(KeyEvent event) {
		this.submitKeyEvent(event, EventE.KEY_PRESSED);
	}

	@Override
	public void keyReleased(KeyEvent event) {
		this.submitKeyEvent(event, EventE.KEY_RELEASE);
	}

	private void submitMouseEvent(MouseEvent mouseEvent, EventE eventE) {
		XEventI xEvent = new XMouseEvent(mouseEvent.getButton(),
				mouseEvent.getButtons(), mouseEvent.getX(), mouseEvent.getY(),
				eventE);
		
		super.notifyListeners(xEvent);
	}

	private void submitKeyEvent(KeyEvent keyEvent, EventE eventE) {
		int keyCode = keyEvent.getVirtualKeyCode();

		XEventI xEvent = new XKeyEvent(keyCode, keyEvent.isAltPressed(),
				keyEvent.isCtrlPressed(), keyEvent.isShiftPressed(), NativeKeyEvent.getKeyText(keyCode), eventE);

		super.notifyListeners(xEvent);
	}
}
