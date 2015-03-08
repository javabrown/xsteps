package com.jbrown.robo.impl;

public class XKeyEvent extends XEvent {
	private int _keyCode;
	private boolean _isAltPressed;
	private boolean _isCtrlPressed;
	private boolean _isShiftPressed;

	public XKeyEvent(int keyCode, boolean altPressed, boolean isCtrlPressed,
			boolean isShiftPressed, EventE eventE) {
		super(new BrownSpot(-1, -1, -1), eventE);
		_keyCode = keyCode;
		_isAltPressed = altPressed;
		_isCtrlPressed = isCtrlPressed;
		_isShiftPressed = isShiftPressed;
	}

	public int getKeyCode() {
		return _keyCode;
	}

	public boolean isAltRequired() {
		return _isAltPressed;
	}

	public boolean isCtrlRequired() {
		return _isCtrlPressed;
	}

	public boolean isShiftRequired() {
		return _isShiftPressed;
	}

	
	@Override
	public String toString() {
		return "XKeyEvent [keyCode=" + _keyCode + ", isAltPressed="
				+ _isAltPressed + ", isCtrlPressed=" + _isCtrlPressed
				+ ", isShiftPressed=" + _isShiftPressed + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (_isAltPressed ? 1231 : 1237);
		result = prime * result + (_isCtrlPressed ? 1231 : 1237);
		result = prime * result + (_isShiftPressed ? 1231 : 1237);
		result = prime * result + _keyCode;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		XKeyEvent other = (XKeyEvent) obj;
		if (_isAltPressed != other._isAltPressed)
			return false;
		if (_isCtrlPressed != other._isCtrlPressed)
			return false;
		if (_isShiftPressed != other._isShiftPressed)
			return false;
		if (_keyCode != other._keyCode)
			return false;
		return true;
	}
	
	
}
