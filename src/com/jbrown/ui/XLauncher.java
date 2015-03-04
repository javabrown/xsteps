//package com.jbrown.ui;
//
//
//import java.awt.BorderLayout;
//import java.awt.Container;
//import java.awt.Dimension;
//import java.awt.Image;
//import java.awt.MenuBar;
//import java.io.BufferedInputStream;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.imageio.ImageIO;
//import javax.swing.JFrame;
//import javax.swing.JMenu;
//import javax.swing.JMenuBar;
//import javax.swing.JMenuItem;
//import javax.swing.JScrollPane;
//import javax.swing.JSplitPane;
//import javax.swing.JTextArea;
//
//import com.jbrown.cast.BrownControl;
//
//public class XLauncher extends JFrame {
//	private Dimension _dimension;
//	private XMenuBar _menuBar;
//	
//    BrownControl _brownControl;
//    JTextArea _eventLog;
//    
//	public XLauncher() throws Exception {
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        _brownControl = new BrownControl();
//        _eventLog = new JTextArea();
//        getContentPane().setLayout(new BorderLayout());
//        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
//        		_brownControl  , new JScrollPane(_eventLog));
//       
//        splitPane.setResizeWeight(0.7);
//        
//        
//        getContentPane().add(splitPane, BorderLayout.CENTER);
//	}
//	
//	public void launch() {
//		super.setSize(_dimension);
//		super.setJMenuBar(_menuBar);
//		super.setVisible(true);
//	}
//
//	private void setIcon() {
//		try {
//			List<Image> icons = new ArrayList<Image>();
//
//			InputStream inputStream = this.getClass().getResourceAsStream(
//					"brown-logo.png");
//			BufferedInputStream in = new BufferedInputStream(inputStream);
//			Image image = ImageIO.read(in);
//
//			icons.add(image);
//			icons.add(image);
//
//			this.setIconImages(icons);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}
//	
//	public void setDimension(Dimension dimension){
//		_dimension = dimension;
//	}
//	
//	public void setXMenuBar(XMenuBar menuBar){
//		_menuBar = menuBar;
//	}
//	
//}
