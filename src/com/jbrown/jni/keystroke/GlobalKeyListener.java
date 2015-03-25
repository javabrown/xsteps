package com.jbrown.jni.keystroke;

public class GlobalKeyListener {
	protected PoolHook _hook;
	protected KeyListener _keyListener;

	public GlobalKeyListener() {
		_keyListener = null;
		_hook = null;
	}

	public void bindKeyListener(KeyListener keyListener) {
		_keyListener = keyListener;
		
		_hook = new PoolHook(this);
		_hook.start();
	}

	public void unbindKeyListener() {
		_hook.stop();
		_hook = null;
		//_keyListener = null;//RK: Not clearing the reference now. Will see later..
	}

	void keyPressed(KeyEvent event) {
		try {
			_keyListener.keyPressed(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void keyReleased(KeyEvent event) {
		try {
			_keyListener.keyReleased(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}