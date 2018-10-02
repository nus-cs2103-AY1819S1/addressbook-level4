package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Accounts;

/**
 * Creates a user for address book.
 */
public class CreateCommand extends Command {

    public static final String COMMAND_WORD = "create";

    //TODO: update MESSAGE_USAGE
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "username "
            + PREFIX_PASSWORD + "password ";

    public static final String MESSAGE_SUCCESS = "New user added: %1$s";
    //TODO: throw exception message

    /**
     * Creates an CreateCommand to add the specified {@code Person}
     */
    public CreateCommand(Accounts account) {
        System.out.println("Account created. Username: " + account.getUsername()
                + " Password: " + account.getPassword());
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        return null;
    }
}
