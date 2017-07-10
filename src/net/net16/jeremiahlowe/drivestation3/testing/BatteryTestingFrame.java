package net.net16.jeremiahlowe.drivestation3.testing;

import javax.swing.JFrame;

import net.net16.jeremiahlowe.drivestation3.gui.BatteryWatcherUI;

public class BatteryTestingFrame extends JFrame{
	public static void main(String[] args){
		BatteryTestingFrame frame = new BatteryTestingFrame();
		frame.setVisible(true);
	}
	public BatteryTestingFrame(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		BatteryWatcherUI bwui = new BatteryWatcherUI();
		add(bwui);
		bwui.setVoltage(7501);
	}
}
