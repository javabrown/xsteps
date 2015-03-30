package com.jbrown.ui;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import org.apache.commons.io.IOUtils;

import sun.launcher.resources.launcher;

import com.jbrown.robo.KeysI;
import com.jbrown.ui.controller.XStepController;

import static com.jbrown.robo.KeysI.*;

public class XStepRecorderView implements XTemplate.XStepView {
//	public static void main(String[] args) {
//		new XStepRecorderView().launchSystemTray();
//	}

	private  String getFileWithUtil(String fileName) {
		String result = "";
 
		try {
			result = IOUtils.toString(
				      this.getClass().getResourceAsStream(fileName),
				      "UTF-8"
				    );
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	private void launchSystemTray() {
		if (!SystemTray.isSupported()) {
			System.out.println("System tray is not supported !!! ");
			return;
		}

		SystemTray systemTray = SystemTray.getSystemTray();

		String path = "C:/git-repo/xsteps/src/com/jbrown/resources/brown-logo.png";// "com/jbrown/resources/icon_about.png";
		String imgFile = getFileWithUtil("brown-logo.png");
	 
		// Image image = new ImageIcon(img).getImage();
		Image image = Toolkit.getDefaultToolkit().getImage(path);
	 
		// popupmenu
		PopupMenu trayPopupMenu = new PopupMenu();

		// 1t menuitem for popupmenu
		MenuItem action = new MenuItem("Action");
		action.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Action Clicked");
			}
		});
		trayPopupMenu.add(action);

		// 2nd menuitem of popupmenu
		MenuItem close = new MenuItem("Close");
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//System.exit(0);
			}
		});
		trayPopupMenu.add(close);

		TrayIcon trayIcon = new TrayIcon(image, "SystemTray Demo",
				trayPopupMenu);
		trayIcon.setImageAutoSize(true);

		try {
			systemTray.add(trayIcon);
		} catch (AWTException awtException) {
			awtException.printStackTrace();
		}

		System.out.println("end of main");

	}// end of main

	private XEventGraph _xEventGraph;
	private XCommand[] _commands;
	private XSector _middleSector;
	private XSector _commandSector;

	private XStepController _controller;

	private SpinnerButton _spinnerButton;

	public void setEventGraph(XEventGraph eventGraph) {
		_xEventGraph = eventGraph;
	}

	public void setMiddleSector(XSector middleSector) {
		_middleSector = middleSector;
	}

	public void setSouthSector(XSector xSector) {
		_commandSector = xSector;
	}

	public void setXCommands(XCommand[] commands) {
		_commands = commands;
	}

	public void setXController(XStepController controller) {
		_controller = controller;
	}

	private void initCustomView() {
		_spinnerButton = new SpinnerButton(KeysI.COMMAND_REPEAT_K);
		_spinnerButton.addActionListener(this, KeysI.COMMAND_REPEAT_K);

		_commandSector.add(_spinnerButton);

		JCheckBox skipMoveCheck = new JCheckBox();

		_commandSector.add(new JLabel("Fast"));
		_commandSector.add(skipMoveCheck);
		this.launchSystemTray();
	}
  

	@Override
	public void pushXView() {
		for (XCommand act : _commands) {
			JButton button = new JButton(act.getCommand());
			button.setActionCommand(act.getCommand());
			button.addActionListener(this);
			_commandSector.add(button);
		}

		this.initCustomView();

		_middleSector.add(_xEventGraph);

		_controller.addObserver(_xEventGraph);
		_xEventGraph.startMonitor();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Event Triggered=" + e.getActionCommand());
		JButton component = (JButton) e.getSource();

		if (component == null) {
			return;
		}

		if (e.getActionCommand().equalsIgnoreCase(COMMAND_RECORD_K)) {
			component.setText(CAPTION_STOP_RECORDING_K);
			component.setActionCommand(COMMAND_STOP_RECORDING_K);
			_controller.startRecording();
		}

		if (e.getActionCommand().equalsIgnoreCase(COMMAND_STOP_RECORDING_K)) {
			component.setText(COMMAND_RECORD_K);
			component.setActionCommand(COMMAND_RECORD_K);
			_controller.stopRecording();
		}

		if (e.getActionCommand().equalsIgnoreCase(COMMAND_RESET_K)) {
			_controller.reset();
		}

		if (e.getActionCommand().equalsIgnoreCase(COMMAND_SAVE_K)) {
			_controller.save();
		}

		if (e.getActionCommand().equalsIgnoreCase(COMMAND_REPEAT_K)) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					int max = _spinnerButton.getIntValue();
					System.out.println("MAX Repeat Scenario = " + max
							+ " - Started !!");

					for (int i = 0; i < max; i++) {
						_controller.execute();
						System.out.println("Scenario #" + i + " done!!");
					}

					System.out
							.println("**** [Repeat Scenario Finished !!] *****");
				}
			});

		}
	}
}
