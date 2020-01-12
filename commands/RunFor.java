package org.usfirst.frc4904.standard.commands;

import java.util.Set;
import java.util.Arrays;

import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.command.Command;

public class RunFor extends CommandBase {
	protected final double duration;
	protected final CommandBase command;
	protected boolean firstTick;
	protected long startMillis;

	/**
	 * Run a command for a given amount of time, in seconds. The command will be
	 * cancelled at the end. For example, if you want to go forward for 3 seconds,
	 * use: new RunFor(new GoForward(), 3)
	 *
	 * @param name          The name of this RunFor
	 * @param command       The command to be run for the duration
	 * @param duration      A duration in seconds
	 * @param interruptible Whether this command should be interruptible
	 */
	public RunFor(String name, CommandBase command, double duration) {
		setName(name);
		this.duration = duration;
		this.command = command;
		firstTick = true;
		addRequirements((Subsystem[]) this.command.getRequirements().toArray());
	}

	/**
	 * Run a command for a given amount of time, in seconds. The command will be
	 * cancelled at the end. For example, if you want to go forward for 3 seconds,
	 * use: new RunFor(new GoForward(), 3)
	 *
	 * @param command       The command to be run for the duration
	 * @param duration      A duration in seconds
	 * @param interruptible Whether this command should be interruptible
	 */
	public RunFor(CommandBase command, double duration) {
		this("RunFor[" + command.getName() + "]", command, duration);
	}

	public boolean isTimedOut() {
		return System.currentTimeMillis() - startMillis > duration;
	}

	public void initialize() {
		command.withTimeout(duration);
		startMillis = System.currentTimeMillis();
	}

	public boolean isFinished() {
		if (firstTick) {
			firstTick = false;
			return isTimedOut();
		}
		return isTimedOut() || !command.isScheduled();
	}

	@Override
	public void end(boolean inturrupted) {
		command.cancel();
		firstTick = true;
	}
}
