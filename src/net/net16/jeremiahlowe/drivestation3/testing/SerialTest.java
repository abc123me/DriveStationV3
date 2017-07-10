package net.net16.jeremiahlowe.drivestation3.testing;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import com.fazecast.jSerialComm.SerialPort;

import net.net16.jeremiahlowe.drivestation3.Utility;

public class SerialTest {
	public static void main(String[] args) throws Exception{
		String[] ports = Utility.getSerialPortNames();
		System.out.println(ports.length + " ports available" + (ports.length == 0 ? "" : ", they are:"));
		for(int i = 0; i < ports.length; i++) System.out.println("Port " + i + ": " + ports[i]);
		Scanner input = new Scanner(System.in);
		System.out.print("Please select a port: ");
		int selectedPortID = input.nextInt();
		System.out.println();
		SerialPort ser = SerialPort.getCommPort(ports[selectedPortID]);
		if(!ser.openPort()){
			System.err.println("Couldn't open port!");
			System.exit(1);
		}
		InputStream is = ser.getInputStream();
		OutputStream os = ser.getOutputStream();
		new Thread(new Runnable(){
			@Override
			public void run(){
				while(!Thread.interrupted()){
					try{
						Thread.sleep(5);
						while(is.available() != -1) System.out.print((char) is.read());
					}catch(Exception e){}
				}
			}
		}).start();
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable(){
			@Override
			public void run(){
				input.close();
				if(ser.isOpen()) ser.closePort();
			}
		}));
		while(System.in.available() != -1) os.write(System.in.read());
	}
}
