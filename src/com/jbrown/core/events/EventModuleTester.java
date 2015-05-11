package com.jbrown.core.events;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jbrown.observers.BrownObserverI;
import com.jbrown.robo.XEventI;
import com.jbrown.robo.XScenarioI;
import com.jbrown.robo.impl.BrownRobot;
import com.jbrown.robo.impl.EventRepeater;
import com.jbrown.robo.impl.XEventSequence;
import com.jbrown.robo.impl.XScenario;

public class EventModuleTester extends JFrame implements BrownObserverI, ActionListener{
	BrownEventsI _event;
	XScenarioI _xScenario;

	public static void main(String[] args){
		 new EventModuleTester();
	}
	
	public EventModuleTester() {
		_event = new AlternativeBrownEventListener();//new PrimeBrownEventListener();
		_xScenario = new XScenario();
		
		JPanel jp = new JPanel();
		getContentPane().add(jp);

		JButton b1 = new JButton("Start");
		JButton b2 = new JButton("Stop");
		JButton b3 = new JButton("Play");
		JButton b4 = new JButton("Reset");
		
		b1.setActionCommand("Start");
		b2.setActionCommand("Stop");
		b3.setActionCommand("Play");
		b4.setActionCommand("Reset");
		
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		
		jp.add(b1);
		jp.add(b2);
		jp.add(b3);
		jp.add(b4);
		
		setDefaultCloseOperation(3);
		setSize(300, 80);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Start")){
			_event.addEventObservable(this);
			_event.setEnable(true);
		}
		
		if(e.getActionCommand().equals("Stop")){
			_event.removeEventObservable(this);
			_event.setEnable(false);
		}
		
		if(e.getActionCommand().equals("Play")){
			BrownRobot r = null;
			try {
				r = new BrownRobot();
			} catch (AWTException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			if(r != null &&_xScenario.hasValidScenario()){
				EventRepeater rp = new EventRepeater(_xScenario, null);
				try {
					rp.trigger(r, 1, false);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		if(e.getActionCommand().equals("Reset")){
			_xScenario.resetXScenario();
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof XEventI){
			System.out.println( ( (XEventI)arg).toString() );
			_xScenario.addEvent((XEventI)arg);
		}
		else{
			System.out.println("Unknown Observer..");
		}
	}
	
}
