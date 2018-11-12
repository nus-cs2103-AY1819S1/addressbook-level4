package seedu.address.logic.commands.jio;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.NotLoggedInCommandException;
import seedu.address.model.Model;
import seedu.address.model.jio.Jio;

/**
 * Creates a public jio.
 */
public class CreateJioCommand extends Command {
    public static final String COMMAND_WORD = "createJio";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new jio and adds a jio to the jiobook."
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_WEEK + "WEEK "
            + PREFIX_DAY + "DAY "
            + PREFIX_TIME + "TIME "
            + PREFIX_ADDRESS + "ADDRESS "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "MALA "
            + PREFIX_WEEK + "7 "
            + PREFIX_DAY + "wed "
            + PREFIX_TIME + "1300 "
            + PREFIX_ADDRESS + "Fine Food ";

    public static final String MESSAGE_SUCCESS = "New jio added: %1$s";
    public static final String MESSAGE_DUPLICATE_JIO = "A jio with the same name already exists";
    public static final String MESSAGE_NO_GROUP = "The group does not exist";

    private Jio toAdd;


    /**
     * Creates an createJioCommand to add the specified {@code Jio}
     */
    public CreateJioCommand(Jio jio) {
        requireNonNull(jio);
        toAdd = jio;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        // Check if user is logged in
        if (!model.isCurrentlyLoggedIn()) {
            throw new NotLoggedInCommandException(COMMAND_WORD);
        }

        // Check if there is a jio of the same name
        if (model.hasJio(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_JIO);
        }

        // Check if the group for a groupjio exists
        if (toAdd.isGroupJio() && !model.hasGroup(toAdd.getGroupName())) {
            throw new CommandException(MESSAGE_NO_GROUP);
        }

        model.createJio(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CreateJioCommand // instanceof handles nulls
                && toAdd.equals(((CreateJioCommand) other).toAdd));
    }
}
