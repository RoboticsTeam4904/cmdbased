package org.usfirst.frc4904.standard.custom.motioncontrollers;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import org.usfirst.frc4904.standard.subsystems.motor.TalonMotorController;

public class CANTalonFX extends WPI_TalonFX implements TalonMotorController {
	protected static final NeutralMode DEFAULT_NEUTRAL_MODE 	= NeutralMode.Coast;
	protected static final InvertType  DEFAULT_INVERT_TYPE  	= InvertType.FollowMaster;
	protected static final double	   DEFAULT_NEUTRAL_DEADBAND = 0.001;	// 0.1%, the minimum possible value 

	/**
	 * Represents a Falcon motor in code. You probably want NeutralMode.Brake,
	 * InvertType.FollowMaster.
	 *
	 * @param deviceNumber              Usually the CAN ID of the device, 
	 * 									declared in RobotMap
	 * @param neutralMode               Whether the motor should brake or coast
	 *                                  when the the output is near zero, or
	 *                                  .disable() or .stopMotor() are called.
	 * @param inverted                  InvertMode of the motor. If this will be
	 *                                  part of a motor group, consider using
	 *                                  FolloMaster or OpposeMaster, so that you
	 *                                  can invert the entire motor group by
	 *                                  inverting the lead motor. Use None or
	 *                                  InvertMotorOutput for the lead motor.
	 * @param neutralDeadbandPercent    Percent output range around zero to
	 *                                  enable neutralOutput (brake/coast mode)
	 *                                  instead. For more info, see
	 *                                  https://v5.docs.ctr-electronics.com/en/latest/ch13_MC.html#neutral-deadband
	 */
	public CANTalonFX(int deviceNumber, NeutralMode neutralMode, InvertType inverted, double neutralDeadbandPercent) {
		super(deviceNumber);
		configFactoryDefault();	// use default settings to prevent unexpected behavior, reccommended in examples
		configNeutralDeadband(neutralDeadbandPercent);
		setNeutralMode(neutralMode);
		setInverted(inverted);
	}
	/**
	 * Represents a Falcon motor in code. You probably want NeutralMode.Brake,
	 * InvertType.FollowMaster.
	 *
	 * @param deviceNumber              Usually the CAN ID of the device, 
	 * 									declared in RobotMap
	 * @param neutralMode               Whether the motor should brake or coast
	 *                                  when the the output is near zero, or
	 *                                  .disable() or .stopMotor() are called.
	 * @param inverted                  InvertMode of the motor. If this will be
	 *                                  part of a motor group, consider using
	 *                                  FolloMaster or OpposeMaster, so that you
	 *                                  can invert the entire motor group by
	 *                                  inverting the lead motor. Use None or
	 *                                  InvertMotorOutput for the lead motor.
	 */
	public CANTalonFX(int deviceNumber, NeutralMode neutralMode, InvertType inverted) {
		this(deviceNumber, neutralMode, inverted, DEFAULT_NEUTRAL_DEADBAND);
	}

	/**
	 * Setting to enable brake mode on neutral (when .neutralOutput(),
	 * .disable(), or .stopMotor() is called, or when output percent is within
	 * neutralDeadbandPercent of zero).
	 *
	 * This does not brake the motor. Use .neutralOutput() instead, after
	 * setBrakeOnNeutral.
	 */
	public TalonMotorController setBrakeOnNeutral() {
		setNeutralMode(NeutralMode.Brake);
		return this;
	}
	/**
	 * Setting to enable coast mode on neutral (when .neutralOutput(),
	 * .disable(), or .stopMotor() is called, or when output percent is within
	 * neutralDeadbandPercent of zero).
	 *
	 * This does not coast the motor. Use .neutralOutput() instead, after
	 * setCoastOnNeutral.
	 */
	public TalonMotorController setCoastOnNeutral() {
		setNeutralMode(NeutralMode.Coast);
		return this;
	}

	@Override
	public TalonMotorController follow(TalonMotorController leader) {
		follow(leader);
		return this;
	}
}