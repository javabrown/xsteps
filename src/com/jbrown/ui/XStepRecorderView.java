package com.jbrown.ui;

import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import com.jbrown.robo.KeysI;
import com.jbrown.ui.controller.XStepController;

import static com.jbrown.robo.KeysI.*;

public class XStepRecorderView extends AbstractView {
	//private BusyIndicator _busyIndicator;
	private XEventGraph _xEventGraph;
	private XStepController _controller;
	private XCommand[] _commands;
	private SpinnerButton _spinnerButton;
	private JCheckBox _fastForwardCheck;
	
	public void setEventGraph(XEventGraph eventGraph) {
		_xEventGraph = eventGraph;
	}

	public void setXCommands(XCommand[] commands) {
		_commands = commands;
	}

	public void setXController(XStepController controller) {
		_controller = controller;
	}

	private void initCustomView() {
		_spinnerButton = new SpinnerButton(KeysI.COMMAND_REPEAT_K);
		_spinnerButton.addActionListener(super.getEventManager(), KeysI.COMMAND_REPEAT_K);
		
		_fastForwardCheck = new JCheckBox();
		
		super.getXDesktop().getSouthSector().add(_spinnerButton);
		super.getXDesktop().getSouthSector().add(new JLabel(KeysI.CHECK_FAST_FORWARD_K));
		super.getXDesktop().getSouthSector().add(_fastForwardCheck);
		
		this.getSystemTray().launch(super.getXDesktop());
	}
  
//	private void launchSystemTray() {
//		if (!SystemTray.isSupported()) {
//			System.out.println("System tray is not supported !!! ");
//			return;
//		}
//
//		SystemTray systemTray = SystemTray.getSystemTray();
//		String path = "/icons/brown-logo.png";
//
//		URL resource =  Main.class.getResource(path);
//		Image image = Toolkit.getDefaultToolkit().getImage(resource); 
//
//		PopupMenu trayPopupMenu = new PopupMenu();
// 
//		MenuItem record = new MenuItem(COMMAND_RECORD_K);
//		record.setActionCommand(COMMAND_RECORD_K);
//		record.addActionListener(this);
//	 
//		MenuItem reset = new MenuItem(COMMAND_RESET_K);
//		reset.setActionCommand(COMMAND_RESET_K);
//		reset.addActionListener(this);
//		
//		MenuItem save = new MenuItem(COMMAND_SAVE_K);
//		save.setActionCommand(COMMAND_SAVE_K);
//		save.addActionListener(this);
//
//		
//		MenuItem verify = new MenuItem(COMMAND_VERIFY_K);
//		verify.addActionListener(this);
//		verify.setActionCommand(COMMAND_VERIFY_K);
// 		
//		MenuItem exit = new MenuItem(COMMAND_EXIT_K);
//		exit.addActionListener(this);
//		exit.setActionCommand(COMMAND_EXIT_K);
//		
//		trayPopupMenu.add(record);
//		trayPopupMenu.addSeparator();
//		trayPopupMenu.add(reset);
//		trayPopupMenu.add(save);
//		trayPopupMenu.addSeparator();
//		trayPopupMenu.add(verify);
//		trayPopupMenu.addSeparator();
//		trayPopupMenu.add(exit);
//		
//		TrayIcon trayIcon = new TrayIcon(image, "XStep Running", trayPopupMenu);
//		trayIcon.setImageAutoSize(true);
//	 
//		trayIcon.addMouseListener(new MouseAdapter() {
//				@Override
//				public void mouseClicked(MouseEvent e) {
//					if(e.getClickCount() == 2){
//						getXDesktop().show();
//					}
//				}
//		});
//		  
//		try {
//			systemTray.add(trayIcon);
//		} catch (AWTException awtException) {
//			awtException.printStackTrace();
//		}
//
//	}
	
	@Override
	public void pushXView() {
		for (XCommand act : _commands) {
			JButton button = new JButton(act.getCommand());
			button.setActionCommand(act.getCommand());
			button.addActionListener(super.getEventManager());
			super.getXDesktop().getSouthSector().add(button);
		}

		this.initCustomView();

		super.getXDesktop().getMiddleSector().add(_xEventGraph);

		this.bindObservers();
		this.bindEventListener();
		
		_xEventGraph.startMonitor();
	}

	private void bindEventListener(){
		_fastForwardCheck.setName(KeysI.CHECK_FAST_FORWARD_K);
		_fastForwardCheck.addItemListener(super.getEventManager());
	}
	
	private void bindObservers(){
		_controller.addObserver(_xEventGraph);
		_spinnerButton.getNRepeatTracker().addObserver(_controller.getAppDataObserver());
	}
	
//	private void switchAction(Object eventSource, String actionCommand, String text){
//		if(eventSource instanceof JButton){
//			JButton component = (JButton) eventSource;
//			component.setText(text);
//			component.setActionCommand(actionCommand);
//		}
//		
//		if(eventSource instanceof MenuItem){
//			MenuItem component = (MenuItem) eventSource;
//			component.setLabel(text);
//			component.setActionCommand(actionCommand);
//		}
//		
//	}
	
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		System.out.println("Event Triggered=" + e.getActionCommand());
//		Object eventSource = e.getSource();
//	  
//
//		if (e.getActionCommand().equalsIgnoreCase(COMMAND_RECORD_K)) {
//			switchAction(eventSource, COMMAND_STOP_RECORDING_K, CAPTION_STOP_RECORDING_K);
//			_controller.startRecording();
//		}
//
//		if (e.getActionCommand().equalsIgnoreCase(COMMAND_STOP_RECORDING_K)) {
//			switchAction(eventSource, COMMAND_RECORD_K, COMMAND_RECORD_K);
//			_controller.stopRecording();
//		}
//
//		if (e.getActionCommand().equalsIgnoreCase(COMMAND_RESET_K)) {
//			_controller.reset();
//		}
//
//		if (e.getActionCommand().equalsIgnoreCase(COMMAND_SAVE_K)) {
//			_controller.save();
//		}
//
//		if (e.getActionCommand().equalsIgnoreCase(COMMAND_REPEAT_K)) {
//			SwingUtilities.invokeLater(new Runnable() {
//				@Override
//				public void run() {
//					int max = _spinnerButton.getIntValue();
//					System.out.println("MAX Repeat Scenario = " + max
//							+ " - Started !!");
//
//					for (int i = 0; i < max; i++) {
//						_controller.execute();
//						System.out.println("Scenario #" + i + " done!!");
//					}
//
//					System.out
//							.println("**** [Repeat Scenario Finished !!] *****");
//				}
//			});
//
//		}
//		
//		if (e.getActionCommand().equalsIgnoreCase(COMMAND_VERIFY_K)) {
//			_controller.save();
//		}
//		
//		if (e.getActionCommand().equalsIgnoreCase(COMMAND_EXIT_K)) {
//			System.exit(0);
//		}
//	}
}
