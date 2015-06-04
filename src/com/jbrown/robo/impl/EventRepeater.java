package com.jbrown.robo.impl;

import org.apache.log4j.Logger;

import com.jbrown.core.activity.ActivityI;
import com.jbrown.core.activity.TweetActivity;
import com.jbrown.robo.XScenarioI;
import com.jbrown.ui.XDialog;
import com.jbrown.util.BrownLogger;

public class EventRepeater {
	static Logger _logger = Logger.getLogger(EventRepeater.class);
	
	private final XScenarioI _xScenario;
	private final ActivityI _clipActivity;
	
	public EventRepeater(XScenarioI xScenarioI, ActivityI clipActivity){
		_xScenario = xScenarioI;
		_clipActivity  = clipActivity;
	}
	
//	public void trigger(BrownRobot r, int nRepeat, boolean fastFarward)
//			throws InterruptedException {
//		_clipActivity.reload();
//		XEventSequence[] seqs = _xScenario.getEventSequence();
//		
//		
//		System.out.printf("\nMAX Repeat Scenario =%s Begin ", nRepeat);
//		
//		for (int i = 0; i < nRepeat; i++) {
//			_clipActivity.prepareNextClipContent();
//			
//			for (XEventSequence seq : seqs) {
//				new ScenarioRunner(seq, r).execute();
//			}
//			
//			XDialog.setTitle(String.format("Scenario# %s done. ", i));
//			System.out.printf("Scenario# %s done. ", i);
//		}
//		
//		XDialog.stop();
//		System.out.printf("\n**** [%s Scenario Repeat Finished !!] *****", nRepeat);
//	}
	
	public void trigger(BrownRobot r, int nRepeat, boolean fastFarward)
			throws InterruptedException {
		new Thread(new Perform(nRepeat, r)).start();
	}
	
	class Perform implements Runnable {
		int _nRepeat;
		BrownRobot _robot;
		
		public Perform(int nRepeat, BrownRobot robot){
			_nRepeat = nRepeat;
			_robot = robot;
		}
		
		@Override
		public void run() {
			_clipActivity.reload();
			XEventSequence[] seqs = _xScenario.getEventSequence();
			
			int i = 0;
			System.out.printf("\nMAX Repeat Scenario =%s Begin ", _nRepeat);
			XDialog.start();
			
			
			for (; i < _nRepeat; i++) {
				XDialog.setTitle(String.format("%s of %s Done ", i, _nRepeat));
				
				_clipActivity.prepareNextClipContent();
				
				for (XEventSequence seq : seqs) {
					try {
						new ScenarioRunner(seq, _robot).execute();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				XDialog.setTitle(String.format("Scenario# %s done. ", i));
				System.out.printf("Scenario# %s done. ", i);
			}
			
			XDialog.stop();
			System.out.printf("\n**** [%s Scenario Repeat Finished !!] *****", _nRepeat);
		}
	}
}

class ScenarioRunner {
	private XEventSequence _seq;
	private BrownRobot _robo;
	private RunnerStatus _status;
	
	ScenarioRunner(XEventSequence seq, BrownRobot robo) {
		_seq = seq;
		_robo = robo;
		_status = RunnerStatus.init;
	}
	
	synchronized void execute() throws InterruptedException {
		_status = RunnerStatus.running;
		//this.executeInterval();
		//_seq.getEvent().trigger(_robo);
		_seq.repeat(_robo);
		_status = RunnerStatus.complete;
	}
	
//	synchronized private void executeInterval() {
//		try {
//			new Thread().sleep(_seq.getDuration());
//		} catch (InterruptedException e) {
//			BrownLogger
//					.logf("Error during call of EventRepeater.executeInterval() : %s",
//							e.getMessage());
//		}
//	}
	
	RunnerStatus getRunnerStatus(){
		return _status;
	}
}

enum RunnerStatus {
	init, running, pause, complete;

	boolean isRunning(RunnerStatus status) {
		return status.equals(running);
	}
	
	boolean isDone(RunnerStatus status) {
		return status.equals(complete);
	}
}