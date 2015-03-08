package com.jbrown.robo.impl;

import java.util.ArrayList;
import java.util.List;

import com.jbrown.robo.XEventI;
import com.jbrown.robo.XEventSequenceI;
import com.jbrown.robo.XScenarioI;

public class XScenario implements XScenarioI {
	private List<XEventSequenceI> _eventSequenceList;
	private EventIndexGenerator _indexGenerator;
	
	public XScenario(){
		_eventSequenceList = new ArrayList<XEventSequenceI>();
		_indexGenerator = new EventIndexGenerator();
	}
	
	public void resetXScenario(){
		_eventSequenceList.clear();
		_indexGenerator.reset();
	}
	
	public void addEvent(XEventI event) {
		int index = _indexGenerator.nextEventId();
		long delay = _indexGenerator.nextEventDelay();
		XEventSequenceI sequence = new XEventSequence(index, event, delay);
		_eventSequenceList.add(sequence);
	}
	
	@Override
	public XEventSequence[] getEventSequence() {
		return _eventSequenceList.toArray(new XEventSequence[0]);
	}
	
	@Override
	public boolean hasValidScenario() {
		return _eventSequenceList != null && 
				_eventSequenceList.size() > 0;
	}
	
	@Override
	public int nEvent(){
		return _eventSequenceList.size();
	}
}
