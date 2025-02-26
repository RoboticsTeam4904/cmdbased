// package org.usfirst.frc4904.standard.commands.chassis;

// import org.usfirst.frc4904.standard.custom.motioncontrollers.MotionController;
// import org.usfirst.frc4904.standard.custom.sensors.IMU;
// import org.usfirst.frc4904.standard.subsystems.chassis.Chassis;
// import edu.wpi.first.wpilibj2.command.Command;

// public class ChassisTurnAbsolute extends ChassisTurn {
// 	/**
// 	 * Constructor This command rotates the chassis to a position relative to the
// 	 * starting point of the robot (e.g. the position where the imu was last reset).
// 	 *
// 	 * @param chassis
// 	 * @param finalAngle
// 	 * @param imu
// 	 * @param motionController
// 	 */
// 	public ChassisTurnAbsolute(Chassis chassis, double finalAngle, IMU imu, MotionController motionController) {
// 		super("Chassis Turn Absolute", chassis, finalAngle, imu, motionController);
// 	}

// 	/**
// 	 * Constructor This command rotates the chassis to a position relative to the
// 	 * starting point of the robot (e.g. the position where the imu was last reset).
// 	 *
// 	 * @param chassis
// 	 * @param finalAngle
// 	 * @param imu
// 	 * @param fallbackCommand  If the sensor fails for some reason, this command
// 	 *                         will be cancelled, then the fallbackCommand will
// 	 *                         start
// 	 * @param motionController
// 	 */
// 	public ChassisTurnAbsolute(Chassis chassis, double finalAngle, IMU imu, Command fallbackCommand,
// 			MotionController motionController) {
// 		super("Chassis Turn Absolute", chassis, finalAngle, imu, fallbackCommand, motionController);
// 	}

// 	/**
// 	 * Constructor This command rotates the chassis to a position relative to the
// 	 * starting point of the robot (e.g. the position where the imu was last reset).
// 	 *
// 	 * @param name
// 	 * @param chassis
// 	 * @param finalAngle
// 	 * @param imu
// 	 * @param motionController
// 	 */
// 	public ChassisTurnAbsolute(String name, Chassis chassis, double finalAngle, IMU imu,
// 			MotionController motionController) {
// 		super(name, chassis, finalAngle, imu, motionController);
// 	}

// 	/**
// 	 * Constructor This command rotates the chassis to a position relative to the
// 	 * starting point of the robot (e.g. the position where the imu was last reset).
// 	 *
// 	 * @param name
// 	 * @param chassis
// 	 * @param finalAngle
// 	 * @param imu
// 	 * @param fallbackCommand  If the sensor fails for some reason, this command
// 	 *                         will be cancelled, then the fallbackCommand will
// 	 *                         start
// 	 * @param motionController
// 	 */
// 	public ChassisTurnAbsolute(String name, Chassis chassis, double finalAngle, IMU imu, Command fallbackCommand,
// 			MotionController motionController) {
// 		super(name, chassis, finalAngle, imu, fallbackCommand, motionController);
// 	}

// 	@Override
// 	public void initialize() {
// 		// ChassisTurnDegrees measures an initial angle and compensates for it
// 		// to make its turns relative;
// 		super.initialize();
// 		// Not anymore
// 		super.initialAngle = 0.0;
// 	}
// }
