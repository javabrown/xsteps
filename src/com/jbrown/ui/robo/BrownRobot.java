package com.jbrown.ui.robo;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class BrownRobot extends Robot {

	public BrownRobot() throws AWTException {
		super();
	}

	private boolean pressSpecialKey(XKeyEvent key) {
		boolean isPressed = false;
		
		if (key.isShiftRequired()) {
			super.keyPress(java.awt.event.KeyEvent.VK_SHIFT);
			isPressed = true;
		}

		if (key.isCtrlRequired()) {
			super.keyPress(java.awt.event.KeyEvent.VK_CONTROL);
			isPressed = true;
		}

		if (key.isAltRequired()) {
			super.keyPress(java.awt.event.KeyEvent.VK_ALT);
			isPressed = true;
		}
		
		return isPressed;
	}
	
	private boolean releaseSpecialKey(XKeyEvent key) {
		boolean isReleased = false;
		
		if (key.isShiftRequired()) {
			super.keyRelease(java.awt.event.KeyEvent.VK_SHIFT);
			isReleased = true;
		}

		if (key.isCtrlRequired()) {
			super.keyRelease(java.awt.event.KeyEvent.VK_CONTROL);
			isReleased = true;
		}

		if (key.isAltRequired()) {
			super.keyRelease(java.awt.event.KeyEvent.VK_ALT);
			isReleased = true;
		}
		
		return isReleased;
	}
			
	public void doKeyPress(XKeyEvent key){
		int extendedCode = key.getKeyCode();
		
		try {
			//int extendedCode = key.getKeyCode(); //KeyEvent.getExtendedKeyCodeForChar(key.getKeyCode());
			//boolean isSpecialKeyAlreadyPressed = this.pressSpecialKey(key);
			
			extendedCode = translateVirtualKeyCode(extendedCode);
			if(/*!isSpecialKeyAlreadyPressed*/ true){
				super.keyPress(extendedCode);
			}
		}
		catch(java.lang.IllegalArgumentException ex){
			System.err.printf("Error on keyCode: %s", extendedCode);
			ex.printStackTrace();
		}
	}
	
	public void doKeyRelease(XKeyEvent key){
		int extendedCode = key.getKeyCode();
		
		try{
			//int extendedCode = key.getKeyCode(); //KeyEvent.getExtendedKeyCodeForChar(key.getKeyCode());
			//boolean isSpecialKeyAlreadyReleased = this.releaseSpecialKey(key);
			
			extendedCode = translateVirtualKeyCode(extendedCode);
			if(/*!isSpecialKeyAlreadyReleased*/true){
				super.keyRelease(extendedCode);
			}
		}
		catch(java.lang.IllegalArgumentException ex){
			System.err.printf("Error on keyCode: %s", extendedCode);
			ex.printStackTrace();
		}
	}
	
	private int translateVirtualKeyCode(int virtualKeyCode) {
		int code = virtualKeyCode;

		switch (virtualKeyCode) {
			case 8:
				code = KeyEvent.VK_BACK_SPACE;
				break;
				
			case 19:
				code = KeyEvent.VK_ENTER;
				break;
				
			case 13:
				code = KeyEvent.VK_ENTER;
				break;
				
			case 160:
				code = KeyEvent.VK_SHIFT;
				break;
				
			case 161:
				code = KeyEvent.VK_SHIFT;
				break;
				
			case 162:
				code = KeyEvent.VK_CONTROL;
				break;	
			
			case 163:
				code = KeyEvent.VK_CONTROL;
				break;	
				
			case 164:
				code = KeyEvent.VK_ALT;
				break;

			case 173:
				code = KeyEvent.VK_F1;
				break;

			case 174:
				code = KeyEvent.VK_F2;
				break;
				
			case 175:
				code = KeyEvent.VK_F3;
				break;
				
			case 177:
				code = KeyEvent.VK_F4;
				break;
				
			case 179:
				code = KeyEvent.VK_F5;
				break;	
			
			case 176:
				code = KeyEvent.VK_F6;
				break;
				
			case 170:
				code = KeyEvent.VK_F9;
				break;
			
			case 255:
				code = KeyEvent.VK_F11;
				break;
				
			case 186:
				code = KeyEvent.VK_COLON;
				break;
			
			case 187:
				code = KeyEvent.VK_PLUS;
				break;
				
			case 188:
				code = KeyEvent.VK_COMMA;
				break;
				
			case 189:
				code = KeyEvent.VK_MINUS;
				break;
				
			case 190:
				code = KeyEvent.VK_PERIOD;
				break;
				
			case 219:
				code = KeyEvent.VK_LEFT_PARENTHESIS;
				break;
								
			case 220:
				code = KeyEvent.VK_BACK_SPACE;
				break;
				
			case 221:
				code = KeyEvent.VK_RIGHT_PARENTHESIS;
				break;
				
			case 222:
				code = KeyEvent.VK_QUOTE;
				break;
	
				
			default:
				code = virtualKeyCode;
		}

		return code;
	}
}
