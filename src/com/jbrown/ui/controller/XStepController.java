package com.jbrown.ui.controller;

import java.awt.AWTException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.jbrown.robo.XEventI;
import com.jbrown.robo.impl.BrownRobot;
import com.jbrown.robo.impl.EventE;
import com.jbrown.robo.impl.EventRecorder;
import com.jbrown.robo.impl.EventRepeater;
import com.jbrown.robo.impl.XKeyEvent;

public class XStepController extends Observable{
	private EventRecorder _recorder;
	private EventRepeater _repeater;
	
	public XStepController(){
		_recorder = null;
		_repeater = null;
	}
	
	private void initializeScanner(){
		_recorder = new EventRecorder();
		_repeater = new EventRepeater(_recorder.getXScenario());
		 
		 Thread t = new Thread(){
			 @Override
			 public void run(){
				 while(true){
				  XEventI live = _recorder.getLiveEvent();
				  
				  if(live!= null){
					if(live.getEvent().typeOf(EventE.KEY_RELEASE) | 
							live.getEvent().typeOf(EventE.MOUSE_RELEASE)){
						try{
						setChanged();
						notifyObservers(live);
						}catch(Exception ex){ex.printStackTrace();}
						System.out.println("Recorder Live = " + live);
					}
					
				    
				  }
				 }
			 }
		 };
		 t.start();
	}
	
	public void startRecording(){
		_recorder.startRecording();
	}
	
	public void stopRecording(){
		_recorder.stopRecording();
	}
	
	public void reset(){
		_recorder.resetRecording();
	}
	
	public void save(){
		saveRecordToFile(_recorder.save());
	}
	
	public void execute(){
		try {
			_repeater.trigger(new BrownRobot());
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void repeat(){
		try {
			_repeater.trigger(new BrownRobot());
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void saveRecordToFile(String recordToSave){
		JFileChooser saver = new JFileChooser("./");
		int returnVal = saver.showSaveDialog(new JFrame());
		File file = saver.getSelectedFile();
		BufferedWriter writer = null;
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
		    try
		    {
		    writer = new BufferedWriter( new FileWriter( file.getAbsolutePath()+".txt"));
		    writer.write(recordToSave);
		    writer.close( );
		    JOptionPane.showMessageDialog(new JFrame(), "The Message was Saved Successfully!",
		                "Success!", JOptionPane.INFORMATION_MESSAGE);
		    }
		    catch (IOException e)
		    {
		    JOptionPane.showMessageDialog(new JFrame(), "The Text could not be Saved!",
		                "Error!", JOptionPane.INFORMATION_MESSAGE);
		    }
		}
	}
	
}
