package seedu.saveit.logic.commands;

/**
 * Represents a danger command that needs confirmation before being executed.
 */
public abstract class DangerCommand extends Command {
    public static final String ASK_FOR_CONFIRMATION = "Are you sure to %s ? Please enter Yes(Y) to confirm.";
    public static final String CONFIRMATION_FAILED = "Command: '%s' is canceled.";

    public abstract CommandResult askForConfirmation();
    public abstract CommandResult failedConfirmation();
}
