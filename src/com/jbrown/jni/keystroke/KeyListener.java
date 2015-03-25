package com.jbrown.jni.keystroke;

import java.util.EventListener;

public interface KeyListener extends EventListener {
  public void keyPressed(KeyEvent event);
  public void keyReleased(KeyEvent event);
}