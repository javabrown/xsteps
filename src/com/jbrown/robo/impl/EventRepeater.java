package com.jbrown.robo.impl;

import org.apache.log4j.Logger;

import com.jbrown.robo.XScenarioI;
import com.jbrown.util.BrownLogger;

public class EventRepeater {
	static Logger _logger = Logger.getLogger(EventRepeater.class);
	
	private XScenarioI _xScenario;
	
	public EventRepeater(XScenarioI xScenarioI){
		_xScenario = xScenarioI;
	}
	
	@SuppressWarnings("static-access")
	public void trigger(BrownRobot r, int nRepeat, boolean fastFarward)
			throws InterruptedException {
		XEventSequence[] seqs = _xScenario.getEventSequence();
		
		System.out.printf("\nMAX Repeat Scenario =%s Begin ", nRepeat);
		
		for (int i = 0; i < nRepeat; i++) {
			for (XEventSequence seq : seqs) {
				new ScenarioRunner(seq, r).execute();
			}
			System.out.printf("Scenario# %s done. ", i);
		}
		
		System.out.printf("\n**** [%s Scenario Repeat Finished !!] *****", nRepeat);
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
		this.executeInterval();
		_seq.getEvent().trigger(_robo);
		_status = RunnerStatus.complete;
	}
	
	synchronized private void executeInterval() {
		try {
			new Thread().sleep(_seq.getDuration());
		} catch (InterruptedException e) {
			BrownLogger
					.logf("Error during call of EventRepeater.executeInterval() : %s",
							e.getStackTrace());
		}
	}
	
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