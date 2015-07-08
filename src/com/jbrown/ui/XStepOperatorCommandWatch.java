package com.jbrown.ui;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import com.jbrown.robo.KeysI;
import com.jbrown.robo.XEventI;
import com.jbrown.robo.impl.EventE;
import com.jbrown.robo.impl.XKeyEvent;
import com.jbrown.util.BrownLogger;

public class XStepOperatorCommandWatch {
	private int maxKeyCombindation;
	private int i;
	private int[] keyCombination;

	public XStepOperatorCommandWatch() {
		i = 0;
		maxKeyCombindation = KeysI.RECORDING_PAUSE_KEY_COMBINATION.length;
		keyCombination = new int[maxKeyCombindation];
	}

	public synchronized void addCommand(XEventI event) {
		if (event instanceof XKeyEvent) {
			XKeyEvent key = (XKeyEvent) event;
			if (key.getEvent().typeOf(EventE.KEY_RELEASE)) {
				addCommand(key.getKeyCode());
			}
			
			System.out.println(Arrays.toString(keyCombination));
		}
	}
	
	private synchronized void addCommand(int keyCode) {
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
			//BrownLogger.log("PAUSE COMMAND BY USER");
		}
		
		return match;
	}
	
	//public static void main(String[] args){
	//	XStepOperatorCommandWatch c = new XStepOperatorCommandWatch();
	//	c.addCommand(27);c.addCommand(27);c.addCommand(27);
	//	System.out.println(c.isPauseCommand());
	//}
}
