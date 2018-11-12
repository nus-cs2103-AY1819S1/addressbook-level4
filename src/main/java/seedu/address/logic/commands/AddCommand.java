package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MANAGER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to SSENISUB.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to SSENISUB. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "[p]" + PREFIX_PHONE + "PHONE "
            + "[p]" + PREFIX_EMAIL + "EMAIL "
            + "[p]" + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_DEPARTMENT + "DEPARTMENT "
            + PREFIX_MANAGER + "MANAGER "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_DEPARTMENT + "Accounting "
            + PREFIX_MANAGER + "Ben Leong "
            + PREFIX_TAG + "staff ";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in SSENISUB";
    public static final String MESSAGE_DUPLICATE_PHONE_NUMBER = "This phone number is already in use";
    public static final String MESSAGE_DUPLICATE_EMAIL = "This email is already in use";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        if (model.hasPhoneNumber(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PHONE_NUMBER);
        }

        if (model.hasEmail(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EMAIL);
        }

        model.addPerson(toAdd);
        model.commitSsenisub();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
