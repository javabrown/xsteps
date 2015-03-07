package com.jbrown.ui.robo;

public interface XEventFinderI {
	XScenarioI getScenario();
	void startScan();
	void stopScan();
	void resetScan();
	String getSavableScanedData();
}
