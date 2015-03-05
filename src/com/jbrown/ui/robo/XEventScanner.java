package com.jbrown.ui.robo;

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
	protected XScenarioI getXScenarioEntry() {
		return _entry.getXScenarioEntry();
	}
	
	public static interface XScenarioEntry {
	    XScenarioI getXScenarioEntry();
	}
}
