package com.jbrown.ui;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import com.jbrown.ui.controller.XStepController;

import static com.jbrown.robo.KeysI.*;

public class XStepRecordAction implements XAction.XStepActions {
	private XEventGraph _xEventGraph;
	private XSector _middleSector;
	
	private XCommand[] _commands;
	private XSector _commandSector;
	
	private XStepController _controller;

	
	public void setMiddleSector(XSector middleSector) {
		_middleSector = middleSector;
	}
	
	public void setEventGraph(XEventGraph eventGraph) {
		_xEventGraph = eventGraph;
	}
	
	public void setXSector(XSector xSector) {
		_commandSector = xSector;
	}

	public void setXCommands(XCommand[] commands) {
		_commands = commands;
	}

	public void setXController(XStepController controller) {
		_controller = controller;
	}
	
	@Override
	public void pushXView() {
		for (XCommand act : _commands) {
			JButton button = new JButton(act.getCommand());
			button.setActionCommand(act.getCommand());
			button.addActionListener(this);
			_commandSector.add(button);
		}
		
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
			_controller.execute();
		}
	}
}
