package com.jbrown.ui;

import static com.jbrown.robo.KeysI.CAPTION_STOP_RECORDING_K;
import static com.jbrown.robo.KeysI.COMMAND_EXIT_K;
import static com.jbrown.robo.KeysI.COMMAND_RECORD_K;
import static com.jbrown.robo.KeysI.COMMAND_REPEAT_K;
import static com.jbrown.robo.KeysI.COMMAND_RESET_K;
import static com.jbrown.robo.KeysI.COMMAND_SAVE_K;
import static com.jbrown.robo.KeysI.COMMAND_STOP_RECORDING_K;
import static com.jbrown.robo.KeysI.COMMAND_VERIFY_K;

import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.SwingUtilities;

import com.jbrown.robo.KeysI;
import com.jbrown.ui.controller.XStepController;

public class XStepRecorderEventManager extends AbstractEventManager {
	private XStepController _controller;

	public XStepRecorderEventManager(XStepController controller) {
		_controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Event Triggered=" + e.getActionCommand());
		Object eventSource = e.getSource();

		if (e.getActionCommand().equalsIgnoreCase(COMMAND_RECORD_K)) {
			switchAction(eventSource, COMMAND_STOP_RECORDING_K,
					CAPTION_STOP_RECORDING_K);
			_controller.startRecording();
		}

		if (e.getActionCommand().equalsIgnoreCase(COMMAND_STOP_RECORDING_K)) {
			switchAction(eventSource, COMMAND_RECORD_K, COMMAND_RECORD_K);
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
					_controller.execute();
				}
			});

		}

		if (e.getActionCommand().equalsIgnoreCase(COMMAND_VERIFY_K)) {
			_controller.save();
		}

		if (e.getActionCommand().equalsIgnoreCase(COMMAND_EXIT_K)) {
			System.exit(0);
		}
	}

	private void switchAction(Object eventSource, String actionCommand,
			String text) {
		if (eventSource instanceof JButton) {
			JButton component = (JButton) eventSource;
			component.setText(text);
			component.setActionCommand(actionCommand);
		}

		if (eventSource instanceof MenuItem) {
			MenuItem component = (MenuItem) eventSource;
			component.setLabel(text);
			component.setActionCommand(actionCommand);
		}

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		JCheckBox check = (JCheckBox)e.getSource();
		boolean isSelected = check.isSelected();
		
        String name = check.getName();
        
        if(name.equalsIgnoreCase(KeysI.CHECK_FAST_FORWARD_K)){
        	_controller.getAppDataObserver().getViewCriteria().setFastForward(isSelected);
        }
		
	}
}
