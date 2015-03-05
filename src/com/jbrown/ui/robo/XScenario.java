package com.jbrown.ui.robo;

import java.util.ArrayList;
import java.util.List;

public class XScenario implements XScenarioI {
	private List<XEventSequence> _eventSequenceList;
	
	public XScenario(){
		_eventSequenceList = new ArrayList<XEventSequence>();
	}
	
	@Override
	public void addEventSequence(XEventSequence sequence){
		if(sequence == null){
			System.out.println("...TEST ON PENDING ADD EVENT...");
			return;
		}
		_eventSequenceList.add(sequence);
	}
	
	@Override
	public XEventSequence[] getEventSequence() {
		return null;
	}
	
	@Override
	public boolean hasValidScenario() {
		return _eventSequenceList != null && 
				_eventSequenceList.size() > 0;
	}
}
