package net.net16.jeremiahlowe.drivestation3.gui;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.Box;

import java.awt.Color;
import java.awt.Component;

public class BatteryWatcherUI extends JPanel {
	private JProgressBar progressBar;
	private JLabel lblStupid, lblVoltage, lblMax, lblMin;
	private float goodLevel = 7500, badLevel = 5000;
	public BatteryWatcherUI() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		Box verticalBox = Box.createVerticalBox();
		add(verticalBox);
		Box horizontalBox = Box.createHorizontalBox();
		verticalBox.add(horizontalBox);
		lblMin = new JLabel("0V");
		horizontalBox.add(lblMin);
		Component horizontalStrut_2 = Box.createHorizontalStrut(5);
		horizontalBox.add(horizontalStrut_2);
		progressBar = new JProgressBar();
		progressBar.setMaximum(10000);
		progressBar.setMinimum(0);
		progressBar.setStringPainted(true);
		horizontalBox.add(progressBar);
		Component horizontalStrut_1 = Box.createHorizontalStrut(5);
		horizontalBox.add(horizontalStrut_1);
		lblMax = new JLabel("10V");
		horizontalBox.add(lblMax);
		Box horizontalBox_1 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_1);
		JLabel lblBattery = new JLabel("Battery status:");
		horizontalBox_1.add(lblBattery);
		Component horizontalStrut = Box.createHorizontalStrut(5);
		horizontalBox_1.add(horizontalStrut);
		lblVoltage = new JLabel("N/A");
		horizontalBox_1.add(lblVoltage);
		Component horizontalStrut_3 = Box.createHorizontalStrut(5);
		horizontalBox_1.add(horizontalStrut_3);
		lblStupid = new JLabel("N/A");
		horizontalBox_1.add(lblStupid);
		Component horizontalGlue = Box.createHorizontalGlue();
		horizontalBox_1.add(horizontalGlue);
	}
	public void setBadLevel(int badMillivolts){badLevel = badMillivolts;}
	public void setGoodLevel(int goodMillivolts){goodLevel = goodMillivolts;}
	public void setMinVoltage(int millivolts){
		progressBar.setMinimum(millivolts);
		lblMin.setText(millivolts / 1000f + "V");
	}
	public void setMaxVoltage(int millivolts){
		progressBar.setMaximum(millivolts);
		lblMax.setText(millivolts / 1000f + "V");
	}
	public void setVoltage(int millivolts){
		if(millivolts < badLevel){
			lblStupid.setForeground(Color.RED.darker());
			lblStupid.setText("Bad");
		}
		if(millivolts >= badLevel){
			lblStupid.setForeground(Color.ORANGE.darker());
			lblStupid.setText("OK");
		}
		if(millivolts >= goodLevel){
			lblStupid.setForeground(Color.GREEN.darker());
			lblStupid.setText("Good");
		}
		lblVoltage.setText(millivolts / 1000f + "V");
		progressBar.setValue(millivolts);
	}
	public int getVoltage(){return progressBar.getValue();}
}
