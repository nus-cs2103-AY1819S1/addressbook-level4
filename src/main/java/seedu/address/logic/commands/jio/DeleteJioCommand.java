package seedu.address.logic.commands.jio;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.restaurant.Name;

/**
 * Deletes a jio the user started.
 */
public class DeleteJioCommand extends Command {
    public static final String COMMAND_WORD = "deleteJio";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an existing jio from the list of jios."
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "MALA";

    public static final String MESSAGE_SUCCESS = "Jio deleted: %1$s";

    // TODO
    public static final String MESSAGE_NONEXISTENT_JIO = "Jio does not exist.";

    // TODO
    private final Name jioName;

    /**
     * Deletes a jio to add the specified {@code Integer} review, that ranges from 1 - 5.
     */
    public DeleteJioCommand(Name name) {
        requireNonNull(name);
        this.jioName = name;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        // TODO
        requireNonNull(model);

        if (!model.hasJioName(jioName)) {
            throw new CommandException(MESSAGE_NONEXISTENT_JIO); //Jio does not exist
        }
        model.removeJioOfName(jioName);
        //model.commitAddressBook(); //TODO: Check if need to commit. If need, then create commit method

        return new CommandResult(String.format(MESSAGE_SUCCESS, jioName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteJioCommand // instanceof handles nulls
                && jioName.equals(((DeleteJioCommand) other).jioName));
    }
}
