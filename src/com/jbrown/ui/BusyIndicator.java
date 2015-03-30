package com.jbrown.ui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.plaf.LayerUI;

import com.jbrown.Main;
 
public class BusyIndicator {
	private BusyIndicatorUI _busyIndicator;
	
	final Timer stopper = new Timer(4000, new ActionListener() {
		public void actionPerformed(ActionEvent ae) {
			_busyIndicator.stop();
		}
	});
	
	public BusyIndicator(){
		_busyIndicator = new BusyIndicatorUI();
	}
	
	public void start(){
		_busyIndicator.start();
		if (!stopper.isRunning()) {
			stopper.start();
		}
	}
	
	public void stop(){
		_busyIndicator.stop();
	}
}

class BusyIndicatorUI extends LayerUI<JPanel> implements
		ActionListener {
	private boolean mIsRunning;
	private boolean mIsFadingOut;
	private Timer mTimer;

	private int mAngle;
	private int mFadeCount;
	private int mFadeLimit = 15;

	@Override
	public void paint(Graphics g, JComponent c) {
		int w = c.getWidth();
		int h = c.getHeight();

		// Paint the view.
		super.paint(g, c);
		this.decorateUI(g, c);
		
		if (!mIsRunning) {
			return;
		}

		Graphics2D g2 = (Graphics2D) g.create();

		float fade = (float) mFadeCount / (float) mFadeLimit;
		// Gray it out.
		Composite urComposite = g2.getComposite();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				.5f * fade));
		g2.fillRect(0, 0, w, h);
		g2.setComposite(urComposite);

		// Paint the wait indicator.
		int s = Math.min(w, h) / 5;
		int cx = w / 2;
		int cy = h / 2;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(s / 4, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));
		g2.setPaint(Color.white);
		g2.rotate(Math.PI * mAngle / 180, cx, cy);
		for (int i = 0; i < 12; i++) {
			float scale = (11.0f - (float) i) / 11.0f;
			g2.drawLine(cx + s, cy, cx + s * 2, cy);
			g2.rotate(-Math.PI / 6, cx, cy);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					scale * fade));
		}

		g2.dispose();
	}

	public void actionPerformed(ActionEvent e) {
		if (mIsRunning) {
			firePropertyChange("tick", 0, 1);
			mAngle += 3;
			if (mAngle >= 360) {
				mAngle = 0;
			}
			if (mIsFadingOut) {
				if (--mFadeCount == 0) {
					mIsRunning = false;
					mTimer.stop();
				}
			} else if (mFadeCount < mFadeLimit) {
				mFadeCount++;
			}
		}
	}

	public void start() {
		if (mIsRunning) {
			return;
		}

		// Run a thread for animation.
		mIsRunning = true;
		mIsFadingOut = false;
		mFadeCount = 0;
		int fps = 24;
		int tick = 1000 / fps;
		mTimer = new Timer(tick, this);
		mTimer.start();
	}

	public void stop() {
		mIsFadingOut = true;
	}

	@Override
	public void applyPropertyChange(PropertyChangeEvent pce, JLayer l) {
		if ("tick".equals(pce.getPropertyName())) {
			l.repaint();
		}
	}
	
	private void decorateUI(Graphics g, JComponent c){
		Graphics2D g2 = (Graphics2D) g.create();
		int w = c.getWidth();
		int h = c.getHeight();
		BufferedImage mazeImage = null;
				//new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		try {
			URL brownLogoUrl = Main.class.getClassLoader()
				      .getResource("com/jbrown/resources/brown-logo.png");
			mazeImage = ImageIO.read(brownLogoUrl);;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f));
		g2.setPaint(new GradientPaint(0, 0, Color.white, 0, h, Color.gray));
		g2.fillRect(0, 0, w, h);
		
		int img_x = (w - mazeImage.getWidth(null)) / 2;
	    int img_y = (h - mazeImage.getHeight(null)) / 2;
	    
		g2.drawImage(mazeImage, img_x, img_y, null);
		
		g2.dispose();
	}
	
	private void drawGrid(Graphics2D g2, JComponent c, int w, int h) {
		BasicStroke bs = new BasicStroke(2);
		g2.setStroke(bs);
		// draw the black vertical and horizontal lines
		for (int i = 0; i < 21; i++) {
			// unless divided by some factor, these lines were being
			// drawn outside the bound of the image..
			g2.drawLine((w + 2) / 20 * i, 0, (w + 2) / 20 * i, h - 1);
			g2.drawLine(0, (h + 2) / 20 * i, w - 1, (h + 2) / 20 * i);
		}
	}
}