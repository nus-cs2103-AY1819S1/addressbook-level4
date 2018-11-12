package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowMedicineListEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.medicine.Medicine;

/**
 * Lists all the stocks that do not have the adequate supply, which is to say
 * current stock level is less than minimum stock quantity of that medicine.
 */
public class CheckStockCommand extends Command {

    public static final String COMMAND_WORD = "checkstock";
    public static final String COMMAND_ALIAS = "cs";
    public static final String MESSAGE_SUCCESS = "The following medicines listed in the medicine panel "
            + "are low in supply.";
    public static final Predicate<Medicine> MEDICINE_LOW_IN_SUPPLY_PREDICATE =
        medicine -> medicine.getStockValue() <= medicine.getMsqValue();

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        model.updateFilteredMedicineList(MEDICINE_LOW_IN_SUPPLY_PREDICATE);

        EventsCenter.getInstance().post(new ShowMedicineListEvent());

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
