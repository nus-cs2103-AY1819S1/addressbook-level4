package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEDICINES;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowDefaultBrowserEvent;
import seedu.address.commons.events.ui.ShowMedicineListEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all medicines in the records to the user.
 */
public class ListStockCommand extends Command {

    public static final String COMMAND_WORD = "liststock";
    public static final String COMMAND_ALIAS = "ls";
    public static final String MESSAGE_EMPTY = "No medicine to list!";
    public static final String MESSAGE_SUCCESS = "Listed all stock.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredMedicineList(PREDICATE_SHOW_ALL_MEDICINES);

        EventsCenter.getInstance().post(new ShowMedicineListEvent());

        if (model.getFilteredMedicineList().isEmpty()) {
            return new CommandResult(MESSAGE_EMPTY);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
