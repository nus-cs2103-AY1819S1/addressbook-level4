package seedu.address.logic.commands.jio;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.NotLoggedInCommandException;
import seedu.address.model.Model;
import seedu.address.model.Name;

/**
 * Joins a jio.
 */
public class JoinJioCommand extends Command {
    public static final String COMMAND_WORD = "joinJio";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds user to the specified jio"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "MALA ";

    public static final String MESSAGE_SUCCESS = "Jio joined: %1$s";

    public static final String MESSAGE_NONEXISTENT_JIO = "This jio does not exist";
    public static final String MESSAGE_USER_IN_JIO = "You have already joined this jio";

    private Name jioName;


    /**
     * Creates an JoinJioCommand to add the specified {@code Jio}
     */
    public JoinJioCommand(Name jioName) {
        requireNonNull(jioName);
        this.jioName = jioName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        // Check if user is logged in
        if (!model.isCurrentlyLoggedIn()) {
            throw new NotLoggedInCommandException(COMMAND_WORD);
        }

        // Check if jio exists
        if (!model.hasJioName(jioName)) {
            throw new CommandException(MESSAGE_NONEXISTENT_JIO); //Jio has already been created
        }

        // Check if user is already in jio
        if (model.isCurrentUserInJioOfName(jioName)) {
            throw new CommandException(MESSAGE_USER_IN_JIO);
        }

        // Add user to the jio
        model.addCurrentUserToJioOfName(jioName);

        return new CommandResult(String.format(MESSAGE_SUCCESS, jioName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JoinJioCommand // instanceof handles nulls
                && jioName.equals(((JoinJioCommand) other).jioName));
    }

}

