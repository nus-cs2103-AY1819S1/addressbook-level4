package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEDICINES;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all medicines in the records to the user.
 */
public class ListStockCommand extends Command {

    public static final String COMMAND_WORD = "listStock";

    public static final String MESSAGE_SUCCESS = "Listed all stock.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_MEDICINES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
