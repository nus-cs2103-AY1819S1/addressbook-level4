package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowMedicineListEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists all the stocks that do not have the adequate supply, which is to say
 * current stock level is less than minimum stock quantity of that medicine.
 */
public class CheckStockCommand extends Command {

    public static final String COMMAND_WORD = "checkstock";
    public static final String COMMAND_ALIAS = "cs";
    public static final String MESSAGE_SUCCESS = "The following medicines listed in the medicine panel "
            + "are low in supply.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        model.updateFilteredMedicineList(medicine ->
                medicine.getStockValue() <= medicine.getMsqValue());

        EventsCenter.getInstance().post(new ShowMedicineListEvent());

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
