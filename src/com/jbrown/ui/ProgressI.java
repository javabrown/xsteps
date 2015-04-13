package com.jbrown.ui;

public interface ProgressI {
	void show();

	void hide();

	void setText(String text);

	String getText();
	
	void setLocationOnScreen(int x, int y);
}