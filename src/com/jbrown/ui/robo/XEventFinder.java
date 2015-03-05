package com.jbrown.ui.robo;

import com.jbrown.ui.robo.XEventScanner.XScenarioEntry;

public class XEventFinder implements XEventFinderI, XScenarioEntry {
	private XScenarioI _xScenario;
	private XEventScanner _xScanner;
	
	public XEventFinder(){
		_xScenario = new XScenario();
		_xScanner = new XEventScanner(this);
	}

	@Override
	public void startScan() {
		_xScanner.startScan();
	}

	@Override
	public void stopScan() {
		_xScanner.stopScan();
	}

	@Override
	public XScenarioI getXScenarioEntry() {
		return _xScenario;
	}

	@Override
	public XScenarioI getScenario() {
		return _xScenario;
	}
}
