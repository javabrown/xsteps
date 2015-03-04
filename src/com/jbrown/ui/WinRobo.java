package com.jbrown.ui;

import java.awt.AWTException;
import java.awt.Button;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;


import de.ksquared.system.keyboard.GlobalKeyListener;
import de.ksquared.system.keyboard.KeyAdapter;
import de.ksquared.system.keyboard.KeyEvent;
import de.ksquared.system.mouse.GlobalMouseListener;
import de.ksquared.system.mouse.MouseAdapter;

public class WinRobo extends JFrame implements ActionListener {
	private JLabel statusLog;
	private JButton record;
	private JButton execute;
	
	private static final String EXECUTE = "Execute";
	private static final String RECORD = "Record";
	private static final String STOP_RECORDING = "Stop Recording";
	
	private Recorder recorder;
	private Robot robot;
	
	private void buildUI(){
		setSize(100, 100);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(3);
		setAlwaysOnTop(true);
		setAutoRequestFocus(true);
		
		statusLog = new JLabel();
		statusLog.setFont(new Font("times roman", Font.PLAIN, 9));
		statusLog.setText(recorder.liveStatus);
		
		record = new JButton(RECORD);
		record.setActionCommand(RECORD);
		
		execute = new JButton(EXECUTE);
		execute.setActionCommand(EXECUTE);
		
		getContentPane().setLayout(new GridLayout(3, 1, 1, 1));
		getContentPane().add(statusLog);
		getContentPane().add(record);
		getContentPane().add(execute);
	}
	
	public void addEvent(){
		record.addActionListener(this);
		execute.addActionListener(this);
		//this.addMouseListener(recorder);
		new GlobalMouseListener().addMouseListener(recorder);
		this.addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseMoved(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseMoved(e);
					System.out.println("MOVED");
				}
		});
	}
	
	public WinRobo() throws AWTException {
		recorder = new Recorder();
		robot = new Robot();
		this.buildUI();
		this.addEvent();
	}

	public static void main(String[] args) throws AWTException {
		//Robot r = new Robot();
		WinRobo m = new WinRobo();

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
		
		if(e.getActionCommand() == EXECUTE){
			recorder.executeRecords(robot);
		}
	}
}


class Recorder extends MouseAdapter {//MouseInputAdapter {
	List<ClickRecord> list;
	Date lastClickOnDate;
	static boolean recorderFlagOn;
	
	public static String liveStatus;
	
	public Recorder() {
		list = new ArrayList<ClickRecord>();
		lastClickOnDate = null;
		recorderFlagOn = false;
		liveStatus = "";
		
		//GlobalKeyListener
		//addTimesNowVoteStep();
		//System.out.println("TimesNow Scenario Saved.");
		 
	}
	
	public void addTimesNowVoteStep(){
		addRecord(1116, 610);
		try {
			new Thread().sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addRecord(722,68);
		
		ClickRecord[] record = list.toArray(new ClickRecord[0]);
		int i=0;
		
		for(; i<70000; i++){
			list.add(record[0]); System.out.println(record[0].toString());
			list.add(record[1]); System.out.println(record[1].toString());
		}
		
		System.out.println("TimesNow Vote - "+ i +" scenario saved.");
	}

	public void startRecording(){ 
		recorderFlagOn = true;
	}
	
	public void startKeyboard(){
		new GlobalKeyListener().addKeyListener(new KeyAdapter() {
			@Override public void keyPressed(KeyEvent event) { System.out.println(event); }
			@Override public void keyReleased(KeyEvent event) {
				System.out.println(event);
				if(event.getVirtualKeyCode()==KeyEvent.VK_ADD
				&& event.isCtrlPressed())
					System.out.println("CTRL+ADD was just released (CTRL is still pressed)");
			}
		});
	}
	
	public void stopRecording(){
		recorderFlagOn = false;
	}
	
	public void executeRecords(Robot r){
		for(ClickRecord clickRecord : list){
			clickRecord.execute(r);
		}
	}
	
	public void addRecord(int x, int y) {
		long delay = 0;
		
		if (this.lastClickOnDate == null) {
			this.lastClickOnDate = new Date();
			delay = 0;
		} else {
			delay = getDifference(this.lastClickOnDate, new Date());
			this.lastClickOnDate = new Date();
		}

		list.add(new ClickRecord(delay, x, y));
	}

	public long getDifference(Date startDate, Date endDate) {
		long diff = 0;

		Calendar c1 = Calendar.getInstance();
		c1.setTime(startDate);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(endDate);

		long ms1 = c1.getTimeInMillis();
		long ms2 = c2.getTimeInMillis();

		diff = ms2 - ms1;

		return diff;
	}
	
	@Override
	public void mouseReleased(de.ksquared.system.mouse.MouseEvent e) {
	if(!recorderFlagOn) return;
		
		System.out.println("Click event saved at " + e.getX() + "  :  "
				+ e.getY());
		liveStatus = "Saved Event: " + e.getX() + " : " + e.getY();
		
		this.addRecord(e.getX(), e.getY());
		
		super.mouseReleased(e);
	}
	
//	@Override
//	public void mouseClicked(MouseEvent e) {
//		if(!recorderFlagOn) return;
//		
//		System.out.println("Click event saved at " + e.getXOnScreen() + "  :  "
//				+ e.getYOnScreen());
//		liveStatus = "Saved Event: " + e.getXOnScreen() + " : " + e.getYOnScreen();
//		
//		this.addRecord(e.getXOnScreen(), e.getYOnScreen());
//	}
}

class ClickRecord {
	long delay;
	int x;
	int y;

	public ClickRecord(long delay, int x, int y) {
		this.delay = delay;
		this.x = x;
		this.y = y;
	}
	
	public void execute(Robot r){
		try {
			new Thread().sleep(delay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		r.mouseMove(x, y);
		r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		
		System.out.printf("click event on x=%d , y=%d , delay=%d  executed.\n", x, y, delay);
	}

	@Override
	public String toString() {
		return "ClickRecord [delay=" + delay + ", x=" + x + ", y=" + y + "]";
	}
}
