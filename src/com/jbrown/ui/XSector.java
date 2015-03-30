package com.jbrown.ui;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class XSector extends JPanel {
	public void setXBorder(boolean flag){
		super.setBorder(null);
	}
	public XSector(){
		super.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		 
	}
}
