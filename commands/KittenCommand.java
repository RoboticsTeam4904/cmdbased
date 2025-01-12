package org.usfirst.frc4904.standard.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.usfirst.frc4904.standard.LogKitten;

/**
 *
 */
public class KittenCommand extends CommandBase {

    protected final String message;
    protected final LogKitten.KittenLevel level;

    public KittenCommand(String message, LogKitten.KittenLevel level) {
        this.message = message;
        this.level = level;
    }

    @Override
    public void initialize() {
        LogKitten.logMessage(message, level, false);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
