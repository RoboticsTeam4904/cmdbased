package org.usfirst.frc4904.standard.commands.systemchecks;

import org.usfirst.frc4904.standard.Util;
import org.usfirst.frc4904.standard.subsystems.motor.Motor;

public class MotorCheck extends SubsystemCheck {
    protected static final double DEFAULT_SPEED = 0.5; // TODO: CHECK THIS
    protected final double speed; 
    protected static final Util.Range outputCurrentRange = new Util.Range(0.1, 0.3); // TODO: Use Current to judge speedcontrollers
    protected final Motor[] motors;

    public MotorCheck(String name, double speed, Motor... motors) {
        super(name, motors);
        this.motors = motors;
        this.speed = speed;
        
    }
    public MotorCheck(double speed, Motor... motors) {
        this("MotorCheck", speed, motors);
    }

    public MotorCheck(String name, Motor... motors) {
        this(name, DEFAULT_SPEED, motors);
    }

    public MotorCheck(Motor... motors) {
        this("MotorCheck", motors);
    }

    public void initialize() {
        for (Motor motor: motors){
            motor.set(speed);
        }
    }

    public void execute() {
        for (Motor motor: motors){
            try {
                motor.set(speed);
            } catch (Exception e) {
                updateStatus(motor.getName(), StatusMessage.SystemStatus.FAIL, e.getMessage());
            }
        }
    }
}