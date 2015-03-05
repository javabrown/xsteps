package com.jbrown.ui.robo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class XEventQueue implements XEventQueueI {
	private List<XEventSequenceI> _sequenceQueue;
	
	public XEventQueue(){
		_sequenceQueue =  new ArrayList<XEventSequenceI>();
	}
	
	
	@Override
	public XEventSequenceI[] getEventSequences(){
		return _sequenceQueue.toArray(new XEventSequenceI[0]);
	}
	
	@Override
	public void addEventSequences(XEventSequenceI sequence){
		_sequenceQueue.add(sequence);
	}
}
