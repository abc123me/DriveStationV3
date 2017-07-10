package net.net16.jeremiahlowe.drivestation3.gui.serial;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.fazecast.jSerialComm.SerialPort;

import net.net16.jeremiahlowe.drivestation3.Enums.Baudrate;
import net.net16.jeremiahlowe.drivestation3.Enums.DataBits;
import net.net16.jeremiahlowe.drivestation3.Enums.Parity;
import net.net16.jeremiahlowe.drivestation3.Enums.StopBits;
import net.net16.jeremiahlowe.drivestation3.Utility;
import net.net16.jeremiahlowe.drivestation3.assets.AssetGetter;
import net.net16.jeremiahlowe.drivestation3.gui.misc.SelectionDialog;

public class SerialDialog extends JFrame implements PortSelectionListener{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private boolean hasPort = false, isDone = false;;
	private JTextField portField;
	private JComboBox<Parity> parityBox;
	private JComboBox<Baudrate> baudRateBox; 
	private JComboBox<StopBits> stopBitsBox;
	private JComboBox<DataBits> dataBitsBox;
	private JButton btnDone;
	public static void main(String[] args) throws Exception{
		SerialPort ser = openSerialDialog();
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
				if(ser.isOpen()) ser.closePort();
			}
		}));
		while(System.in.available() != -1) os.write(System.in.read());
	}
	public static SerialPort openSerialDialog(){
		SerialDialog sd = new SerialDialog();
		sd.setVisible(true);
		while(!sd.hasPort || !sd.isDone){try{Thread.sleep(50);}catch(InterruptedException e){}}
		SerialPort out = sd.generateSerialPort();
		return out;
	}
	public SerialPort generateSerialPort(){
		SerialPort out = SerialPort.getCommPort(portField.getText());
		out.setBaudRate(((Baudrate) baudRateBox.getSelectedItem()).toBaudrate());
		out.setParity(((Parity) parityBox.getSelectedItem()).toParity());
		out.setNumStopBits(((StopBits) stopBitsBox.getSelectedItem()).toStopBits());
		out.setNumDataBits(((DataBits) dataBitsBox.getSelectedItem()).toDataBits());
		return out;
	}
	public SerialDialog() {
		setIconImage(AssetGetter.getImage("serial.png"));
		setTitle("Serial configurator");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 390, 259);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
		contentPane.add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{81, 81, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 24, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		JLabel lblPort = new JLabel("Port");
		GridBagConstraints gbc_lblPort = new GridBagConstraints();
		gbc_lblPort.anchor = GridBagConstraints.WEST;
		gbc_lblPort.insets = new Insets(0, 0, 5, 5);
		gbc_lblPort.gridx = 0;
		gbc_lblPort.gridy = 0;
		panel.add(lblPort, gbc_lblPort);
		Box horizontalBox_1 = Box.createHorizontalBox();
		GridBagConstraints gbc_horizontalBox_1 = new GridBagConstraints();
		gbc_horizontalBox_1.anchor = GridBagConstraints.WEST;
		gbc_horizontalBox_1.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalBox_1.gridx = 1;
		gbc_horizontalBox_1.gridy = 0;
		panel.add(horizontalBox_1, gbc_horizontalBox_1);
		JButton btnSelect = new JButton("Select");
		SerialDialog ref = this;
		btnSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String[] ports = Utility.getSerialPortNames();
				new PortSelectionThread(ports, ref, ref).start();
			}
		});
		horizontalBox_1.add(btnSelect);
		portField = new JTextField();
		portField.setEditable(false);
		portField.setText("No port selected");
		horizontalBox_1.add(portField);
		portField.setColumns(10);
		JLabel lblNewLabel = new JLabel("Baud rate");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		baudRateBox = new JComboBox<Baudrate>();
		baudRateBox.setModel(new DefaultComboBoxModel<Baudrate>(Baudrate.values()));
		baudRateBox.setSelectedIndex(2);
		baudRateBox.setSelectedItem(Baudrate.$9_600);
		GridBagConstraints gbc_baudRateBox = new GridBagConstraints();
		gbc_baudRateBox.insets = new Insets(0, 0, 5, 0);
		gbc_baudRateBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_baudRateBox.gridx = 1;
		gbc_baudRateBox.gridy = 1;
		panel.add(baudRateBox, gbc_baudRateBox);
		JLabel lblDataBits = new JLabel("Data bits");
		GridBagConstraints gbc_lblDataBits = new GridBagConstraints();
		gbc_lblDataBits.anchor = GridBagConstraints.WEST;
		gbc_lblDataBits.fill = GridBagConstraints.VERTICAL;
		gbc_lblDataBits.insets = new Insets(0, 0, 5, 5);
		gbc_lblDataBits.gridx = 0;
		gbc_lblDataBits.gridy = 2;
		panel.add(lblDataBits, gbc_lblDataBits);
		dataBitsBox = new JComboBox<DataBits>();
		GridBagConstraints gbc_dataBitsBox = new GridBagConstraints();
		gbc_dataBitsBox.fill = GridBagConstraints.BOTH;
		gbc_dataBitsBox.insets = new Insets(0, 0, 5, 0);
		gbc_dataBitsBox.gridx = 1;
		gbc_dataBitsBox.gridy = 2;
		panel.add(dataBitsBox, gbc_dataBitsBox);
		dataBitsBox.setModel(new DefaultComboBoxModel<DataBits>(DataBits.values()));
		dataBitsBox.setSelectedIndex(0);
		JLabel lblStopBits = new JLabel("Stop bits");
		GridBagConstraints gbc_lblStopBits = new GridBagConstraints();
		gbc_lblStopBits.anchor = GridBagConstraints.WEST;
		gbc_lblStopBits.fill = GridBagConstraints.VERTICAL;
		gbc_lblStopBits.insets = new Insets(0, 0, 5, 5);
		gbc_lblStopBits.gridx = 0;
		gbc_lblStopBits.gridy = 3;
		panel.add(lblStopBits, gbc_lblStopBits);
		stopBitsBox = new JComboBox<StopBits>();
		GridBagConstraints gbc_stopBitsBox = new GridBagConstraints();
		gbc_stopBitsBox.fill = GridBagConstraints.BOTH;
		gbc_stopBitsBox.insets = new Insets(0, 0, 5, 0);
		gbc_stopBitsBox.gridx = 1;
		gbc_stopBitsBox.gridy = 3;
		panel.add(stopBitsBox, gbc_stopBitsBox);
		stopBitsBox.setModel(new DefaultComboBoxModel<StopBits>(StopBits.values()));
		stopBitsBox.setSelectedIndex(0);
		JLabel lblParity = new JLabel("Parity");
		GridBagConstraints gbc_lblParity = new GridBagConstraints();
		gbc_lblParity.anchor = GridBagConstraints.WEST;
		gbc_lblParity.fill = GridBagConstraints.VERTICAL;
		gbc_lblParity.insets = new Insets(0, 0, 0, 5);
		gbc_lblParity.gridx = 0;
		gbc_lblParity.gridy = 4;
		panel.add(lblParity, gbc_lblParity);
		parityBox = new JComboBox<Parity>();
		GridBagConstraints gbc_parityBox = new GridBagConstraints();
		gbc_parityBox.fill = GridBagConstraints.BOTH;
		gbc_parityBox.gridx = 1;
		gbc_parityBox.gridy = 4;
		panel.add(parityBox, gbc_parityBox);
		parityBox.setModel(new DefaultComboBoxModel<Parity>(Parity.values()));
		parityBox.setSelectedIndex(0);
		Component verticalStrut = Box.createVerticalStrut(5);
		contentPane.add(verticalStrut, BorderLayout.NORTH);
		Component horizontalStrut = Box.createHorizontalStrut(5);
		contentPane.add(horizontalStrut, BorderLayout.WEST);
		Component horizontalStrut_1 = Box.createHorizontalStrut(5);
		contentPane.add(horizontalStrut_1, BorderLayout.EAST);
		Box verticalBox = Box.createVerticalBox();
		contentPane.add(verticalBox, BorderLayout.SOUTH);
		Box horizontalBox = Box.createHorizontalBox();
		verticalBox.add(horizontalBox);
		btnDone = new JButton("Done!");
		btnDone.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!hasPort) JOptionPane.showMessageDialog(ref, "Please select a port to countinue");
				else{
					ref.dispatchEvent(new WindowEvent(ref, WindowEvent.WINDOW_CLOSING));
					isDone = true;
				}
			}
		});
		horizontalBox.add(btnDone);
		Component verticalStrut_1 = Box.createVerticalStrut(5);
		verticalBox.add(verticalStrut_1);
	}
	@Override
	public void onPortSelected(String port) {
		System.out.println("Selected port: " + port);
		if(port != null && port != ""){
			hasPort = true;
			portField.setText(port);
		}
		else{
			hasPort = false;
			portField.setText("No port selected");
		}
	}
}
class PortSelectionThread extends Thread{
	private final Component parent;
	private final String[] ports;
	private final PortSelectionListener psl;
	public PortSelectionThread(String[] ports, Component parent, PortSelectionListener psl){
		this.parent = parent;
		this.ports = ports;
		this.psl = psl;
	}
	@Override
	public void run(){
		psl.onPortSelected(SelectionDialog.showSelectionDialog(ports, parent, "Select a port"));
	}
}
interface PortSelectionListener{
	void onPortSelected(String port);
}
