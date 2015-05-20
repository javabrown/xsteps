package com.jbrown.util;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;

public class SmartWorker implements Runnable {
	private static Robot _robot;
	private static boolean _isRunning = false;
	private static SmartWorker _instance;

	public static SmartWorker getInstance() {
		if (_instance == null) {
			_instance = new SmartWorker();
		}

		return _instance;
	}

	private SmartWorker() {
		try {
			_robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}

		_isRunning = false;
	}

	public synchronized void stop() {
		_isRunning = false;
	}

	private synchronized void moveNowhere() {
		PointerInfo a = MouseInfo.getPointerInfo();
		Point b = a.getLocation();
		int x = (int) b.getX();
		int y = (int) b.getY();
		_robot.mouseMove(x + 1, y);
		_robot.mouseMove(x, y);
	}

	@Override
	public void run() {
		_isRunning = true;
		System.out.println("Smart Thread Started!!");
		while (_isRunning) {
			moveNowhere();

			try {
				Thread.sleep(200);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println("Smart Thread Stoped!!");
	}
}
