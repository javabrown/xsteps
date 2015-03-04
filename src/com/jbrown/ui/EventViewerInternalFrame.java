package com.jbrown.ui;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
 
import java.awt.BorderLayout;
import java.awt.EventQueue;

public class EventViewerInternalFrame extends JInternalFrame {
    JTextArea _eventLog;
 
	
    public EventViewerInternalFrame() throws Exception {

        
        EventQueue.invokeLater(new Runnable() {
    		@Override
    		public void run() {
    			 
    			
    	        setSize(450, 350);
    	        setDefaultCloseOperation(3); 
    	        
    	        setLayout(new BorderLayout());
    	        add( new JScrollPane(new EventViewer()), BorderLayout.CENTER);
    	        setVisible(true);
    		}
    	});
    }
    
}
