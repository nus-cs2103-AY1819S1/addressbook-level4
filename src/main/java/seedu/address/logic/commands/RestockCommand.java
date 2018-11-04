package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ShowMedicineListEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.medicine.AmountToRestock;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.MedicineName;
import seedu.address.model.medicine.MinimumStockQuantity;
import seedu.address.model.medicine.PricePerUnit;
import seedu.address.model.medicine.SerialNumber;
import seedu.address.model.medicine.Stock;

/**
 * Restocks a particular medicine in the records.
 */
public class RestockCommand extends Command {

    public static final String COMMAND_WORD = "restock";
    public static final String COMMAND_ALIAS = "rs";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Restocks the medicine of the indicated index "
            + "by the amount given.\n"
            + "Parameters: INDEX (must be a positive integer) \n"
            + "amt/Amount ";
    public static final String MESSAGE_RESTOCK_SUCCESS = "Restock success! %1$s x %2$s added.";
    public static final String MESSAGE_INVALID_QUANTITY = "(must be an integer between 1 "
            + "to 2147483647 inclusive.)";

    private final Index index;
    private final AmountToRestock quantityToRestock;

    public RestockCommand(Index index, AmountToRestock quantityToRestock) {
        requireNonNull(index);
        requireNonNull(quantityToRestock);
        this.index = index;
        this.quantityToRestock = quantityToRestock;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Medicine> lastShownList = model.getFilteredMedicineList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);
        }

        Medicine medicineToEdit = lastShownList.get(index.getZeroBased());
        MedicineName updatedMedicineName = medicineToEdit.getMedicineName();
        MinimumStockQuantity updatedMinimumStockQuantity = medicineToEdit.getMinimumStockQuantity();
        PricePerUnit updatedPricePerUnit = medicineToEdit.getPricePerUnit();
        SerialNumber updatedSerialNumber = medicineToEdit.getSerialNumber();
        Stock updatedStock = new Stock(medicineToEdit.getStock().getValue() + quantityToRestock.getValue());
        Medicine editedMedicine = new Medicine(updatedMedicineName,
                updatedMinimumStockQuantity, updatedPricePerUnit,
                updatedSerialNumber, updatedStock);

        model.updateMedicine(medicineToEdit, editedMedicine);
        model.updateFilteredMedicineList(Model.PREDICATE_SHOW_ALL_MEDICINES);
        model.commitAddressBook();

        EventsCenter.getInstance().post(new ShowMedicineListEvent());
        return new CommandResult(String.format(MESSAGE_RESTOCK_SUCCESS, quantityToRestock, updatedMedicineName));
    }
}
