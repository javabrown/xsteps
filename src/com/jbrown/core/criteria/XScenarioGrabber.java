package com.jbrown.core.criteria;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.accessibility.Accessible;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.*;

public class XScenarioGrabber extends JFrame {
	public XScenarioGrabber(){
		setSize(300, 80);
		JPanel jp = (JPanel) getContentPane();
		jp.setLayout(new GridLayout(1, 1));
		
		jp.add(new XScenarioGrabberUI("Clipboard Live :"));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args){
		new XScenarioGrabber();
	}
}

class XScenarioGrabberUI extends JPanel implements Accessible, Observer {
	private JLabel _label;
	private JTextField _eventInfo;
	private JTextField _clipboard;
	private JButton _button;

	private ClipboardTracker _tracker;
	
	public XScenarioGrabberUI(String caption){
		_tracker = new ClipboardTracker();
		this.add(createUI(caption), BoxLayout.X_AXIS);
		
		_tracker.addObserver(this);
		_tracker.start();
	}
	
	public void addActionListener(ActionListener listener, String action) {
		_button.addActionListener(listener);
		_button.setActionCommand(action);
	}
	
	private JPanel createUI(String caption) {
		JPanel jp = new JPanel();
		jp.setLayout(new GridLayout(1,4,2,0));
		 
	    _label = new JLabel(caption+" ", SwingConstants.CENTER);
	    _eventInfo = new JTextField();
	    _clipboard = new JTextField();
	    _button = new JButton("GO");
	    
	    jp.add(_label);
	    jp.add(_eventInfo);
	    jp.add(_clipboard);
	    jp.add(_button);
	    //jp.setPreferredSize(new Dimension(10, 10));
	  
	    jp.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
	    
	    return jp;
	}
	

	@Override
	public void update(Observable o, Object arg) {
	  System.out.println(arg);	 
	  if(arg instanceof String){
		  _clipboard.setText((String)arg);
	  }
	}
}

class ClipboardTracker extends Observable implements Runnable {
	private String _clipboardValue;
	private Clipboard _clipboard;
	private boolean _isRunning;
	private Thread _runner;
	
	public ClipboardTracker(){
		_clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		_isRunning = true;
		_runner = new Thread(this);
	}
	
	public void start(){
		_isRunning = true;
		_runner.start();
	}
	
	
	public void stop(){
		_isRunning = false;
	}
	
	
	@Override
	public synchronized void addObserver(Observer o) {
		super.addObserver(o);
	}

	@Override
	public synchronized void deleteObserver(Observer o) {
		super.deleteObserver(o);
		_isRunning = false;
	}
	
	private String getClipboardContents() {
		String result = "";
		
		// odd: the Object param of getContents is not currently used
		Transferable contents = _clipboard.getContents(null);
		boolean hasTransferableText = (contents != null)
				&& contents.isDataFlavorSupported(DataFlavor.stringFlavor);
		if (hasTransferableText) {
			try {
				result = (String) contents
						.getTransferData(DataFlavor.stringFlavor);
			} catch (UnsupportedFlavorException | IOException ex) {
				System.out.println(ex);
				ex.printStackTrace();
			}
		}

		return result;
	}
	
	public boolean isClipboardChanged() {
		String newValue = this.getClipboardContents();

		if(newValue == null || newValue.length() == 0){
			return false;
		}
		
		if (newValue != null && _clipboardValue != null
				&& newValue.equals(_clipboardValue)) {
			return false;
		}
		
		_clipboardValue = newValue;
		
		return true;
	}

	@Override
	public void run() {
		while(_isRunning){
			sleep(1000);
			if(isClipboardChanged()){
				setChanged();
				notifyObservers(_clipboardValue);
			}
		}
	}
	
	private void sleep(long milis) {
		try {
			Thread.sleep(milis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}


