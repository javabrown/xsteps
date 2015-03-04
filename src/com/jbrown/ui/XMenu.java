package com.jbrown.ui;

import java.awt.MenuBar;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class XMenu extends JMenu {	
	public XMenu(){
		super();
	}
	
	public void setMenuOptions(XMenuItem[] options){
		for(XMenuItem option :  options){
			this.add(option);
		}
	}
	
	public void setMnemonics(String mnemonic){
		super.setMnemonic(mnemonic.charAt(0));
	}
}
