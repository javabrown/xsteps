package com.jbrown.robo.impl;

import com.jbrown.robo.XScenarioI;

public class EventRepeater {
	private XScenarioI _xScenario;
	
	public EventRepeater(XScenarioI xScenarioI){
		_xScenario = xScenarioI;
	}
	
	@SuppressWarnings("static-access")
	public void trigger(BrownRobot r, boolean fastFarward) throws InterruptedException {
		for (XEventSequence seq : _xScenario.getEventSequence()) {
			//if(fastFarward && seq.getEvent().getEvent().typeOf(EventE.MOUSE_MOVE)){
			//	continue;
			//}
			
			new Thread().sleep(seq.getDuration());
			seq.getEvent().trigger(r);
			//System.out.println("seq="+seq.getSequenceNumber() + ": " + seq.getEvent());
		}
	}
	 
}
