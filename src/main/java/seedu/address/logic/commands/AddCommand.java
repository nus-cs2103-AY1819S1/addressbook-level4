package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.carpark.Carpark;

/**
 * Adds a car park to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    //public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a carpark to the address book. "
    //        + "Parameters: "
    //        + PREFIX_NAME + "NAME "
    //        + PREFIX_PHONE + "PHONE "
    //        + PREFIX_EMAIL + "EMAIL "
    //        + PREFIX_ADDRESS + "ADDRESS "
    //        + "[" + PREFIX_TAG + "TAG]...\n"
    //        + "Example: " + COMMAND_WORD + " "
    //        + PREFIX_NAME + "John Doe "
    //        + PREFIX_PHONE + "98765432 "
    //        + PREFIX_EMAIL + "johnd@example.com "
    //        + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
    //        + PREFIX_TAG + "friends "
    //        + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_USAGE = "We are not doing adds. Due for deletion.";

    public static final String MESSAGE_SUCCESS = "New car park added: %1$s";
    public static final String MESSAGE_DUPLICATE_CARPARK = "This car park already exists in the address book";

    private final Carpark toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Carpark}
     */
    public AddCommand(Carpark carpark) {
        requireNonNull(carpark);
        toAdd = carpark;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasCarpark(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CARPARK);
        }

        model.addCarpark(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
