package net.net16.jeremiahlowe.drivestation3.gui.driving.schemes;

import net.net16.jeremiahlowe.drivestation3.Enums.Motor;
import net.net16.jeremiahlowe.drivestation3.Enums.Speed;
import net.net16.jeremiahlowe.drivestation3.gui.driving.DriveControlListener;

public class Tracked extends ControlScheme{
	public Tracked(DriveControlListener dcl) {
		super(dcl);
	}
	public void onForward(){
		setMotor(Motor.Both, Speed.Forward);
	}
	public void onBackward(){
		setMotor(Motor.Both, Speed.Backward);
	}
	public void onLeft(){
		setMotor(Motor.A, Speed.Backward);
		setMotor(Motor.B, Speed.Forward);
	}
	public void onRight(){
		setMotor(Motor.A, Speed.Forward);
		setMotor(Motor.B, Speed.Backward);
	}
	public void onStop(){
		setMotor(Motor.Both, Speed.Stop);
	}
}
