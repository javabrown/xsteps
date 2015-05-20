package com.jbrown.robo.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedDeque;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.jbrown.robo.KeysI;
import com.jbrown.robo.XEventI;
import com.jbrown.robo.XEventSequenceI;
import com.jbrown.robo.XScenarioI;
import com.jbrown.util.BrownLogger;
import com.jbrown.util.StepUtil;

public class XScenario implements XScenarioI {
	//private List<XEventSequenceI> _eventSequenceList;
	private Stack<XEventSequenceI> _eventSequenceList;
	private EventIndexGenerator _indexGenerator;
	
	public XScenario(){
		//_eventSequenceList = new ArrayList<XEventSequenceI>();
		_eventSequenceList = new Stack<XEventSequenceI>();
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
		//_eventSequenceList.add(sequence);
		_eventSequenceList.push(sequence);
		
		//listen pause command
		this.verifyPauseCommand(sequence);
	}
	
 
	//Check point to identify Pause command to halt recording
	private void verifyPauseCommand(XEventSequenceI sequence){
		XCommand xCommand = XCommand.getInstance();
		
		if(xCommand.put(sequence.getEvent())){
			int nKeys = xCommand.nKeys();
			
			for(int i = 0; i< nKeys; i++){
				_eventSequenceList.pop();
			}
			
			JOptionPane.showMessageDialog(new JFrame(), "User Break");
		}
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

class XCommand {
	int[] _eventKeys;
	int[] _eventStoreIndexs;
	
	int _i;

	private static XCommand _command;

	public static XCommand getInstance() {
		if (_command == null) {
			_command = new XCommand();
		}

		return _command;
	}

	private XCommand() {
		_i = 0;
		_eventKeys = new int[3];// XEventI
		_eventStoreIndexs = new int[3];
	}

	public boolean put(XEventI e) {
		if (e == null || !e.getEvent().typeOf((EventE.KEY_RELEASE))) {
			return false;
		}

		XKeyEvent k = (XKeyEvent) e;

		if (_i >= _eventKeys.length) {
			_i = 0;
		}

		_eventKeys[_i] = k.getKeyCode();

		return StepUtil.arrayComp(KeysI.RECORDING_PAUSE_KEY_COMBINATION, _eventKeys);
	}
	
	public int nKeys(){
		return _eventKeys.length;
	}
}
