package com.jbrown.ui;

import java.awt.event.ActionListener;

public abstract class XTemplate implements XTemplateI {
	//private XStepView[] _xStepView;
	private XDesktop _xDesktop;
	
	//public void setXStepViews(XStepView[] xStepView){
	//	_xStepView = xStepView;
	//}
	
	@Override
	public void trigger() {
	//	if(_commands == null){
	//		return;
	//	}
	//	_stepActions.pushXCommand();
	}
	
	public void setXDesktop(XDesktop xDesktop){
		_xDesktop = xDesktop;
	}
	
	public final XDesktop getXDesktop(){
		return _xDesktop;
	}
	
	static interface XStepView {
		void pushXView();
	}
}
