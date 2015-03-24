package com.jbrown.ui;

import javax.swing.JButton;

public class XStepControl {
	private XSector _sector;
	
	private JButton _record;
	private JButton _save;
	private JButton _repeat;
	
	 
	public XStepControl(){
		_sector = new XSector();
		
		JButton record = new JButton("Record");
		JButton save = new JButton("Save");
		JButton repeat = new JButton("Repeat");
		
		
		_sector.add(record);
		_sector.add(save);
		_sector.add(repeat);
	}
	
	
	
}
