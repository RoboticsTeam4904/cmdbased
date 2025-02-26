package org.usfirst.frc4904.standard.commands;

// import org.usfirst.frc4904.standard.LogKitten;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * A command that will only run if the isSafe() method returns safe. To use,
 * implement an isSafe() method, and use executeIfSafe() in place of the
 * execute() method
 */
public abstract class SafetyCommand extends Command {

    protected String reasonUnsafe;

    public SafetyCommand(String name, double timeout) {
        super();
        setName(name);
        withTimeout(timeout);
    }

    public SafetyCommand(double timeout) {
        this("SafetyCommand", timeout);
    }

    public SafetyCommand(String name) {
        super();
        setName(name);
    }

    public SafetyCommand() {
        this("SafetyCommand");
    }

    @Override
    public final void execute() {
        if (isSafe()) {
            executeIfSafe();
            return;
        }
        cancel();
        if (reasonUnsafe == null) {
            reasonUnsafe = "the required safety conditions haven't been met";
        }
        System.err.println(
            "SafetyCommand " + getName() + " cannot run because " + reasonUnsafe + ". Cancelling..."
        );
        // LogKitten.e("SafetyCommand " + getName() + " cannot run because " + reasonUnsafe + ". Cancelling...");
    }

    protected void setUnsafeReason(String reasonUnsafe) {
        this.reasonUnsafe = reasonUnsafe;
    }

    /**
     * This execute method is called the first time this Command is run after being
     * started, on the condition that isSafe() returns true.
     */
    protected abstract void executeIfSafe();

    /**
     * Determines if the command is safe to run. setUnsafeReason(String) should be
     * called to describe a safety failure. (Will be called every execute
     * iteration.)
     *
     * @return is the command safe to run?
     */
    protected abstract boolean isSafe();
}
