package com.jbrown.ui.robo;

public class DblClickEventRecord extends XEvent {
	public DblClickEventRecord(BrownSpot spot) {
		super(spot);
	}

	@Override
	public Event getEvent() {
		return Event.DOUBLE_CLICKED;
	}
}