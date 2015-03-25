package com.jbrown.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import com.jbrown.robo.XEventI;

public class XEventGraph extends JPanel implements Observer {
	private TimeSeries series;

	private double _eventSimulatorValue = 100.0;
	private Timer _timer;

	@Override
	public void update(Observable obj, Object arg) {
		System.out.println("Observer Update called with Arguments: "+arg);
		XEventI event = (XEventI) arg;
		
		if(event != null){
			this.addEvent(event.getGraphMaskValue());
		}
	}
	
	public void startMonitor() {
		this.startWatch();
	}

	public void stopMonitor() {
		this.stopMonitor();
	}

	private void addEvent(double eventSimulatorValue) {
		this.series.add(new Millisecond(), eventSimulatorValue);
		_eventSimulatorValue = eventSimulatorValue;
	}

	public XEventGraph() {
		this.series = new TimeSeries("Random Data", Millisecond.class);
		final TimeSeriesCollection dataset = new TimeSeriesCollection(
				this.series);
		final JFreeChart chart = createChart(dataset);

		final ChartPanel chartPanel = new ChartPanel(chart);
		// final JButton button = new JButton("Add New Data Item");
		// button.setActionCommand("ADD_DATA");
		// button.addActionListener(this);

		final JPanel content = new JPanel(new BorderLayout());
		content.add(chartPanel, BorderLayout.CENTER);
		// content.add(button, BorderLayout.SOUTH);
		// chartPanel.setPreferredSize(new java.awt.Dimension(600, 400));

		_timer = new Timer(1000, new ActionListener() {// quarter second
					@Override
					public void actionPerformed(ActionEvent e) {
						series.add(new Millisecond(), _eventSimulatorValue);
					}
				});

		this.add(content);
	}

	private JFreeChart createChart(final XYDataset dataset) {
		final JFreeChart result = ChartFactory.createTimeSeriesChart(
				"XStep Monitor", "Time", "Value", dataset, true, true, false);
		final XYPlot plot = result.getXYPlot();
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		axis.setFixedAutoRange(60000.0); // 60 seconds
		axis = plot.getRangeAxis();
		axis.setRange(0.0, 200.0);
		return result;
	}

	private void startWatch() {
		_timer.start();
	}

	private void stopWatch() {
		_timer.stop();
	}

	// public void actionPerformed(final ActionEvent e) {
	// if (e.getActionCommand().equals("ADD_DATA")) {
	// final double factor = 0.90 + 0.2 * Math.random();
	// this.lastValue = this.lastValue * factor;
	// final Millisecond now = new Millisecond();
	// System.out.println("Now = " + now.toString());
	// this.series.add(new Millisecond(), this.lastValue);
	// }
	// }
}
