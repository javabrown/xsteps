package com.jbrown.robo.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.jbrown.robo.XEventQueueI;
import com.jbrown.robo.XEventSequenceI;

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
