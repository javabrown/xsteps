package com.jbrown.observers;

import java.util.Observable;
import java.util.Observer;

import com.jbrown.robo.impl.ViewCriteria;
import com.jbrown.ui.SpinnerButton;
import com.jbrown.ui.SpinnerButton.NRepeatTracker;

public class AppDataObserver implements Observer {
	private int _nRepeat;
	private ViewCriteria _viewCriteria; 
	
	public AppDataObserver(){
		_nRepeat = 1;
		_viewCriteria = new ViewCriteria();
	}
	
	public int getNRepeat(){
		return _nRepeat;
	}
	
	public ViewCriteria getViewCriteria(){
		return _viewCriteria;
	}
	
	@Override
	public void update(Observable o, Object obj) {
		if(obj instanceof NRepeatTracker){
			_nRepeat = ((NRepeatTracker)obj).getNRepeat();
			System.out.printf("AppDataObserver : Observerd NRepeatTracker = %s.", _nRepeat);
		}
		
		else{
			System.out.println("AppDataObserver : Unknown Observer..");
		}
	}
}
