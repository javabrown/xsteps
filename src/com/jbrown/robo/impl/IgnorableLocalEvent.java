package com.jbrown.robo.impl;

public class IgnorableLocalEvent extends XEvent {
	public IgnorableLocalEvent(BrownSpot spot, EventE event) {
		super(null, null);
	}

	@Override
	public boolean isIgnorableEvent() {
		return true;
	}
}
