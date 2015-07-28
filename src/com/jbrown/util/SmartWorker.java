package com.jbrown.util;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.util.Date;

import org.springframework.format.datetime.DateFormatter;

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
		PointerInfo a = null;
		try {
			a = MouseInfo.getPointerInfo();
			if (a == null) {
				return;
			}
		} catch (HeadlessException ex) {
			System.err.println("Exception on getPointerInfo :"
					+ ex.getMessage());
			return;
		}

		Point b = a.getLocation();
		int x = (int) b.getX();
		int y = (int) b.getY();
		_robot.mouseMove(x + 1, y);
		_robot.mouseMove(x, y);
		System.out.println(new Date());
	}

	@Override
	public void run() {
		_isRunning = true;
		System.out.println("Smart Thread Started!!");
		while (_isRunning) {
			moveNowhere();

			try {
				Thread.sleep(10000);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println("Smart Thread Stoped!!");
	}
}
