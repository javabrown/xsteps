package com.jbrown.robo.impl;

import com.jbrown.robo.XScenarioI;

public class EventRepeater {
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
				new Thread().sleep(seq.getDuration());
				seq.getEvent().trigger(r);
			}
			System.out.printf("Scenario# %s done. ", i);
		}
		
		System.out.printf("\n**** [%s Scenario Repeat Finished !!] *****", nRepeat);
	}
	 
}
