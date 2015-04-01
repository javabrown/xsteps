package com.jbrown.ui;

import java.awt.Dimension;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

abstract class XStepApplication extends JFrame {
	private Dimension _dimension;
	private XMenuBar _menuBar;
	private XTemplate[] _xViews;

	public XStepApplication() {
		int state = this.getExtendedState();
		state = state | this.ICONIFIED;
		this.setExtendedState(state);
		this.setIcon();
		this.setTitle("XStep - Activity Recorder");
		super.setDefaultCloseOperation(HIDE_ON_CLOSE);
	}

	public void launch() {
		super.setSize(_dimension);
		super.setJMenuBar(_menuBar);
		super.setContentPane(_xViews[0].getXDesktop());
		super.setVisible(true);
	}
	
	private void setIcon() {
		try {
			List<Image> icons = new ArrayList<Image>();

			InputStream inputStream = this.getClass().getResourceAsStream(
					"/icons/brown-logo.png");
			BufferedInputStream in = new BufferedInputStream(inputStream);
			Image image = ImageIO.read(in);
			icons.add(image);
			this.setIconImages(icons);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void setDimension(Dimension dimension) {
		_dimension = dimension;
	}

	public void setXMenuBar(XMenuBar menuBar) {
		_menuBar = menuBar;
	}

	public XTemplate[] getXViews() {
		return _xViews;
	}

	public void setXViews(XTemplate[] xAction) {
		_xViews = xAction;
	}
}
