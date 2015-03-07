package com.jbrown.ui.robo;

public class EventRepeater {
	private XScenarioI _xScenario;
	
	public EventRepeater(XScenarioI xScenarioI){
		_xScenario = xScenarioI;
	}
	
	@SuppressWarnings("static-access")
	public void trigger(BrownRobot r) throws InterruptedException {
		for (XEventSequence seq : _xScenario.getEventSequence()) {
			new Thread().sleep(seq.getDuration());
			seq.getEvent().trigger(r);
			//System.out.println("seq="+seq.getSequenceNumber() + ": " + seq.getEvent());
		}
	}
}
