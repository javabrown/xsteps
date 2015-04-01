package com.jbrown.ui;

import static com.jbrown.robo.KeysI.COMMAND_EXIT_K;
import static com.jbrown.robo.KeysI.COMMAND_RECORD_K;
import static com.jbrown.robo.KeysI.COMMAND_RESET_K;
import static com.jbrown.robo.KeysI.COMMAND_SAVE_K;
import static com.jbrown.robo.KeysI.COMMAND_VERIFY_K;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import com.jbrown.Main;

public class WindowsSystemTray {
	private SystemTray _systemTray; 
	private AbstractEventManager _eventManager;
	private PopupMenu _trayPopupMenu;
	private TrayIcon _trayIcon;
	
	public enum Status { 
		RECORDING_ON("Recording On", "/icons/brown-logo.png"), 
		RECORDING_OFF("Recording Off", "/icons/brown-logo.png");
		
		String _text;
		String _imageIcon;
		
		Status(String text, String imageIcon){
			_text = text;
			_imageIcon = imageIcon;
		}
		
		String getText(){
			return _text;
		}
		
		Image getImage(){
			URL resource = Main.class.getResource(_imageIcon);
			Image image = Toolkit.getDefaultToolkit().getImage(resource);
			return image;
		}
	};
			
	public WindowsSystemTray(AbstractEventManager eventManager){
		if (!SystemTray.isSupported()) {
			System.out.println("System tray is not supported !!! ");
			return;
		}
		
		_systemTray = SystemTray.getSystemTray();
		_eventManager = eventManager;
		_trayPopupMenu = new PopupMenu();
		

		
		this.defineMenuAndAction();
	}
	
	public void setActivityStatus(Status status) {
		_trayIcon.setImage(status.getImage());
		_trayIcon.setToolTip(status.getText());
	}
	
	private void defineMenuAndAction(){
		MenuItem record = new MenuItem(COMMAND_RECORD_K);
		record.setActionCommand(COMMAND_RECORD_K);
		record.addActionListener(_eventManager);
	 
		MenuItem reset = new MenuItem(COMMAND_RESET_K);
		reset.setActionCommand(COMMAND_RESET_K);
		reset.addActionListener(_eventManager);
		
		MenuItem save = new MenuItem(COMMAND_SAVE_K);
		save.setActionCommand(COMMAND_SAVE_K);
		save.addActionListener(_eventManager);

		
		MenuItem verify = new MenuItem(COMMAND_VERIFY_K);
		verify.addActionListener(_eventManager);
		verify.setActionCommand(COMMAND_VERIFY_K);
 		
		MenuItem exit = new MenuItem(COMMAND_EXIT_K);
		exit.addActionListener(_eventManager);
		exit.setActionCommand(COMMAND_EXIT_K);
		
		_trayPopupMenu.add(record);
		_trayPopupMenu.addSeparator();
		_trayPopupMenu.add(reset);
		_trayPopupMenu.add(save);
		_trayPopupMenu.addSeparator();
		_trayPopupMenu.add(verify);
		_trayPopupMenu.addSeparator();
		_trayPopupMenu.add(exit);
	}
	
	public void launch(final XDesktop desktop) {
		_trayIcon = new TrayIcon(Status.RECORDING_OFF.getImage(),
				Status.RECORDING_OFF.getText(), _trayPopupMenu);
		_trayIcon.setImageAutoSize(true);

		_trayIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					desktop.show();
				}
			}
		});


		
		try {
			_systemTray.add(_trayIcon);
		} catch (AWTException awtException) {
			awtException.printStackTrace();
		}
	}
}
