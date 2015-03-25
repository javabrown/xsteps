package com.jbrown.ui;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

public class XMenuItem extends JMenuItem implements ActionListener {
	private XTemplateI _action;
	
	public XMenuItem(){
		super();
	}
	
	public void setMnemonics(String mnemonic){
		super.setMnemonic(mnemonic.charAt(0));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		_action.trigger();
	}
}

 
 