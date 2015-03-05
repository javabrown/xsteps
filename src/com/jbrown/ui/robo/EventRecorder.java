package com.jbrown.ui.robo;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;

public class EventRecorder {
	public XEventFinderI _finder;
	
	
	public EventRecorder(){
		_finder = new XEventFinder();
	}
	
	public void doRecording(){
		_finder.startScan();
	}
	
	public static void main(String[] args) throws InterruptedException, AWTException{
		EventRecorder recorder = new EventRecorder();
		
		recorder.doRecording();
		int i = 0;
		while(i <10000000){
			//dddff
			System.out.println(i);
			i++;
		}
		recorder._finder.stopScan();
		
		EventRepeater repeater = new EventRepeater(recorder._finder.getScenario());
		repeater.trigger(new Robot());
	}
}
