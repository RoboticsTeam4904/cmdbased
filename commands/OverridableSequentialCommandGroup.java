package org.usfirst.frc4904.standard.commands;

import org.usfirst.frc4904.standard.custom.Overridable;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public abstract class OverridableSequentialCommandGroup extends SequentialCommandGroup {
	protected final Overridable overridable;

	public OverridableSequentialCommandGroup(Overridable overridable) {
		super();
		this.overridable = overridable;
	}

	public OverridableSequentialCommandGroup(String name, Overridable overridable) {
		this(overridable);
		setName(name);
	}

	public final synchronized void addSequentialUnlessOverridden(CommandBase command) {
		addCommands(new RunIf(command, overridable::isNotOverridden));
	}

	public final synchronized void addSequentialUnlessOverridden(CommandBase command, double timeout) {
		addCommands(new RunIf(command.withTimeout(timeout), overridable::isNotOverridden));
	}

	public final synchronized void addSequentialIfOverridden(CommandBase command) {
		addCommands(new RunIf(command, overridable::isOverridden));
	}

	public final synchronized void addSequentialIfOverridden(CommandBase command, double timeout) {
		addCommands(new RunIf(command.withTimeout(timeout), overridable::isOverridden));
	}
}
