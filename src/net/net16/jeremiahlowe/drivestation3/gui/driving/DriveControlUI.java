package net.net16.jeremiahlowe.drivestation3.gui.driving;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import net.net16.jeremiahlowe.drivestation3.Enums.Motor;
import net.net16.jeremiahlowe.drivestation3.gui.driving.schemes.ControlScheme;

public class DriveControlUI extends JPanel{
	private static final long serialVersionUID = 1L;
	private MotorController rightMotorControl;
	private MotorController leftMotorControl;
	public DriveControlUI(ControlScheme cs) {
		leftMotorControl = new MotorController(cs, Motor.A);
		leftMotorControl.setName("Left track");
		add(leftMotorControl);
		rightMotorControl = new MotorController(cs, Motor.B);
		rightMotorControl.setName("Right track");
		add(rightMotorControl);
		KeyboardController keyboardControl = new KeyboardController(cs);
		add(keyboardControl);
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				keyboardControl.grabFocus();
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				keyboardControl.grabFocus();
			}
		});
	}
}
