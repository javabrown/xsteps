package com.jbrown.ui;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.accessibility.Accessible;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.*;

public class SpinnerButton extends JPanel implements Accessible{
	private JLabel _label;
	private JSpinner _spinner;
	private JButton _button;
	private SpinnerNumberModel _model;
			
	public SpinnerButton(String caption){
		this.add(createUI(caption), BoxLayout.X_AXIS);
	}
	
	public int getIntValue(){
		return _model.getNumber().intValue();
	}
	
	public void addActionListener(ActionListener listener, String action){
		_button.addActionListener(listener);
		_button.setActionCommand(action);
	}
	
	private JPanel createUI(String caption){
		JPanel jp = new JPanel();
		jp.setLayout(new GridLayout(1,3,2,0));
		 
		_model = new SpinnerNumberModel(1, 1, 12000, 1);
	    _label = new JLabel(caption+" ", SwingConstants.CENTER);
		_spinner = new JSpinner(_model);
	    _button = new JButton("GO");
	    
	    jp.add(_label);
	    jp.add(_spinner);
	    jp.add(_button);
	    //jp.setPreferredSize(new Dimension(10, 10));
	  
	    jp.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
	    
	    return jp;
	}
	
//	public static void main(String[] args){
//		JFrame f = new JFrame();
//		f.setSize(200,200);
//		SpinnerButton spin = new SpinnerButton("Repeat");
//		
//		f.getContentPane().add(spin);
//		f.setVisible(true);
//		System.out.println(spin.getIntValue());
//	}
}
