package com.jbrown.ui;
import javax.swing.JDesktopPane;


public class Desktop extends JDesktopPane {
	public Desktop(){
		try {
			add(new EventViewerInternalFrame());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

