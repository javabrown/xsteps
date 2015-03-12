package com.jbrown;

import java.awt.AWTException;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 




import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.jbrown.robo.XEventI;
import com.jbrown.robo.impl.BrownRobot;
import com.jbrown.robo.impl.EventRecorder;
import com.jbrown.robo.impl.EventRepeater;
 

public class MinRunner {
	public static void main(String[] args) throws InterruptedException, AWTException{
//		EventRecorder recorder = new EventRecorder();
//		
//		recorder.startRecording();
//		new Thread().sleep(10000);
//		recorder.stopRecording();
//		
//		EventRepeater repeater = new EventRepeater(recorder._finder.getScenario());
//		repeater.trigger(new Robot());
//	 
//		System.out.println("DONE!!!!!");
		new WinRobo();
	}
}

class WinRobo extends JFrame implements ActionListener {
	EventRecorder recorder;
	EventRepeater repeater;
 
	private JLabel statusLog;
	private JButton record;
	private JButton execute;
	private JButton save;
	private JButton reset;

	private static final String EXECUTE = "Execute";
	private static final String RECORD = "Record";
	private static final String RESET = "Re-Set";
	private static final String STOP_RECORDING = "Stop Recording";
	private static final String SAVE = "Save";

	public WinRobo() throws AWTException {
		this.buildUI();
		this.addEvent();
		this.initializeScanner();
	}
	
	public void setLiveStatus(String status){
		statusLog.setText(status);
	}
	
	private void initializeScanner(){
		 recorder = new EventRecorder();
		 repeater = new EventRepeater(recorder._finder.getScenario());
		 
		 Thread t = new Thread(){
			 @Override
			 public void run(){
				 while(true){
				  XEventI live = recorder.getLiveEvent();
				  
				  if(live!= null){
				    System.out.println("Recorder Live = " + live);
				  }
				 }
			 }
		 };
		 t.start();
	}
	
	private void buildUI(){
		setSize(100, 150);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(3);
		setAlwaysOnTop(true);
		setAutoRequestFocus(true);
		
		statusLog = new JLabel();
		statusLog.setFont(new Font("times roman", Font.PLAIN, 9));
 
		record = new JButton(RECORD);
		record.setActionCommand(RECORD);
		
		execute = new JButton(EXECUTE);
		execute.setActionCommand(EXECUTE);
		
		reset = new JButton(RESET);
		reset.setActionCommand(RESET);

		save = new JButton(SAVE);
		save.setActionCommand(SAVE);
		
		getContentPane().setLayout(new GridLayout(5, 1, 1, 1));
		getContentPane().add(statusLog);
		getContentPane().add(record);
		getContentPane().add(execute);
		getContentPane().add(reset);
		getContentPane().add(save);
	}
	
	public void addEvent(){
		record.addActionListener(this);
		execute.addActionListener(this);
		reset.addActionListener(this);
		save.addActionListener(this);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == RECORD){
			record.setText(STOP_RECORDING);
			record.setActionCommand(STOP_RECORDING);
			recorder.startRecording();
		}
		
		if(e.getActionCommand() == STOP_RECORDING){
			record.setText(RECORD);
			record.setActionCommand(RECORD);
			recorder.stopRecording();
		}
		
		if(e.getActionCommand() == RESET){
			recorder.resetRecording();
		}
		
		if(e.getActionCommand() == SAVE){
			saveRecordToFile(recorder.save());
		}
		
		if(e.getActionCommand() == EXECUTE){
			try {
				repeater.trigger(new BrownRobot());
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (AWTException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	private void saveRecordToFile(String recordToSave){
		JFileChooser saver = new JFileChooser("./");
		int returnVal = saver.showSaveDialog(this);
		File file = saver.getSelectedFile();
		BufferedWriter writer = null;
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
		    try
		    {
		    writer = new BufferedWriter( new FileWriter( file.getAbsolutePath()+".txt"));
		    writer.write(recordToSave);
		    writer.close( );
		    JOptionPane.showMessageDialog(this, "The Message was Saved Successfully!",
		                "Success!", JOptionPane.INFORMATION_MESSAGE);
		    }
		    catch (IOException e)
		    {
		    JOptionPane.showMessageDialog(this, "The Text could not be Saved!",
		                "Error!", JOptionPane.INFORMATION_MESSAGE);
		    }
		}
	}
}