package com.jbrown.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.MenuItem;
import java.awt.SystemTray;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.accessibility.Accessible;
import javax.swing.JButton;

abstract class AbstractEventManager extends MouseAdapter implements
		ActionListener, ItemListener {

	
	public void triggerClickEventOnButtonWithCommandName(Container c,
			String lookupCommand) {
		List<JButton> list = new ArrayList<JButton>();

		Component[] components = c.getComponents();
		for (Component com : components) {

			if (com instanceof JButton) {
				if (((JButton) com).getActionCommand().equals(lookupCommand)) {
					 ((JButton)com).doClick();
				}
			}
		}
	}
}