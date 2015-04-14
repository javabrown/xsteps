package com.jbrown.ui;

import java.util.Arrays;

import com.jbrown.robo.KeysI;
import com.jbrown.util.BrownLogger;

public class XStepOperatorCommandWatch {
	int maxKeyCombindation;
	int i;
	int[] keyCombination;

	public XStepOperatorCommandWatch() {
		i = 0;
		maxKeyCombindation = KeysI.RECORDING_PAUSE_KEY_COMBINATION.length;
		keyCombination = new int[maxKeyCombindation];
	}

	public synchronized void addCommand(int keyCode) {
		if (i > 2) {
			i = 0;
			keyCombination = new int[maxKeyCombindation];
		}
		
		keyCombination[i] = keyCode;
		i++;
	}

	public synchronized void resetPauseCommand() {
		keyCombination = new int[maxKeyCombindation];
	}
	
	public synchronized boolean isPauseCommand() {
		boolean match = keyCombination.length == KeysI.RECORDING_PAUSE_KEY_COMBINATION.length;
		match &= Arrays.equals(keyCombination, KeysI.RECORDING_PAUSE_KEY_COMBINATION);
		
		if(match){
			BrownLogger.log("PAUSE COMMAND BY USER");
		}
		
		return match;
	}
}
