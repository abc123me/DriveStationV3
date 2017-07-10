package net.net16.jeremiahlowe.drivestation3;

import java.awt.Component;

import com.fazecast.jSerialComm.SerialPort;

import net.net16.jeremiahlowe.drivestation3.gui.misc.SelectionDialog;

public class Utility {
	@SafeVarargs
	public static final <T> boolean anyOf(T val, T... these){
		for(T i : these) if(i == val) return true;
		return false;
	}
	public static String[] getSerialPortNames(){
		SerialPort[] ports = SerialPort.getCommPorts();
		String[] portNames = new String[ports.length];
		for(int i = 0; i < ports.length; i++) portNames[i] = ports[i].getSystemPortName();
		return portNames;
	}
	public static String showPortSelectionDialog(Component parent) {
		String[] portList = getSerialPortNames();
		return SelectionDialog.showSelectionDialog(portList, parent, "Select a port");
	}
	//Please don't sue me for this :)
	//Song: Seven nation army
	//Author: The glitch mob
	public static String getSevenNationArmyLyrics() {
		return "I'm gonna fight 'em off\r\n"
				+ "A seven nation army couldn't hold me back\r\n"
				+ "They're gonna rip it off\r\n"
				+ "Taking their time right behind my back\r\n"
				+ "\r\n"
				+ "And I'm talking to myself at night\r\n"
				+ "Because I can't forget\r\nBack and forth through my mind\r\n"
				+ "Behind a cigarette\r\n"
				+ "And the message coming from my eyes\r\n"
				+ "Says leave it alone\r\n"
				+ "\r\n"
				+ "Don't want to hear about it\r\n"
				+ "Every single one's got a story to tell\r\n"
				+ "Everyone knows about it\r\n"
				+ "From the Queen of England to the hounds of hell\r\n"
				+ "\r\n"
				+ "And if I catch it coming back my way\r\n"
				+ "I'm gonna serve it to you\r\n"
				+ "And that ain't what you want to hear\r\n"
				+ "But thats what I'll do\r\n"
				+ "And the feeling coming from my bones\r\n"
				+ "Says find a home\r\n"
				+ "\r\n"
				+ "I'm going to Wichita\r\n"
				+ "Far from this opera forevermore\r\n"
				+ "I'm gonna work the straw\r\n"
				+ "Make the sweat drip out of every pore\r\n"
				+ "And I'm bleeding, and I'm bleeding, and I'm bleeding\r\n"
				+ "Right before the Lord\r\n"
				+ "All the words are gonna bleed from me\r\n"
				+ "And I will think no more\r\n"
				+ "And the stains coming from my blood\r\n"
				+ "Tell me go back home\r\n";
	}
}
