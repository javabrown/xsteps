package com.jbrown.ui;

import java.awt.Dimension;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.jfree.ui.RefineryUtilities;

abstract class XStepApplication extends JFrame {
	private Dimension _dimension;
	private XMenuBar _menuBar;
	private XDesktop _container;
	private XAction[] _xAction;

	public XStepApplication() {
		int state = this.getExtendedState();
		state = state | this.ICONIFIED;
		this.setExtendedState(state);
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void launch() {
		super.setSize(_dimension);
		super.setJMenuBar(_menuBar);
		super.setContentPane(_container);
		
		//=====
	     final RealTimeWatch watch = new RealTimeWatch();
	     RefineryUtilities.centerFrameOnScreen(this);
	     _container.getMiddleSector().add(watch);
	     watch.monitor();
		//=====
		super.setVisible(true);
	}
	
	private void setIcon() {
		try {
			List<Image> icons = new ArrayList<Image>();

			InputStream inputStream = this.getClass().getResourceAsStream(
					"brown-logo.png");
			BufferedInputStream in = new BufferedInputStream(inputStream);
			Image image = ImageIO.read(in);

			icons.add(image);
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

	public void setXDesktop(XDesktop desktop) {
		_container = desktop;
	}

	public void setXAction(XAction[] xAction) {
		_xAction = xAction;
	}
}
