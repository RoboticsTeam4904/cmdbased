package org.usfirst.frc4904.standard.subsystems.motor;


import org.usfirst.frc4904.standard.commands.motor.MotorIdle;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Motor extends Subsystem implements SpeedController {
	protected final SpeedController motor;
	private final String haha;
	
	public Motor(String name, SpeedController motor) {
		this.motor = motor;
		haha = name;
	}
	
	protected void initDefaultCommand() {
		setDefaultCommand(new MotorIdle(this));
	}
	
	public void pidWrite(double arg0) {
		motor.pidWrite(arg0);
	}
	
	public void disable() {
		motor.disable();
	}
	
	public double get() {
		return motor.get();
	}
	
	public void set(double arg0) {
		motor.set(arg0);
		if (haha == "WinchMotor") {
			System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName() + Thread.currentThread().getStackTrace()[3].getClassName() + " : " + haha + ": " + arg0);
		}
	}
	
	public void set(double arg0, byte arg1) {
		motor.set(arg0, arg1);
	}
}
