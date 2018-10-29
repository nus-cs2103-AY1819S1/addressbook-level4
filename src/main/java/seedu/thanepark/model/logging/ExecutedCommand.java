package seedu.thanepark.model.logging;

import java.util.Objects;

import seedu.thanepark.logic.commands.AllCommandWords;

/**
 * Represents an executed command with its command word and command args.
 */
public class ExecutedCommand {
    private static final String INVALID_COMMAND_WORD = "<invalid_command_word>";

    private final String commandWord;
    private final String commandArgs;

    /**
     * Instantiates an ExecutedCommand from a complete String.
     */
    public ExecutedCommand(String commandString) {
        final String[] splitCommandString = commandString.split(" ", 2);
        if (AllCommandWords.isCommandWord(splitCommandString[0])
            || splitCommandString[0].equals(INVALID_COMMAND_WORD)) {
            commandWord = splitCommandString[0];
            commandArgs = splitCommandString.length >= 2 ? splitCommandString[1] : "";
        } else {
            commandWord = INVALID_COMMAND_WORD;
            commandArgs = commandString;
        }
    }

    public String getCommandWord() {
        return commandWord;
    }

    public String getCommandArgs() {
        return commandArgs;
    }

    @Override
    public String toString() {
        return commandWord + " " + commandArgs;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ExecutedCommand // instanceof handles nulls
            && Objects.equals(toString(), other.toString())); // state check
    }

}
