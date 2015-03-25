package com.jbrown.robo.impl;

import com.jbrown.robo.XEventFinderI;
import com.jbrown.robo.XEventI;
import com.jbrown.robo.XScenarioI;

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
	
	public XEventI getLiveEvent(){
		return _finder.getLiveEvent();
	}
	
	public String save(){
		return _finder.getSavableScanedData();
	}
	
	public XScenarioI getXScenario(){
		return _finder.getScenario();
	}
}
