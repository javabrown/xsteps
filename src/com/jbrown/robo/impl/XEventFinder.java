package com.jbrown.robo.impl;

import com.jbrown.robo.XEventFinderI;
import com.jbrown.robo.XScenarioI;
import com.jbrown.robo.impl.XEventScanner.XScenarioEntry;

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
	public void resetScan() {
		if(_xScanner.isScanRunning()){
			_xScanner.stopScan();
		}

		_xScenario.resetXScenario();
	}
	
	@Override
	public XScenarioI getXScenarioEntry() {
		return _xScenario;
	}

	@Override
	public XScenarioI getScenario() {
		return _xScenario;
	}
	
	@Override
	public String getSavableScanedData(){
		XEventSequence[] seqs = _xScenario.getEventSequence();
		
		StringBuilder builder = new StringBuilder();
		
		for(XEventSequence seq : seqs){
			builder.append(String.format("%s\n", seq));
		}
		
		return builder.toString();
	}
}