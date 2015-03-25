package com.jbrown.ui;

import java.awt.event.ActionListener;

public class XTemplate implements XTemplateI {
	private XStepView[] _xStepView;
	
	public void setXStepViews(XStepView[] xStepView){
		_xStepView = xStepView;
	}
	
	@Override
	public void trigger() {
	//	if(_commands == null){
	//		return;
	//	}
	//	_stepActions.pushXCommand();
	}
	
	static interface XStepView extends ActionListener{
		void pushXView();
	}
}
