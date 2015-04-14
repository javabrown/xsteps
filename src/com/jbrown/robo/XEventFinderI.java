package com.jbrown.robo;

import com.jbrown.ui.XStepOperatorCommandWatch;

public interface XEventFinderI {
	XScenarioI getScenario();
	void startScan();
	void stopScan();
	void resetScan();
	XEventI getLiveEvent();
	String getSavableScanedData();
	XStepOperatorCommandWatch getXStepOperatorCommandWatch();
}
