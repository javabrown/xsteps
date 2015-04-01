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
import javax.swing.SwingUtilities;

import com.jbrown.robo.XEventI;
import com.jbrown.robo.impl.BrownRobot;
import com.jbrown.robo.impl.EventE;
import com.jbrown.robo.impl.EventRecorder;
import com.jbrown.robo.impl.EventRepeater;
import com.jbrown.robo.impl.XKeyEvent;
import com.jbrown.ui.AppDataObserver;

public class XStepController extends Observable {
	private EventRecorder _recorder;
	private EventRepeater _repeater;
 
	private AppDataObserver _appDataObserver;
	
//	public XStepController() {
//		_recorder = null;
//		_repeater = null;
//		_appDataObserver = new AppDataObserver();
//	}
	 
	public void setAppDataObserver(AppDataObserver observer){
		_appDataObserver = observer;
	}
	
	public AppDataObserver getAppDataObserver(){
		return _appDataObserver;
	}
	
	//launcher injected
	private void initializeScanner() {
		_recorder = new EventRecorder();
		_repeater = new EventRepeater(_recorder.getXScenario());
		//_appDataObserver = new AppDataObserver();
		
		Thread t = new Thread() {
			@Override
			public void run() {
				while (true) {
					XEventI live = _recorder.getLiveEvent();

					if (live != null) {
						if (live.getEvent().typeOf(EventE.KEY_RELEASE)
								| live.getEvent().typeOf(EventE.MOUSE_RELEASE)) {
							try {
								setChanged();
								notifyObservers(live);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
							System.out.println("Recorder Live = " + live);
						}

					}
				}
			}
		};
		t.start();
	}

	public void startRecording() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_recorder.startRecording();
			}
		});
	}

	public void stopRecording() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_recorder.stopRecording();
			}
		});
	}

	public void reset() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_recorder.resetRecording();
			}
		});
	}

	public void save() {
		saveRecordToFile(_recorder.save());
	}

	public void execute() {
		int nRepeat = _appDataObserver.getNRepeat();
		System.out.printf("MAX Repeat Scenario =%s Begin ", nRepeat);
		
		try {
			BrownRobot robo = new BrownRobot();
			for (int i = 0; i < nRepeat; i++) {
				_repeater.trigger(robo);
			}
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.printf("**** [%s Scenario Repeat Finished !!] *****", nRepeat);
	}
 
	public void repeat() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				repeatRecordedScenario();
			}
		});
	}
	
	private final void repeatRecordedScenario() {
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

	private void saveRecordToFile(String recordToSave) {
		JFileChooser saver = new JFileChooser("./");
		int returnVal = saver.showSaveDialog(new JFrame());
		File file = saver.getSelectedFile();
		BufferedWriter writer = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				writer = new BufferedWriter(new FileWriter(
						file.getAbsolutePath() + ".txt"));
				writer.write(recordToSave);
				writer.close();
				JOptionPane.showMessageDialog(new JFrame(),
						"The Message was Saved Successfully!", "Success!",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(new JFrame(),
						"The Text could not be Saved!", "Error!",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

}
