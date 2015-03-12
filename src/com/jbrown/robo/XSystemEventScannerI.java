package com.jbrown.robo;

public interface XSystemEventScannerI {
	void startScan();
	void stopScan();
	XEventI getLiveEvent();
	boolean isScanRunning();
}
