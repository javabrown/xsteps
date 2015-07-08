package com.jbrown.ui;

import static java.awt.GraphicsDevice.WindowTranslucency.TRANSLUCENT;

import java.awt.Component;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class XDialog implements Runnable {
	public static boolean start() {
		if (!_isRunning) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			new Thread(XDialog.getInstance()).start();
			return true;
		}

		return false;
	}
	
	public static void stop() {
		_isRunning = false;
	}
	
	public static void setTitle(String title){
		_title = title;
	}
	
	public static void setMessage(String message) {
		_message = message;
	}
	
	public static void showMsg(String title, String message){
		_title = title;
		_message = message;
		
	}
	
	private static XDialog getInstance(){
		if(_instance == null){
			_instance = new XDialog();
		}
		
		return _instance;
	}
	
	
	@Override
	public void run() {
		_isRunning = true;
		System.out.println("Dialog Thread Started!!");
		_dialog.start();
		
		while (_isRunning) {
			_dialog.setTitle(_title);
			_dialog.setHeader(_message);

			try {
				Thread.sleep(200);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		
		_dialog.stop();
		System.out.println("Dialog Thread Stoped!!");
	}
	
	
	private static boolean _isRunning = false;
	private static String _title;
	private static String _message;
	private static XDialog _instance;
	
	private TranslucentDialog _dialog;
	
	private XDialog(){
		_dialog = TranslucentDialog.getDialog();
	}
}

class TranslucentDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private final JPanel _jp;
	private final JLabel _header;
	private final JLabel _body;
	private final $Watch _$watch;

	private static TranslucentDialog _instance;

	public static TranslucentDialog getDialog() {
		if (_instance == null) {
			_instance = new TranslucentDialog();
		}

		return _instance;
	}
	
	public void setTitle(String title) {
		this.getRootPane().setBorder(
				new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED), title,
						TitledBorder.TOP, TitledBorder.CENTER));
	}

	public final void setHeader(final String msgHeader) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_header.setText(msgHeader);
			}
		});
		//_header.setText(msgHeader);
	}

	public synchronized final void setBody(final String msgBody) {
		//_body.setText(msgBody);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_body.setText(msgBody);
			}
		});
	}

	public void setOpacity(int value){
		this.setOpacity(0.75f);
	}
	
	public final void start() {
		setVisible(true);
		new Thread(_$watch).start();
		
//		SwingUtilities.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				setVisible(true);
//				new Thread(_$watch).start();
//			}
//		});
	}

	public final void stop() {
		_$watch.stop();
		setVisible(false);
	}

	private TranslucentDialog() {
		this.setLayout(new GridBagLayout());
		this.setSize(200, 80);
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setUndecorated(true);
		// this.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
		JFrame.setDefaultLookAndFeelDecorated(true);

		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();

		setTitle("XStep Live");

		_$watch = new $Watch();
		_jp = new JPanel();
		_header = new JLabel();
		_body = new JLabel();

		add(_jp);

		if (gd.isWindowTranslucencySupported(TRANSLUCENT)) {
			this.setOpacity(0.75f);
		}

		this.setupComponent();
		this.addEvent();
	}

	private void setupComponent() {
		_jp.setLayout(new GridLayout(2, 1, 5, 5));
		_jp.add(_header);
		_jp.add(_body);
	}

	private void addEvent() {
		MouseListener ml = new MouseAdapter() {
			int _x, _y;
			int _xCenter = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
			int _yCenter = Toolkit.getDefaultToolkit().getScreenSize().height / 2;

			@Override
			public void mouseEntered(MouseEvent e) {
				_x = e.getXOnScreen();
				_y = e.getYOnScreen();

				JDialog dlg = (JDialog) SwingUtilities.getRoot((Component) e
						.getSource());

				int xIncrement = dlg.getWidth();
				int yIncrement = dlg.getHeight();

				if (_x >= _xCenter) {
					_x = getLocationOnScreen().x - xIncrement;
				} else {
					_x = getLocationOnScreen().x + xIncrement;
				}

				if (_y >= _yCenter) {
					_y = getLocationOnScreen().y - yIncrement;
				} else {
					_y = getLocationOnScreen().y + yIncrement;
				}

				setLocation(_x, _y);
			}
		};

		this.addMouseListener(ml);
	}

	class $Watch implements Runnable {
		private boolean _kontinue;

		public $Watch() {
			_kontinue = true;
		}

		@Override
		public void run() {
			while (_kontinue) {
				try {
					
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							setBody(String.format("%s | %s", getMouseInfo(), getTime()));
						}
					});
					
					//setBody(String.format("%s | %s", getMouseInfo(), getTime()));
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			_kontinue = true;
		}

		private String getTime() {
			DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			Date date = new Date();
			return dateFormat.format(date);
		}

		private String getMouseInfo() {
			PointerInfo a = MouseInfo.getPointerInfo();
			Point b = a.getLocation();
			int x = (int) b.getX();
			int y = (int) b.getY();
			return String.format("%s:%s", x, y);
		}

		public void stop() {
			_kontinue = false;
		}
	}

	class Banner {
		private String _title;
		private String _msgHeader;
		private String _msgBody;

		private Banner(String title, String msgHeader, String msgBody) {
			_title = title;
			_msgHeader = msgHeader;
			_msgBody = msgBody;
		}

		public String getTitle() {
			return _title;
		}

		public String getHeader() {
			return _msgHeader;
		}

		public String getBody() {
			return _msgBody;
		}

		public void setTitle(String title) {
			this._title = title;
		}

		public void setHeader(String msgHeader) {
			this._msgHeader = msgHeader;
		}

		public void setBody(String msgBody) {
			this._msgBody = msgBody;
		}
	}
}
