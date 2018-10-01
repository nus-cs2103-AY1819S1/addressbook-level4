package seedu.address.logic.Anakin_commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Anakin_Model;
import seedu.address.model.Anakin_deck.Anakin_Deck;

/**
 * Adds a person to the address book.
 */
public class Anakin_NewDeckCommand extends Anakin_Command {

    public static final String COMMAND_WORD = "newdeck";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Create a new deck inside Anakin. "
            + "Parameters: "
            + PREFIX_NAME + "NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Deck 1";

    public static final String MESSAGE_SUCCESS = "New deck added: %1$s";
    public static final String MESSAGE_DUPLICATE_DECK = "This deck already exists in Anakin";

    private final Anakin_Deck toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public Anakin_NewDeckCommand(Anakin_Deck anakin_deck) {
        requireNonNull(anakin_deck);
        toAdd = anakin_deck;
    }

    @Override
    public CommandResult execute(Anakin_Model anakinModel, CommandHistory history) throws CommandException {
        requireNonNull(anakinModel);

        if (anakinModel.hasDeck(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_DECK);
        }

        anakinModel.addDeck(toAdd);
        anakinModel.commitAnakin();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Anakin_NewDeckCommand // instanceof handles nulls
                && toAdd.equals(((Anakin_NewDeckCommand) other).toAdd));
    }
}
