package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.TriviaBundle;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " : Clears all the cards inside the card list.\n"
            + " Parameters: null.\n"
            + " Example: clear";

    public static final String MESSAGE_SUCCESS = "Card list has been cleared!";

    public static final String MESSAGE_NO_CARDS = "No cards to be cleared";

    public ClearCommand() {}

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canClearCardList()) {
            throw new CommandException(MESSAGE_NO_CARDS);
        }
        model.resetData(new TriviaBundle());
        model.commitTriviaBundle();
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
