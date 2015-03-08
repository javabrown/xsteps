package com.jbrown.robo.impl;

import java.awt.AWTException;
import java.awt.Robot;

import com.jbrown.robo.XEventFinderI;

public class EventRecorder {
	public XEventFinderI _finder;
	
	
	public EventRecorder(){
		_finder = new XEventFinder();
	}
	
	public void startRecording(){
		_finder.startScan();
	}
	
	public void stopRecording(){
		_finder.stopScan();
	}
	
	public void resetRecording(){
		_finder.resetScan();
	}
	
	public String save(){
		return _finder.getSavableScanedData();
	}
}
