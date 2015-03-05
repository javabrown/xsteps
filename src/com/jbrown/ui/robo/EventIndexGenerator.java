package com.jbrown.ui.robo;

import java.util.Calendar;
import java.util.Date;

public class EventIndexGenerator {
	private int _index;
	private Date _lastClickOnDate;
	private long _delay;
	
	public EventIndexGenerator(){
		_index = 0;
		_lastClickOnDate = null;
		_delay = 0;
	}

	private long getDifference(Date startDate, Date endDate) {
		long diff = 0;

		Calendar c1 = Calendar.getInstance();
		c1.setTime(startDate);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(endDate);

		long ms1 = c1.getTimeInMillis();
		long ms2 = c2.getTimeInMillis();

		diff = ms2 - ms1;

		return diff;
	}
	
	public int nextEventId(){
		return _index++;
	}
	
	public long nextEventDelay(){
		 if(_lastClickOnDate == null){
			 _lastClickOnDate= new Date();
			 _delay = 0;
		 }
		 else{
			 _delay = getDifference(_lastClickOnDate, new Date());
			 _lastClickOnDate = new Date(); //Assign next date stamp.
		 }

		return _delay;
	}
}
