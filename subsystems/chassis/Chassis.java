package org.usfirst.frc4904.standard.subsystems.chassis;


import org.usfirst.frc4904.standard.commands.chassis.ChassisIdle;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;
import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class Chassis extends Subsystem {
	protected int numberWheels;
	protected double[] motorSpeeds;
	private Motor[] motors;
	
	/**
	 * 
	 * @param name
	 * @param motors
	 *        :
	 *        all the motors that are part of this chassis. Pass from front to back, left to right
	 */
	public Chassis(String name, Motor... motors) {
		super(name);
		this.motors = motors;
	}
	
	protected void initDefaultCommand() {
		setDefaultCommand(new ChassisIdle(this));
	}
	
	public int getNumberWheels() {
		return numberWheels;
	}
	
	/**
	 * returns an array of motors of the size getNumberWheels in the order that they were passed to the constructor
	 * 
	 * @return
	 */
	public Motor[] getMotors() {
		return motors;
	}
	
	public double[] getMotorSpeeds() {
		return motorSpeeds;
	}
	
	public abstract void move2dp(double speed, double angle, double turnSpeed);
	
	public abstract void move2dc(double xSpeed, double ySpeed, double turnSpeed);
	
	public abstract void move(double speed, double turnSpeed);
}
