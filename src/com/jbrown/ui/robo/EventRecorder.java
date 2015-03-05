package com.jbrown.ui.robo;

import java.awt.Toolkit;

public class EventRecorder {
	private XEventFinderI _finder;
	
	
	public EventRecorder(){
		_finder = new XEventFinder();
	}
	
	public void doRecording(){
		_finder.startScan();
	}
	
	public static void main(String[] args){
		EventRecorder recorder = new EventRecorder();
		recorder.doRecording();
		while(true){
			//dddff
			System.out.println();
		}
	}
}
