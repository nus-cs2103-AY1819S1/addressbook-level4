package seedu.address.model.logging;

/**
 * Represents the command arguments inside an executed command.
 */
public class CommandArgs {
    private String commandArgsString;

    /**
     * Instantiates a CommandWord.
     */
    public CommandArgs(String commandArgsString) {
        this.commandArgsString = commandArgsString;
    }

    @Override
    public String toString() {
        return commandArgsString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof CommandArgs // instanceof handles nulls
            && commandArgsString == ((CommandArgs) other).toString()); // state check
    }
}
