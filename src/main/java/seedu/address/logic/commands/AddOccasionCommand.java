package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCASIONDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCASIONLOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCASIONNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.occasion.Occasion;


/**
 * Adds an occasion to the address book.
 */
public class AddOccasionCommand extends Command {

    public static final String COMMAND_WORD = "addoccasion";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an occasion to the address book. \n"
            + "Parameters: "
            + PREFIX_OCCASIONNAME + "OCCASION_NAME "
            + PREFIX_OCCASIONDATE + "OCCASION_DATE "
            + PREFIX_OCCASIONLOCATION + "LOCATION "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_OCCASIONNAME + "discussion "
            + PREFIX_OCCASIONDATE + "2018-01-01 "
            + PREFIX_OCCASIONLOCATION + "SoC "
            + PREFIX_TAG + "project "
            + PREFIX_TAG + "gg ";

    public static final String MESSAGE_SUCCESS = "New occasion added: %1$s";
    public static final String MESSAGE_DUPLICATE_OCCASION = "This occasion already exists in the address book";

    private final Occasion toAdd;

    public AddOccasionCommand(Occasion occasion) {
        requireNonNull(occasion);
        this.toAdd = occasion;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasOccasion(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_OCCASION);
        }

        model.addOccasion(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }



    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddOccasionCommand // instanceof handles nulls
                && toAdd.equals(((AddOccasionCommand) other).toAdd));
    }
}
