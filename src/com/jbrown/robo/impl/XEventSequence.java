package com.jbrown.robo.impl;

import java.util.Timer;
import java.util.TimerTask;

import com.jbrown.robo.XEventI;
import com.jbrown.robo.XEventSequenceI;
import com.jbrown.util.BrownLogger;

public class XEventSequence implements XEventSequenceI {
	private final int _sequenceNo;
	private final XEventI _event;
	private final long _duration;
	
	public XEventSequence(int sequenceNo, XEventI event, long duration) {
		_sequenceNo = sequenceNo;
		_event = event;
		_duration = duration;
	}
	
	@Override
	public void repeat(final BrownRobot r){
//		new Timer().schedule(new TimerTask() {
//			
//			@Override
//			public void run() {
//				_event.trigger(r);
//			}
//		}, _duration);
		
		try {
			Thread.sleep(_duration);
			_event.trigger(r);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public int getSequenceNumber() {
		return _sequenceNo;
	}

	@Override
	public XEventI getEvent() {
		return _event;
	}

	@Override
	public long getDuration() {
		return _duration;
	}

	@Override
	public String toString() {
		return "XEventSequence [_sequenceNo=" + _sequenceNo + ", _event="
				+ _event + ", _duration=" + _duration + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (_duration ^ (_duration >>> 32));
		result = prime * result + ((_event == null) ? 0 : _event.hashCode());
		result = prime * result + _sequenceNo;
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
		XEventSequence other = (XEventSequence) obj;
		if (_duration != other._duration)
			return false;
		if (_event == null) {
			if (other._event != null)
				return false;
		} else if (!_event.equals(other._event))
			return false;
		if (_sequenceNo != other._sequenceNo)
			return false;
		return true;
	}
}
