package net.net16.jeremiahlowe.drivestation3.gui.driving.schemes;

import net.net16.jeremiahlowe.drivestation3.Enums.Motor;
import net.net16.jeremiahlowe.drivestation3.Enums.Speed;
import net.net16.jeremiahlowe.drivestation3.gui.driving.DriveControlListener;

public abstract class ControlScheme {
	private DriveControlListener dcl;
	
	public ControlScheme(DriveControlListener dcl){
		this.dcl = dcl;
	}
	
	public abstract void onForward();
	public abstract void onBackward();
	public abstract void onLeft();
	public abstract void onRight();
	public abstract void onStop();
	
	public final void setMotor(Motor m, Speed s){
		dcl.setMotor(m, s);
	}
}
