package com.jbrown.ui.controller;

import java.awt.AWTException;
import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.jbrown.core.activity.ActivityI;
import com.jbrown.observers.AppDataObserver;
import com.jbrown.robo.XEventI;
import com.jbrown.robo.impl.BrownRobot;
import com.jbrown.robo.impl.EventE;
import com.jbrown.robo.impl.EventRecorder;
import com.jbrown.robo.impl.EventRepeater;
import com.jbrown.robo.impl.XStepLocalEventScanner;
import com.jbrown.ui.XDialog;

public class XStepController extends Observable {
	private EventRecorder _recorder;
	private EventRepeater _repeater;
 
	private AppDataObserver _appDataObserver;
	private ActivityI _activity;
	
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
	
	public void setActivity(ActivityI activity){
		_activity = activity;
	}
	
	public ActivityI getActivity(){
		return _activity;
	}
	
	
	//launcher injected
	private void initializeScanner() {
		_recorder = new EventRecorder();
		_repeater = new EventRepeater(_recorder.getXScenario(), this.getActivity());
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
				XStepLocalEventScanner.getInstance().start();
			}
		});
	}

	public void stopRecording() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_recorder.stopRecording();
				XStepLocalEventScanner.getInstance().stop();
			}
		});
	}

	public void reset() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				_recorder.resetRecording();
				XStepLocalEventScanner.getInstance().stop();
			}
		});
	}

	public void save() {
		saveRecordToFile(_recorder.save());
	}

	public void execute() {
		SwingUtilities.invokeLater(new Runnable() {
			int nRepeat = _appDataObserver.getNRepeat();
			boolean fastFarward = _appDataObserver.getViewCriteria()
					.isFastForward();
			
			@Override
			public void run() {
				try {
					_repeater.trigger(new BrownRobot(), nRepeat, fastFarward);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
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
			_repeater.trigger(new BrownRobot(), _appDataObserver.getNRepeat(),
					_appDataObserver.getViewCriteria().isFastForward());
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
