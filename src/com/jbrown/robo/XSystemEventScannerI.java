package com.jbrown.robo;

import com.jbrown.ui.XStepOperatorCommandWatch;

public interface XSystemEventScannerI {
	void startScan();
	void stopScan();
	XEventI getLiveEvent();
	boolean isScanRunning();
	XStepOperatorCommandWatch getXStepOperatorCommandWatch();
}
