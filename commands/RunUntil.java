package org.usfirst.frc4904.standard.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.function.Supplier;

public class RunUntil extends CommandBase {

    protected final CommandBase command;
    protected final Supplier<Boolean> stopCondition;
    protected final boolean cancelOnEnd;

    public RunUntil(
        String name,
        CommandBase command,
        Supplier<Boolean> stopCondition,
        boolean cancelOnEnd
    ) {
        super();
        setName(name);
        this.command = command;
        this.stopCondition = stopCondition;
        this.cancelOnEnd = cancelOnEnd;
    }

    public RunUntil(CommandBase command, Supplier<Boolean> stopCondition) {
        this("RunUntil", command, stopCondition, true);
    }

    @Override
    public void initialize() {
        command.schedule();
    }

    @Override
    public boolean isFinished() {
        return stopCondition.get();
    }

    @Override
    public void end(boolean interrupted) {
        if (cancelOnEnd) {
            command.cancel();
        }
    }
}
