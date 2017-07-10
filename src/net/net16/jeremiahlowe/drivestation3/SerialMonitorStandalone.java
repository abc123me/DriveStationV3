package net.net16.jeremiahlowe.drivestation3;

import javax.swing.JOptionPane;

import com.fazecast.jSerialComm.SerialPort;

import net.net16.jeremiahlowe.drivestation3.gui.serial.SerialDialog;

public class SerialMonitorStandalone {
	public static void main(String[] args) {
		SerialPort ser = SerialDialog.openSerialDialog();
		if(!ser.openPort()){
			JOptionPane.showMessageDialog(null, "Couldn't open serial port on " + ser.getSystemPortName());
			System.exit(1);
		}
	}
}
