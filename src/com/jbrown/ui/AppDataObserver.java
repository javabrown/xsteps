package com.jbrown.ui;

import java.util.Observable;
import java.util.Observer;

import com.jbrown.ui.SpinnerButton.NRepeatTracker;

public class AppDataObserver implements Observer {
	private int _nRepeat;
	
	public AppDataObserver(){
		_nRepeat = 1;
	}
	
	public int getNRepeat(){
		return _nRepeat;
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
