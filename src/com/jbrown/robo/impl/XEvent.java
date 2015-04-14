package com.jbrown.robo.impl;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.Date;

import com.jbrown.robo.XEventI;
import com.jbrown.util.StepUtil;

public abstract class XEvent implements XEventI {
	private BrownSpot _spot;
	private EventE _event;
	private Date _timestamp;
	
	public XEvent(BrownSpot spot, EventE event) {
		_spot = spot;
		_event = event;
		_timestamp = new Date();
	}

	public BrownSpot getBrownSpot(){
		return _spot;
	}
	
	public void trigger(BrownRobot r){
		_event.trigger(r, this);
	}
	
	@Override
	public EventE getEvent() {
		return _event;
	}

	@Override
	public int getGraphMaskValue(){
		return 100;//default graph
	}

	@Override
	public String toString() {
		return "XEvent [_spot=" + _spot + ", _event=" + _event
				+ ", _timestamp=" + _timestamp + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_event == null) ? 0 : _event.hashCode());
		result = prime * result + ((_spot == null) ? 0 : _spot.hashCode());
		result = prime * result
				+ ((_timestamp == null) ? 0 : _timestamp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XEvent other = (XEvent) obj;
		if (_event != other._event)
			return false;
		if (_spot == null) {
			if (other._spot != null)
				return false;
		} else if (!_spot.equals(other._spot))
			return false;
		if (_timestamp == null) {
			if (other._timestamp != null)
				return false;
		} else if (!_timestamp.equals(other._timestamp))
			return false;
		return true;
	}
}



class ClickRecord {
	long delay;
	int x;
	int y;

	public ClickRecord(long delay, int x, int y) {
		this.delay = delay;
		this.x = x;
		this.y = y;
	}
	
	public void execute(Robot r){
		try {
			new Thread().sleep(delay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		r.mouseMove(x, y);
		r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		
		System.out.printf("click event on x=%d , y=%d , delay=%d  executed.\n", x, y, delay);
	}

	@Override
	public String toString() {
		return "ClickRecord [delay=" + delay + ", x=" + x + ", y=" + y + "]";
	}
}
