package com.jbrown.ui;

import java.util.Arrays;
import com.jbrown.robo.KeysI;
import com.jbrown.robo.XEventI;
import com.jbrown.robo.impl.EventE;
import com.jbrown.robo.impl.XKeyEvent;
import com.jbrown.util.XStepCommandMode;

public class XStepOperatorCommandWatch {
	private int _maxKeyCombindation;
	private int _i;
	private int[] _keyCombination;
	private XStepCommandMode _currentMode; 
	
	public XStepOperatorCommandWatch() {
		_i = 0;
		_maxKeyCombindation = KeysI.RECORDING_PAUSE_KEY_COMBINATION.length;
		_keyCombination = new int[_maxKeyCombindation];
		_currentMode = XStepCommandMode.NONE;
	}

	public synchronized void addCommand(XEventI event) {
		if (event instanceof XKeyEvent) {
			XKeyEvent key = (XKeyEvent) event;
			if (key.getEvent().typeOf(EventE.KEY_RELEASE)) {
				addCommand(key.getKeyCode());
			}
			
			System.out.println(Arrays.toString(_keyCombination));
		}
	}
	
	private synchronized void addCommand(int keyCode) {
		if (_currentMode.typeOf(XStepCommandMode.PAUSE)
				&& keyCode != KeysI.RECORDING_RESUME_PAUSE_COMMAND_KEY) {
			return;
		}

		if (keyCode == KeysI.RECORDING_RESUME_PAUSE_COMMAND_KEY) {
			_i = 0;
			_keyCombination = new int[_maxKeyCombindation];
			_currentMode = XStepCommandMode.RESUME_PAUSE;
		}
		
		if (_i > KeysI.RECORDING_PAUSE_KEY_COMBINATION.length - 1) {
			_i = 0;
			_keyCombination = new int[_maxKeyCombindation];
		}

		_keyCombination[_i] = keyCode;
		_i++;
	}

	public synchronized void resetPauseCommand() {
		_keyCombination = new int[_maxKeyCombindation];
	}
	
	public synchronized boolean isPauseCommand() {
		boolean match = _keyCombination.length == KeysI.RECORDING_PAUSE_KEY_COMBINATION.length;
		match &= Arrays.equals(_keyCombination, KeysI.RECORDING_PAUSE_KEY_COMBINATION);

		if (match) {
			_currentMode = XStepCommandMode.PAUSE;
		} else {
			_currentMode = XStepCommandMode.RESUME_PAUSE;
		}

		return match;
	}
	
	
	//public static void main(String[] args){
	//	XStepOperatorCommandWatch c = new XStepOperatorCommandWatch();
	//	c.addCommand(27);c.addCommand(27);c.addCommand(27);
	//	System.out.println(c.isPauseCommand());
	//}
}
