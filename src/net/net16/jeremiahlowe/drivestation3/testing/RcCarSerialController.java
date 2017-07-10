package net.net16.jeremiahlowe.drivestation3.testing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import net.net16.jeremiahlowe.drivestation3.Enums.Motor;
import net.net16.jeremiahlowe.drivestation3.Enums.Speed;
import net.net16.jeremiahlowe.drivestation3.gui.driving.DriveControlListener;
import net.net16.jeremiahlowe.drivestation3.gui.driving.DriveControlUI;
import net.net16.jeremiahlowe.drivestation3.gui.driving.schemes.Steerable;
import net.net16.jeremiahlowe.drivestation3.gui.serial.SerialDialog;

public class RcCarSerialController extends JFrame implements DriveControlListener, SerialPortDataListener{
	public SerialPort sp;
	private static final long serialVersionUID = 1L;
	public static void main(String[] args) {
		RcCarSerialController frame = new RcCarSerialController();
		frame.setVisible(true);
		frame.getSerialPort();
		JFrame j = new JFrame();
		JButton b = new JButton("Reconnect");
		j.setBounds(100, 100, 150, 75);
		j.setVisible(true);
		j.add(b);
		j.invalidate();
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getSerialPort();
			}
		});
	}
	public void getSerialPort(){
		if(sp != null && sp.isOpen()) sp.closePort();
		sp = SerialDialog.openSerialDialog();
		if(sp != null) sp.openPort();
		sp.addDataListener(this);
	}
	public RcCarSerialController() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		DriveControlUI dcui = new DriveControlUI(new Steerable(this));
		setContentPane(dcui);
		dcui.setFocusable(true);
		dcui.grabFocus();
	}
	@Override
	public void setMotor(Motor motor, Speed speed) {
		try{
			if(sp != null && sp.isOpen()){
				OutputStream os = sp.getOutputStream();
				if(motor == Motor.A && speed == Speed.Forward) os.write('1');
				if(motor == Motor.A && speed == Speed.Backward) os.write('2');
				if(motor == Motor.B && speed == Speed.Forward) os.write('3');
				if(motor == Motor.B && speed == Speed.Backward) os.write('4');
				if(motor == Motor.Both && speed == Speed.Forward) os.write(new byte[]{'1', '3'});
				if(motor == Motor.Both && speed == Speed.Backward) os.write(new byte[]{'2', '4'});
				if(speed == Speed.Stop) os.write('0');
			}
			else{
				if(sp == null) System.err.println("Serial port is null?!");
				else{
					System.err.println("Serial port is closed, attempting to re-open it");
					if(sp.openPort()) System.out.println("Serial port was sucessfully re-opened!");
					else System.err.println("Couldn't re-open serial port");
				}
			} 
		}
		catch(Exception e){
			System.err.println(e);
			e.printStackTrace();
		}
	}
	@Override
	public int getListeningEvents() {
		return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
	}
	@Override
	public void serialEvent(SerialPortEvent arg0) {
		try{
			InputStream is = arg0.getSerialPort().getInputStream();
			while(is.available() != -1){
				System.out.print((char) is.read());
			}
		}
		catch(Exception e){
			System.err.println(e);
			e.printStackTrace();
		}
	}
}
