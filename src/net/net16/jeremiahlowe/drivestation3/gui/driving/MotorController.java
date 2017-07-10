package net.net16.jeremiahlowe.drivestation3.gui.driving;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import net.net16.jeremiahlowe.drivestation3.Enums.Motor;
import net.net16.jeremiahlowe.drivestation3.Enums.Speed;
import net.net16.jeremiahlowe.drivestation3.gui.driving.schemes.ControlScheme;

public class MotorController extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel trackLabel;
	public MotorController(ControlScheme cs, Motor track) {
		Box trackArea = Box.createVerticalBox();
		add(trackArea);
		Box horizontalBox = Box.createHorizontalBox();
		trackArea.add(horizontalBox);
		JButton btnFwd1 = new JButton("/\\");
		horizontalBox.add(btnFwd1);
		Component horizontalStrut = Box.createHorizontalStrut(5);
		horizontalBox.add(horizontalStrut);
		JToggleButton btnLockFwd1 = new JToggleButton("Lock");
		horizontalBox.add(btnLockFwd1);
		Component horizontalGlue = Box.createHorizontalGlue();
		horizontalBox.add(horizontalGlue);
		Box horizontalBox_2 = Box.createHorizontalBox();
		trackArea.add(horizontalBox_2);
		trackLabel = new JLabel("");
		horizontalBox_2.add(trackLabel);
		Box horizontalBox_1 = Box.createHorizontalBox();
		trackArea.add(horizontalBox_1);
		JButton btnBwd1 = new JButton("\\/");
		horizontalBox_1.add(btnBwd1);
		Component horizontalStrut_1 = Box.createHorizontalStrut(5);
		horizontalBox_1.add(horizontalStrut_1);
		JToggleButton btnLockBwd1 = new JToggleButton("Lock");
		horizontalBox_1.add(btnLockBwd1);
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		horizontalBox_1.add(horizontalGlue_1);
		btnLockFwd1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnLockBwd1.setSelected(false);
				Speed speed = btnLockFwd1.isSelected() ? Speed.Forward : Speed.Stop;
				cs.setMotor(track, speed);
			}
		});
		btnLockBwd1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnLockFwd1.setSelected(false);
				Speed speed = btnLockBwd1.isSelected() ? Speed.Backward : Speed.Stop;
				cs.setMotor(track, speed);
			}
		});
		btnFwd1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				cs.setMotor(track, Speed.Forward);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				cs.setMotor(track, Speed.Stop);
			}
		});
		btnBwd1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				cs.setMotor(track, Speed.Backward);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				cs.setMotor(track, Speed.Stop);
			}
		});
	}
	@Override
	public void setName(String name){
		trackLabel.setText(name);
	}
	@Override
	public String getName(){
		return trackLabel.getText();
	}
}
