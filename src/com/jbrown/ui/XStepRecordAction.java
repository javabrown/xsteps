package com.jbrown.ui;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

public class XStepRecordAction implements XAction.XStepActions {
	private XSector _sector;
	private XCommand[] _commands;
	
	public void setXSector(XSector xSector){
		_sector = xSector;
	}
	
	public void setXCommands(XCommand[] commands){
		_commands = commands;
	}
	
	@Override
	public void pushXCommand() {
		for(XCommand act : _commands){
			JButton button = new JButton(act.getCommand());
			button.setActionCommand(act.getShortcut());
			button.addActionListener(this);
			_sector.add(button);
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		System.out.println("Event Triggered=" + event.getActionCommand());
	}
}
