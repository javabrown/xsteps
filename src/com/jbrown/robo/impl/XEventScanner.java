package com.jbrown.robo.impl;

import com.jbrown.robo.XScenarioI;
import com.jbrown.robo.XSystemEventScannerI;

public class XEventScanner extends XSystemEventScanner implements XSystemEventScannerI {
	private XScenarioEntry _entry;
	
	public XEventScanner(XScenarioEntry entry) {
		_entry = entry;
	}

	@Override
	public void startScan() {
		super.startListener();;
	}

	@Override
	public void stopScan() {
		super.stopListener();
	}
	
	@Override
	public boolean isScanRunning(){
		return super.isScanRunning();
	}
	
	@Override
	protected XScenarioI getXScenarioEntry() {
		return _entry.getXScenarioEntry();
	}
	
	public static interface XScenarioEntry {
	    XScenarioI getXScenarioEntry();
	}
}
