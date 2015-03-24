package com.jbrown.ui;

import java.awt.event.ActionListener;

public class XAction implements XActionI {
	private XStepActions[] _stepActions;
	
	public void setXStepActions(XStepActions[] xStepActions){
		_stepActions = xStepActions;
	}
	
	@Override
	public void trigger() {
	//	if(_commands == null){
	//		return;
	//	}
	//	_stepActions.pushXCommand();
	}
	
	static interface XStepActions extends ActionListener{
		void pushXCommand();
	}
}
