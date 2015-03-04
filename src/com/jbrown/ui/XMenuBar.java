package com.jbrown.ui;

import javax.swing.JMenuBar;

public class XMenuBar extends JMenuBar {
	public XMenuBar(){
		super();
	}
	
	public void setXMenus(XMenu[] menus) {
		for (XMenu xMenu : menus) {
			super.add(xMenu);
		}
	}
}
