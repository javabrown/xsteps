package com.jbrown.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

class Progress extends MouseMotionAdapter implements ProgressI {
	JWindow _window;
	JLabel _label;

	public Progress() {
		_window = new JWindow();
		_label = new JLabel("", SwingConstants.CENTER);

		JPanel panel = (JPanel) _window.getContentPane();
		panel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		panel.add(_label, BorderLayout.CENTER);
		JButton b = new JButton("Close");
		panel.add(b, BorderLayout.SOUTH);

		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hide();
			}
		});

		_window.setAlwaysOnTop(true);
		_window.pack();
		_window.setBounds(50, 50, 200, 200);
		addEvent();
	}

	private void addEvent() {
		_window.addMouseMotionListener(this);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		_window.setLocation(e.getXOnScreen(), e.getYOnScreen());
	}

	@Override
	public final void show() {
		_window.setVisible(true);
	}

	@Override
	public final void hide() {
		_window.setVisible(false);
	}

	@Override
	public void setText(String text) {
		_label.setText(text);

	}

	@Override
	public String getText() {
		return _label.getText();
	}

	public void setLocationOnScreen(int x, int y) {
		_window.setLocation(x, y);
	}
}