package com.jbrown.ui;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Observable;
import javax.accessibility.Accessible;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SpinnerButton extends JPanel implements Accessible {
	private JLabel _label;
	private JSpinner _spinner;
	private JButton _button;
	private SpinnerNumberModel _model;
	private NRepeatTracker _tracker;
	
	public SpinnerButton(String caption){
		_tracker = new NRepeatTracker();
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
		_model.addChangeListener(_tracker);
		
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

	public NRepeatTracker getNRepeatTracker(){
		return _tracker;
	}
	
	static class NRepeatTracker extends Observable implements ChangeListener {
		private int _nRepeat;
		
		public int getNRepeat(){
			return _nRepeat;
		}
		
		@Override
		public void stateChanged(ChangeEvent e) {
			SpinnerNumberModel model = ( SpinnerNumberModel ) e.getSource();
			
			if(model != null){
				_nRepeat = model.getNumber().intValue();
				
				setChanged();
				notifyObservers(this);
				System.out.println(model.getValue());
			}
		}
	}
}


