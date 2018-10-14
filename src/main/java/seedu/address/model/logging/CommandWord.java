package seedu.address.model.logging;

import seedu.address.logic.commands.AllCommandWords;

/**
 * Represents the command word in an executed command
 */
public class CommandWord {
    private String commandWordString;

    /**
     * Instantiates a CommandWord.
     */
    public CommandWord(String commandWordString) {
        if (!AllCommandWords.isCommandWord(commandWordString)) {
            throw new IllegalArgumentException("Not a valid commandWord!");
        }
        this.commandWordString = commandWordString;
    }

    @Override
    public String toString() {
        return commandWordString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof CommandWord // instanceof handles nulls
            && commandWordString == ((CommandWord) other).toString()); // state check
    }
}
