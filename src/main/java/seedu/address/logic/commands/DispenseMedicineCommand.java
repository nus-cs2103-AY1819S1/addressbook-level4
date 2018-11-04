package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT_TO_DISPENSE;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ShowCurrentPatientViewEvent;
import seedu.address.commons.events.ui.ShowMedicineListEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PatientQueue;
import seedu.address.model.ServedPatientList;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.QuantityToDispense;
import seedu.address.model.medicine.exceptions.InsufficientStockException;
import seedu.address.model.person.CurrentPatient;

/**
 * Dispenses the medicine to the current patient. The amount to dispense will be deducted from the stock
 * of that medicine. The amount and name of the medicine will appear in the receipt.
 */
public class DispenseMedicineCommand extends QueueCommand {

    public static final String COMMAND_WORD = "dispensemedicine";
    public static final String COMMAND_ALIAS = "dm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Dispenses the medicine to the current patient. "
            + "Existing medicine will be overriden with input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_AMOUNT_TO_DISPENSE
            + "AMOUNT "
            + "(must be an integer between 1 to 2147483647 inclusive.)";

    public static final String MESSAGE_SUCCESS = "%d x %s dispensed!";
    public static final String MESSAGE_MEDICINE_NOT_FOUND = "%s not found in records.";
    public static final String MESSAGE_MEDICINE_STOCK_INSUFFICIENT = "%s has not enough stock.";
    public static final String MESSAGE_CURRENT_PATIENT_NOT_FOUND = "This is no current patient, "
            + "use the serve command first.";

    private final Index index;
    private final QuantityToDispense quantityToDispense;

    public DispenseMedicineCommand(Index targetIndex, QuantityToDispense quantityToDispense) {
        requireAllNonNull(targetIndex, quantityToDispense);
        this.index = targetIndex;
        this.quantityToDispense = quantityToDispense;
    }

    @Override
    public CommandResult execute(Model model, PatientQueue patientQueue, CurrentPatient currentPatient,
                                 ServedPatientList servedPatientList, CommandHistory history) throws CommandException {
        if (!currentPatient.hasCurrentPatient()) {
            throw new CommandException(MESSAGE_CURRENT_PATIENT_NOT_FOUND);
        }

        List<Medicine> lastShownList = model.getFilteredMedicineList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);
        }

        Medicine medicine = lastShownList.get(index.getZeroBased());

        if (!model.hasMedicine(medicine)) {
            throw new CommandException(String.format(MESSAGE_MEDICINE_NOT_FOUND, medicine.getMedicineName()));
        }

        //if the current patient already has this medicine allocated to him,
        //return the medicine to the stock first before dispensing
        if (currentPatient.getMedicineAllocated().get(medicine) != null) {
            model.refillMedicine(medicine, currentPatient.getMedicineAllocated().get(medicine));
        }

        try {
            model.dispenseMedicine(medicine, quantityToDispense);
            currentPatient.addMedicine(medicine, quantityToDispense);
            model.commitAddressBook();
            EventsCenter.getInstance().post(new ShowMedicineListEvent());
            EventsCenter.getInstance().post(new ShowCurrentPatientViewEvent(currentPatient));
            return new CommandResult(String.format(MESSAGE_SUCCESS, quantityToDispense.getValue(),
                    medicine.getMedicineName()));
        } catch (InsufficientStockException ise) {
            throw new CommandException(String.format(MESSAGE_MEDICINE_STOCK_INSUFFICIENT, medicine.getMedicineName()));
        }

    }
}
