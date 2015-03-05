package com.jbrown.ui.robo;

import java.awt.Robot;
import java.awt.event.InputEvent;

public abstract class XEvent implements XEventI {
	private BrownSpot _spot;
	private Event _event;
	
	public XEvent(BrownSpot spot){
		_spot = spot;
		_event = getEvent();
	}

	public BrownSpot getBrownSpot(){
		return _spot;
	}
	
	public abstract Event getEvent();
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
