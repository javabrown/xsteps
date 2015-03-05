package com.jbrown.ui.robo;

public class ClickEventRecord extends XEvent {
	public ClickEventRecord(BrownSpot spot) {
		super(spot);
	}

	@Override
	public Event getEvent() {
		return Event.CLICK;
	}
}