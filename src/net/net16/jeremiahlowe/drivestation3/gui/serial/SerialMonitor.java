package net.net16.jeremiahlowe.drivestation3.gui.serial;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import net.net16.jeremiahlowe.drivestation3.Enums.Baudrate;
import net.net16.jeremiahlowe.drivestation3.Enums.LineSeperator;
import net.net16.jeremiahlowe.drivestation3.Utility;
import net.net16.jeremiahlowe.drivestation3.assets.AssetGetter;

//TODO: Start testing this shit boi
public class SerialMonitor extends JFrame implements SerialPortDataListener{
	private JPanel contentPane;
	private JTextField textField;
	private JTextArea txtConsole;
	private JButton btnSend;
	private JComboBox<LineSeperator> lineSeperatorBox;
	private JComboBox<Baudrate> baudRateBox;
	private LineSeperator lineSeperator = LineSeperator.None;
	private Baudrate baudrate = Baudrate.$9_600;
	private SerialPort serialPort;
	public static void main(String[] args) {
		SerialPort ser = SerialDialog.openSerialDialog();
		SerialMonitor frame = SerialMonitor.create(ser);
		frame.openPort();
	}
	public static SerialMonitor create(SerialPort ser){
		SerialMonitor frame = new SerialMonitor();
		frame.serialPort = ser;
		frame.serialPort.addDataListener(frame);
		frame.setVisible(true);
		return frame;
	}
	public SerialPort getSerial(){
		return serialPort;
	}
	private SerialMonitor() {
		setTitle("Serial monitor");
		setIconImage(AssetGetter.getImage("term.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		txtConsole = new JTextArea();
		txtConsole.setText(Utility.getSevenNationArmyLyrics());
		scrollPane.setViewportView(txtConsole);
		Box horizontalBox = Box.createHorizontalBox();
		contentPane.add(horizontalBox, BorderLayout.SOUTH);
		textField = new JTextField();
		horizontalBox.add(textField);
		textField.setColumns(10);
		btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sendSerial(textField.getText(), true);
			}
		});
		horizontalBox.add(btnSend);
		lineSeperatorBox = new JComboBox<LineSeperator>();
		lineSeperatorBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setLineSeperator((LineSeperator) lineSeperatorBox.getSelectedItem(), false);
			}
		});
		lineSeperatorBox.setModel(new DefaultComboBoxModel<LineSeperator>(LineSeperator.values()));
		horizontalBox.add(lineSeperatorBox);
		baudRateBox = new JComboBox<Baudrate>();
		baudRateBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setBaudRate((Baudrate) baudRateBox.getSelectedItem(), false, true);
			}
		});
		baudRateBox.setModel(new DefaultComboBoxModel<Baudrate>(Baudrate.values()));
		baudRateBox.setSelectedIndex(3);
		horizontalBox.add(baudRateBox);
		JFrame ref = this;
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent we){
				if(serialPort.isOpen()) serialPort.closePort();
				if(serialPort.isOpen()) JOptionPane.showMessageDialog(ref, "Couldn't close " + serialPort.getSystemPortName());
			}
		});
	}
	public void sendSerial(String text) {sendSerial(text, false);}
	public void sendSerial(String text, boolean useLineSeperator) {
		if(useLineSeperator) text += lineSeperator.toSuffix();
		//TODO: Implement code that actually sends through serial port
		try{serialPort.getOutputStream().write(text.getBytes());}
		catch(Exception e){
			System.err.println(e);
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Couldn't send message \"" + text + "\"" + System.lineSeparator() + e);
		}
	}
	public void setBaudRate(Baudrate baudrate){setBaudRate(baudrate, true, true);}
	public void setBaudRate(Baudrate baudrate, boolean updateGui, boolean updateComms) {
		if(updateGui) baudRateBox.setSelectedItem(baudrate);
		this.baudrate = baudrate;
		serialPort.setBaudRate(this.baudrate.toBaudrate());
		System.out.println("Set " + serialPort + " baudrate to " + this.baudrate.toBaudrate());
	}
	public void setLineSeperator(LineSeperator lineSeperator){setLineSeperator(lineSeperator, true);}
	public void setLineSeperator(LineSeperator lineSeperator, boolean updateGui) {
		if(updateGui) lineSeperatorBox.setSelectedItem(lineSeperator);
		this.lineSeperator = lineSeperator;
	}
	public void clearConsole(){txtConsole.setText("");}
	public void openPort(){
		if(!serialPort.openPort()){
			JOptionPane.showMessageDialog(this, "Couldn't open serial port on " + serialPort.getSystemPortName());
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
	}
	@Override
	public int getListeningEvents() {
		//TODO: Decide listening event type
		return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
		//return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
		//return SerialPort.LISTENING_EVENT_DATA_WRITTEN;
	}
	@Override
	public void serialEvent(SerialPortEvent spe) {
		byte[] dataBytes = spe.getReceivedData();
		String data = "";
		for(int i = 0; i < dataBytes.length; i++) data += (char) dataBytes[i];
		txtConsole.append(data);
	}
}
