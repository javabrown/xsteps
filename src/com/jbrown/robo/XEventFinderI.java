package com.jbrown.robo;

public interface XEventFinderI {
	XScenarioI getScenario();
	void startScan();
	void stopScan();
	void resetScan();
	XEventI getLiveEvent();
	String getSavableScanedData();
}