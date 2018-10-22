package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT_TO_DISPENSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICINE_NAME;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowMedicineListEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.medicine.MedicineName;

/**
 * Dispenses the medicine to the current patient. The amount to dispense will be deducted from the stock
 * of that medicine. The amount and name of the medicine will appear in the receipt.
 */
public class DispenseMedicineCommand extends Command {

    public static final String COMMAND_WORD = "dispenseMedicine";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Dispenses the medicine to the current patient. "
            + "Parameters: "
            + PREFIX_MEDICINE_NAME + "MEDICINE NAME "
            + PREFIX_AMOUNT_TO_DISPENSE + "AMOUNT ";

    public static final String MESSAGE_SUCCESS = "%d x %1$s dispensed!";
    public static final String MESSAGE_MEDICINE_NOT_FOUND = "%1$s not found in records.";

    private final MedicineName toDispense;
    private final Integer quantityToDispense;

    public DispenseMedicineCommand(MedicineName medicineName, Integer quantityToDispense) {
        requireNonNull(medicineName);
        this.toDispense = medicineName;
        this.quantityToDispense = quantityToDispense;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.hasMedicine(toDispense)) {
            throw new CommandException(MESSAGE_MEDICINE_NOT_FOUND);
        }

        model.dispenseMedicine(toDispense, quantityToDispense);
        model.commitAddressBook();

        EventsCenter.getInstance().post(new ShowMedicineListEvent());

        return new CommandResult(String.format(MESSAGE_SUCCESS, quantityToDispense, toDispense));
    }
}
