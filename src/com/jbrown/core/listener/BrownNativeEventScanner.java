package com.jbrown.core.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.SwingDispatchService;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

import com.jbrown.nativehook.BrownNativeEventScanner;
import com.jbrown.nativehook.BrownStream;
import com.jbrown.nativehook.GlobalBrownListener;
import com.jbrown.nativehook.NativeHookDemo;
 
class BrownNativeEventScanner {
	private GlobalBrownListener _listener;

	public BrownNativeEventScanner() {
		_listener = new GlobalBrownListener();
	}

	public void setEnable(boolean enable) {
		setBorwnNativeDllSwitch(enable);
		setBorwnKeyboardSwitch(enable);
		setBorwnMouseSwitch(enable);
	}

	private void setBorwnNativeDllSwitch(boolean start) {
		try {
			if (start) {
				GlobalScreen.registerNativeHook();
				setBorwnKeyboardSwitch(start);
			} else {
				GlobalScreen.unregisterNativeHook();
			}
		} catch (NativeHookException ex) {
			System.out.println("Error: " + ex.getMessage() + "\n");
		}
	}

	private void setBorwnKeyboardSwitch(boolean start) {
		if (start) {
			GlobalScreen.addNativeKeyListener(_listener);
		} else {
			GlobalScreen.removeNativeKeyListener(_listener);
		}
	}

	private void setBorwnMouseSwitch(boolean start) {
		if (start) {
			GlobalScreen.addNativeMouseListener(_listener);
			GlobalScreen.addNativeMouseWheelListener(_listener);
		} else {
			GlobalScreen.removeNativeMouseListener(_listener);
			GlobalScreen.removeNativeMouseWheelListener(_listener);
		}
	}
}

