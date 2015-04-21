//package com.jbrown.core.listener;
//
//import org.jnativehook.GlobalScreen;
//import org.jnativehook.NativeHookException;
// 
//class BrownNativeEventScanner {
//	private GlobalBrownListener _listener;
//
//	public BrownNativeEventScanner() {
//		_listener = new GlobalBrownListener();
//	}
//
//	public void setEnable(boolean enable) {
//		setBorwnNativeDllSwitch(enable);
//		setBorwnKeyboardSwitch(enable);
//		setBorwnMouseSwitch(enable);
//	}
//
//	private void setBorwnNativeDllSwitch(boolean start) {
//		try {
//			if (start) {
//				GlobalScreen.registerNativeHook();
//				setBorwnKeyboardSwitch(start);
//			} else {
//				GlobalScreen.unregisterNativeHook();
//			}
//		} catch (NativeHookException ex) {
//			System.out.println("Error: " + ex.getMessage() + "\n");
//		}
//	}
//
//	private void setBorwnKeyboardSwitch(boolean start) {
//		if (start) {
//			GlobalScreen.addNativeKeyListener(_listener);
//		} else {
//			GlobalScreen.removeNativeKeyListener(_listener);
//		}
//	}
//
//	private void setBorwnMouseSwitch(boolean start) {
//		if (start) {
//			GlobalScreen.addNativeMouseListener(_listener);
//			GlobalScreen.addNativeMouseWheelListener(_listener);
//		} else {
//			GlobalScreen.removeNativeMouseListener(_listener);
//			GlobalScreen.removeNativeMouseWheelListener(_listener);
//		}
//	}
//}
//
