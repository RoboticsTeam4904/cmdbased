package org.usfirst.frc4904.standard.subsystems.motor.speedmodifiers;


import org.usfirst.frc4904.standard.LogKitten;
import org.usfirst.frc4904.standard.Util;
import org.usfirst.frc4904.standard.custom.sensors.InvalidSensorException;
import org.usfirst.frc4904.standard.custom.sensors.PDP;

/**
 * A SpeedModifier that does brownout protection and voltage ramping.
 * This is designed to reduce power consumption (via voltage ramping)
 * and prevent RoboRIO/router brownouts.
 */
public class AccelerationCap implements SpeedModifier {
	public final static double MAXIMUM_MOTOR_INCREASE_PER_SECOND = 2.4;
	public final static double MAXIMUM_MOTOR_DECREASE_PER_SECOND = 4.8;
	public final static double ANTI_BROWNOUT_BACKOFF_PER_SECOND = 5.6; // How much to throttle a motor down to avoid brownout
	public final static double ANTI_BROWNOUT_WEAK_BACKOFF_PER_SECOND = 1.2;
	public final static double DEFAULT_HARD_STOP_VOLTAGE = 10.0;
	protected final static double TIMEOUT_SECONDS = 0.5; // If we do not get a value for this long, set the motor to zero (this is designed to handle the case where the robot is disabled with the motors still running_
	protected final static double VOLTAGE_DROP_SCALE = 0.4;
	protected final static double TICKS_PER_PDP_DATA = 5; // PDP update speed (100ms) / Scheduler loop time (20ms)
	protected long lastUpdate; // in milliseconds
	protected final PDP pdp;
	protected final double hardStopVoltage;
	protected final int channels[];
	protected double currentSpeed;
	protected double voltageDrop;

	/**
	 * A SpeedModifier that does brownout protection and voltage ramping.
	 * This is designed to reduce power consumption (via voltage ramping)
	 * and prevent RoboRIO/router brownouts.
	 *
	 * @param pdp
	 *        The robot's power distribution panel.
	 *        This is used to monitor the battery voltage.
	 * @param softStopVoltage
	 *        Voltage to stop increasing motor speed at.
	 * @param hardStopVoltage
	 *        Voltage to begin decreasing motor speed at.
	 */
	public AccelerationCap(PDP pdp, double hardStopVoltage, int... channels) {
		this.pdp = pdp;
		this.hardStopVoltage = hardStopVoltage;
		this.channels = channels;
		currentSpeed = 0;
		voltageDrop = 0;
		lastUpdate = System.currentTimeMillis();
	}

	/**
	 * A SpeedModifier that does brownout protection and voltage ramping.
	 * This is designed to reduce power consumption (via voltage ramping)
	 * and prevent RoboRIO/router brownouts.
	 *
	 * Default hard stop voltage is 7.0 volts.
	 *
	 * @param pdp
	 *        The robot's power distribution panel.
	 *        This is used to monitor the battery voltage.
	 */
	public AccelerationCap(PDP pdp, int... channels) {
		this(pdp, AccelerationCap.DEFAULT_HARD_STOP_VOLTAGE, channels);
	}

	protected double calculate(double inputSpeed) {
		double deltaTime = (System.currentTimeMillis() - lastUpdate) / 1000.0;
		lastUpdate = System.currentTimeMillis();
		// Update current data
		double newVoltageDrop = voltageDrop;
		try {
			double currentCurrent = 0.0;
			for (int channel : channels) {
				currentCurrent += pdp.getCurrentSafely(channel);
			}
			newVoltageDrop = currentCurrent * pdp.getBatteryResistanceSafely();
		}
		catch (InvalidSensorException e) {
			LogKitten.ex(e);
		}
		double lastVoltageDrop = voltageDrop;
		if (!new Util.Range(lastVoltageDrop - 0.01, lastVoltageDrop + 0.01).contains(newVoltageDrop)) {
			voltageDrop = newVoltageDrop; // This prevents the delta voltage drop from going to zero if multiple ticks go by between PDP reads
		}
		// If we have not called this function in a while, we were probably disabled, so we should just output zero
		if (deltaTime > AccelerationCap.TIMEOUT_SECONDS) {
			return 0;
		}
		// After doing updates, check for low battery voltage first
		double currentVoltage = pdp.getVoltage(); // Allow fallback to DS voltage
		if (currentVoltage < hardStopVoltage) { // If we are below hardStopVoltage, start backing off
			double outputSpeed = currentSpeed
				- AccelerationCap.ANTI_BROWNOUT_BACKOFF_PER_SECOND * Math.signum(currentSpeed) * deltaTime;
			if (Math.abs(outputSpeed) <= AccelerationCap.ANTI_BROWNOUT_BACKOFF_PER_SECOND) {
				return 0;
			}
			return outputSpeed;
		}
		if (Math.abs(inputSpeed) < Math.abs(currentSpeed) && Math.signum(inputSpeed) == Math.signum(currentSpeed)) {
			// Ramp down (faster) for the sake of the gearboxes
			if (Math.abs(currentSpeed - inputSpeed) < AccelerationCap.MAXIMUM_MOTOR_DECREASE_PER_SECOND * deltaTime) {
				return inputSpeed;
			}
			if (inputSpeed > currentSpeed) {
				return currentSpeed + AccelerationCap.MAXIMUM_MOTOR_DECREASE_PER_SECOND * deltaTime;
			}
			if (inputSpeed < currentSpeed) {
				return currentSpeed - AccelerationCap.MAXIMUM_MOTOR_DECREASE_PER_SECOND * deltaTime;
			}
		}
		double rampedSpeed = inputSpeed;
		// Ramping
		if (Math.abs(currentSpeed - inputSpeed) > AccelerationCap.MAXIMUM_MOTOR_INCREASE_PER_SECOND * deltaTime) {
			if (inputSpeed > currentSpeed) {
				rampedSpeed = currentSpeed + AccelerationCap.MAXIMUM_MOTOR_INCREASE_PER_SECOND * deltaTime;
			} else if (inputSpeed < currentSpeed) {
				rampedSpeed = currentSpeed - AccelerationCap.MAXIMUM_MOTOR_INCREASE_PER_SECOND * deltaTime;
			}
		}
		// After ramping, apply brown-out protection
		// Even if we are still above the hard stop voltage, try to avoid going below next tick
		double deltaVoltageDrop = newVoltageDrop - lastVoltageDrop;
		if (currentVoltage < hardStopVoltage + newVoltageDrop + deltaVoltageDrop * AccelerationCap.TICKS_PER_PDP_DATA) {
			LogKitten.wtf("Preventative capping");
			return currentSpeed
				- AccelerationCap.ANTI_BROWNOUT_WEAK_BACKOFF_PER_SECOND * Math.signum(currentSpeed) * deltaTime;
		}
		return rampedSpeed;
	}

	/**
	 * Modify the input speed and get the new output. AccelerationCap does voltage ramping,
	 * which means that motor speed changes take place over 1/16th of a second rather than
	 * instantly. This decreases power consumption, but minimally affects performance.
	 *
	 * AccelerationCap also prevents brownouts by slowing motors as voltage decreases.
	 */
	@Override
	public double modify(double inputSpeed) {
		currentSpeed = calculate(inputSpeed);
		LogKitten.wtf("AccelerationCap outputed: " + currentSpeed);
		return currentSpeed;
	}
}