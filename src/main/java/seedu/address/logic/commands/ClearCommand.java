package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.WishBook;

/**
 * Clears the wish book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_ALIAS = "c";

    public static final String MESSAGE_SUCCESS = "Wish book has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetData(new WishBook());
        model.commitWishBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
