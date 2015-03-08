package com.jbrown.robo;

public interface XEventSequenceI {
	int getSequenceNumber();
	XEventI getEvent();
	long getDuration(); 
}
