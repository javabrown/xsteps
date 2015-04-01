package com.jbrown.ui;

import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.SpringLayout;
import javax.swing.border.EtchedBorder;

public class XDesktop extends JPanel {
	private static final long serialVersionUID = 1L;
	private XSector _middleSector;
	private XSector _eastSector;
	private XSector _westSector;
	private XSector _nothSector;
	private XSector _southSector;

	public XDesktop() {
		this.setLayout(new BorderLayout(10, 10));
		this.setBorder(new EtchedBorder(EtchedBorder.RAISED));
	}
	
	public void setMiddleSector(XSector sector) {
		_middleSector = sector;
		this.add(_middleSector, BorderLayout.CENTER);
	}

	public void setEastSector(XSector sector) {
		_eastSector = sector;
		_eastSector.setLayout(new SpringLayout());
		this.add(_eastSector, BorderLayout.EAST);
	}

	public void setWestSector(XSector sector) {
		_westSector = sector;
		this.add(_westSector, BorderLayout.WEST);
	}

	public void setNorthSector(XSector sector) {
		_nothSector = sector;
		this.add(_nothSector, BorderLayout.NORTH);
	}

	public void setSouthSector(XSector sector) {
		_southSector = sector;
		this.add(_southSector, BorderLayout.SOUTH);
	}

	public XSector getMiddleSector() {
		return _middleSector;
	}

	public XSector getEastSector() {
		return _eastSector;
	}

	public XSector getWestSector() {
		return _westSector;
	}

	public XSector getNothSector() {
		return _nothSector;
	}

	public XSector getSouthSector() {
		return _southSector;
	}
	
	public XStepApplication getOriginApp() {
		return (XStepApplication) SwingUtilities.getAncestorOfClass(
				XStepApplication.class, this);
	}
	
	public void show(){
		XStepApplication app = this.getOriginApp();
		app.setVisible(true);
	}
	
	public void hide(){
		XStepApplication app = this.getOriginApp();
		app.setVisible(false);
	}
}
