package com.jbrown.robo.impl;

public class ViewCriteria {
	private boolean _isFastFarwardRequested; 
	
	public ViewCriteria(){
		_isFastFarwardRequested = false;
	}
	
	public void setFastForward(boolean flag) {
		_isFastFarwardRequested = flag;
	}
	
	public boolean isFastForward() {
		return _isFastFarwardRequested;
	}
}
