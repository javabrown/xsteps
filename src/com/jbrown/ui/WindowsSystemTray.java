package com.jbrown.ui;

import static com.jbrown.robo.KeysI.COMMAND_EXIT_K;
import static com.jbrown.robo.KeysI.COMMAND_RECORD_K;
import static com.jbrown.robo.KeysI.COMMAND_RESET_K;
import static com.jbrown.robo.KeysI.COMMAND_SAVE_K;
import static com.jbrown.robo.KeysI.COMMAND_VERIFY_K;
import static com.jbrown.robo.KeysI.COMMAND_SMART_WORKER_POWER_SAVE_MODE_K;

import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.PopupMenu;
import java.awt.Robot;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import org.springframework.util.StringUtils;

import sun.security.jca.GetInstance;
import sun.swing.SwingUtilities2;

import com.jbrown.Main;
import com.jbrown.util.SmartWorker;
import com.sun.java.swing.SwingUtilities3;

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

		Status(String text, String imageIcon) {
			_text = text;
			_imageIcon = imageIcon;
		}

		String getText() {
			return _text;
		}

		Image getImage() {
			URL resource = Main.class.getResource(_imageIcon);
			Image image = Toolkit.getDefaultToolkit().getImage(resource);
			return image;
		}
	};

	public WindowsSystemTray(AbstractEventManager eventManager) {
		if (!SystemTray.isSupported()) {
			System.out.println("System tray is not supported !!! ");
			return;
		}

		_systemTray = SystemTray.getSystemTray();
		_eventManager = eventManager;
		_trayPopupMenu = new TrayMenu(_eventManager);// new PopupMenu();

		// this.defineMenuAndAction();
	}

	public void setActivityStatus(Status status) {
		_trayIcon.setImage(status.getImage());
		_trayIcon.setToolTip(status.getText());
	}

	// private void defineMenuAndAction(){
	// MenuItem record = new MenuItem(COMMAND_RECORD_K);
	// record.setActionCommand(COMMAND_RECORD_K);
	// record.addActionListener(_eventManager);
	//
	// MenuItem reset = new MenuItem(COMMAND_RESET_K);
	// reset.setActionCommand(COMMAND_RESET_K);
	// reset.addActionListener(_eventManager);
	//
	// MenuItem save = new MenuItem(COMMAND_SAVE_K);
	// save.setActionCommand(COMMAND_SAVE_K);
	// save.addActionListener(_eventManager);
	//
	// MenuItem verify = new MenuItem(COMMAND_VERIFY_K);
	// verify.addActionListener(_eventManager);
	// verify.setActionCommand(COMMAND_VERIFY_K);
	//
	// MenuItem exit = new MenuItem(COMMAND_EXIT_K);
	// exit.addActionListener(_eventManager);
	// exit.setActionCommand(COMMAND_EXIT_K);
	//
	// _trayPopupMenu.add(record);
	// _trayPopupMenu.addSeparator();
	// _trayPopupMenu.add(reset);
	// _trayPopupMenu.add(save);
	// _trayPopupMenu.addSeparator();
	// _trayPopupMenu.add(verify);
	// _trayPopupMenu.addSeparator();
	// _trayPopupMenu.add(exit);
	// }

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

	@SuppressWarnings("serial")
	class TrayMenu extends PopupMenu {
		private MenuBag _menuBag;

		public TrayMenu(AbstractEventManager eventManager) {
			super();
			_menuBag = new MenuBag(eventManager);

			this.add(_menuBag.getMenuItem(COMMAND_RECORD_K));
			this.addSeparator();
			this.add(_menuBag.getMenuItem(COMMAND_RESET_K));
			this.add(_menuBag.getMenuItem(COMMAND_SAVE_K));
			this.addSeparator();
			this.add(_menuBag.getMenuItem(COMMAND_VERIFY_K));
			this.addSeparator();
			this.add(_menuBag.getPowerSaverMenu());
			this.addSeparator();
			this.add(_menuBag.getMenuItem(COMMAND_EXIT_K));

		}

		public MenuItem getRecorderMenu() {
			return this.getItem(0);
		}

		public MenuItem getResetMenu() {
			return this.getItem(2);
		}

		public MenuItem getSaveMenu() {
			return this.getItem(3);
		}

		public MenuItem getVerifyeMenu() {
			return this.getItem(5);
		}

		public MenuItem getExitMenu() {
			return this.getItem(7);
		}
	}

	class MenuBag implements ItemListener {

		private MenuItem[] _menuBag;
		private CheckboxMenuItem _smartSaverMode; // special handling due to
						
		public MenuBag(AbstractEventManager eventManager) {
			_menuBag = new MenuItem[] {
					new MenuItem(COMMAND_RECORD_K, new MenuShortcut('R')),
					new MenuItem(COMMAND_RESET_K, new MenuShortcut('S')),
					new MenuItem(COMMAND_SAVE_K),
					new MenuItem(COMMAND_VERIFY_K),
					// new
					// CheckboxMenuItem(COMMAND_SMART_WORKER_POWER_SAVE_MODEL_K,
					// true),
					new MenuItem(COMMAND_EXIT_K),

			};

			_smartSaverMode = new CheckboxMenuItem(
					COMMAND_SMART_WORKER_POWER_SAVE_MODE_K, true);
			this.addEvent(eventManager);

			// Special handling for CheckboxMenuItem due to event mapping issue.
			_smartSaverMode.setName(_smartSaverMode.getLabel());
			_smartSaverMode
					.setActionCommand(COMMAND_SMART_WORKER_POWER_SAVE_MODE_K);
			_smartSaverMode.addActionListener(eventManager);

			_smartSaverMode.setState(true);
			_smartSaverMode.addItemListener(this);
		}

		public CheckboxMenuItem getPowerSaverMenu() {
			return _smartSaverMode;
		}

		public MenuItem getMenuItem(String menuName) {
			if (!StringUtils.isEmpty(menuName)) {
				for (MenuItem menuTem : _menuBag) {
					if (menuTem.getName().equals(menuName)) {
						return menuTem;
					}
				}
			}

			return null;
		}

		private void addEvent(AbstractEventManager eventManager) {
			for (Object item : _menuBag) {
				CheckboxMenuItem checkItem = item instanceof CheckboxMenuItem ? (CheckboxMenuItem) item
						: null;
				MenuItem menuItem = item instanceof MenuItem ? (MenuItem) item
						: null;

				if (menuItem != null) {
					menuItem.setName(menuItem.getLabel());
					menuItem.setActionCommand(menuItem.getLabel());
					menuItem.addActionListener(eventManager);
				}

				// if(checkItem != null){
				// checkItem.setName(checkItem.getLabel());
				// checkItem.setActionCommand(checkItem.getLabel());
				// checkItem.addActionListener(eventManager);
				// }
			}
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			//System.out.println("AutoSave is set " + _smartSaverMode.getState());
			if (!_smartSaverMode.getState()) {
				new Thread(SmartWorker.getInstance()).start();
			} else {
				SmartWorker.getInstance().stop();
			}

		}
	}
}
