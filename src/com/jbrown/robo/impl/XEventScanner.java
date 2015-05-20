package com.jbrown.robo.impl;

import java.util.Deque;

import com.jbrown.core.events.BrownEventFactory;
import com.jbrown.robo.XEventI;
import com.jbrown.robo.XScenarioI;
import com.jbrown.robo.XSystemEventScannerI;
import com.jbrown.ui.XStepOperatorCommandWatch;

public class XEventScanner extends XSystemEventScanner0 implements XSystemEventScannerI {
	
	public XEventScanner(XScenarioI scenarioStorage) {
		super(scenarioStorage);
	}

	@Override
	public void startScan() {
		super.startListener();
	}

	@Override
	public void stopScan() {
		super.stopListener();
	}
	
	@Override
	public boolean isScanRunning(){
		return super.isScanRunning();
	}
	 
	
	public static interface XScenarioEntry {
	    XScenarioI getXScenarioEntry();
	}

	@Override
	public XEventI getLiveEvent() {
		return super.getLiveStatus();
	}
	
	@Override
	public XStepOperatorCommandWatch getXStepOperatorCommandWatch(){
		return null;//getOperatorCommand();
	}
}
