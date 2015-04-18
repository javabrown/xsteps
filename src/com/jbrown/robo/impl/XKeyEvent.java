package com.jbrown.robo.impl;

public class XKeyEvent extends XEvent {
	private int _keyCode;
	private boolean _isAltPressed;
	private boolean _isCtrlPressed;
	private boolean _isShiftPressed;
	private String _keyText;
	
	public XKeyEvent(int keyCode, boolean altPressed, boolean isCtrlPressed,
			boolean isShiftPressed, String keyText, EventE eventE) {
		super(new BrownSpot(-1, -1, -1), eventE);
		_keyCode = keyCode;
		_isAltPressed = altPressed;
		_isCtrlPressed = isCtrlPressed;
		_isShiftPressed = isShiftPressed;
		_keyText = keyText;
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
	public int getGraphMaskValue(){
		return _keyCode;
	}
	
	public String getKeyText() {
		return _keyText;
	}

	@Override
	public String toString() {
		return "XKeyEvent [_keyCode=" + _keyCode + ", _isAltPressed="
				+ _isAltPressed + ", _isCtrlPressed=" + _isCtrlPressed
				+ ", _isShiftPressed=" + _isShiftPressed + ", _strKeyChar="
				+ _keyText + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (_isAltPressed ? 1231 : 1237);
		result = prime * result + (_isCtrlPressed ? 1231 : 1237);
		result = prime * result + (_isShiftPressed ? 1231 : 1237);
		result = prime * result + _keyCode;
		result = prime * result
				+ ((_keyText == null) ? 0 : _keyText.hashCode());
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
		if (_keyText == null) {
			if (other._keyText != null)
				return false;
		} else if (!_keyText.equals(other._keyText))
			return false;
		return true;
	}

	@Override
	public boolean isIgnorableEvent() {
		return false;
	}
}
