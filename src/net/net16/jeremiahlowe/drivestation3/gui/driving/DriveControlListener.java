package net.net16.jeremiahlowe.drivestation3.gui.driving;

import net.net16.jeremiahlowe.drivestation3.Enums.Motor;
import net.net16.jeremiahlowe.drivestation3.Enums.Speed;

public interface DriveControlListener {
	public void setMotor(Motor track, Speed speed);
}
