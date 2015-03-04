package com.jbrown.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Launcher extends JFrame {
	private Dimension _dimension;
	private XMenuBar _menuBar;
	
	public Launcher() {
		Container contentPane = getContentPane();
		contentPane.add(new Desktop(), BorderLayout.CENTER);

		int state = this.getExtendedState();
		state = state | this.ICONIFIED;
		this.setExtendedState(state);
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void launch() {
		super.setSize(_dimension);
		super.setJMenuBar(_menuBar);
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
	
	public void setDimension(Dimension dimension){
		_dimension = dimension;
	}
	
	public void setXMenuBar(XMenuBar menuBar){
		_menuBar = menuBar;
	}
	
}
