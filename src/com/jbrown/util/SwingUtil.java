package com.jbrown.util;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.jnativehook.keyboard.NativeKeyEvent;

import com.jbrown.robo.XEventI;
import com.jbrown.robo.impl.EventE;
import com.jbrown.robo.impl.XKeyEvent;
import com.jbrown.robo.impl.XMouseEvent;

public class SwingUtil {

	public static XEventI getXEvent(AWTEvent e) {
		if (e instanceof MouseEvent) {
			return makeXEvent((MouseEvent) e);
		}

		if (e instanceof KeyEvent) {
			return makeXEvent((KeyEvent) e);
		}

		return null;
	}

	public static XEventI makeXEvent(KeyEvent e) {
		EventE event = SwingUtil.getEventE(e.getID());

		if (event != null) {
			return new XKeyEvent(e.getKeyCode(), e.isAltDown(),
					e.isControlDown(), e.isShiftDown(),
					NativeKeyEvent.getKeyText(e.getKeyCode()), event);
		}

		return null;
	}

	public static XEventI makeXEvent(MouseEvent e) {
		EventE event = SwingUtil.getEventE(e.getID());

		if (event != null) {
			return new XMouseEvent(e.getButton(), 2, e.getX(), e.getY(), event);
		}

		return null;
	}

	public static EventE getEventE(int eventId) {
		EventE eventE = null;

		switch (eventId) {
		case MouseEvent.MOUSE_PRESSED:
			eventE = EventE.MOUSE_PRESS;
			break;
		case MouseEvent.MOUSE_RELEASED:
			eventE = EventE.MOUSE_RELEASE;
			break;
		// case MOUSE_CLICKED:
		// str.append("MOUSE_CLICKED");
		// break;
		// case MOUSE_ENTERED:
		// str.append("MOUSE_ENTERED");
		// break;
		// case MOUSE_EXITED:
		// str.append("MOUSE_EXITED");
		// break;
		case MouseEvent.MOUSE_MOVED:
			eventE = EventE.MOUSE_RELEASE;
			break;
		case KeyEvent.KEY_PRESSED:
			eventE = EventE.KEY_PRESSED;
			break;
		case KeyEvent.KEY_RELEASED:
			eventE = EventE.KEY_RELEASE;
			break;
		// case MOUSE_DRAGGED:
		// str.append("MOUSE_DRAGGED");
		// break;
		// case MOUSE_WHEEL:
		// str.append("MOUSE_WHEEL");
		// break;
		// default:
		// str.append("unknown type");
		// }
		}

		return eventE;
	}
}
