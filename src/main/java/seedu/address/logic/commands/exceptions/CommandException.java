package seedu.address.logic.commands.exceptions;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandToDo;

/**
 * Represents an error which occurs during execution of a {@link Command}.
 * Represents an error which occurs during execution of a {@link CommandToDo}.
 */
public class CommandException extends Exception {
    public CommandException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code CommandException} with the specified detail {@code message} and {@code cause}.
     */
    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
