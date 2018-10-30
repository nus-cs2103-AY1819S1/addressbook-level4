package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICINE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MINIMUM_STOCK_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE_PER_UNIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STOCK;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowMedicineListEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.medicine.Medicine;

/**
 * Adds a medicine to the records.
 */
public class AddMedicineCommand extends Command {

    public static final String COMMAND_WORD = "addmedicine";
    public static final String COMMAND_ALIAS = "am";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a medicine to the records. "
            + "Parameters: "
            + PREFIX_MEDICINE_NAME + "MEDICINE NAME "
            + PREFIX_MINIMUM_STOCK_QUANTITY + "MINIMUM STOCK QUANTITY "
            + PREFIX_PRICE_PER_UNIT + "PRICE PER UNIT "
            + PREFIX_SERIAL_NUMBER + "SERIAL NUMBER "
            + PREFIX_STOCK + "STOCK ";

    public static final String MESSAGE_SUCCESS = "New medicine added: %1$s";
    public static final String MESSAGE_DUPLICATE_MEDICINE = "This medicine already exists in the records";

    private final Medicine toAdd;

    /**
     * Creates an AddCommand to add the specified ({@code Medicine}
     * @param medicine The medicine to add.
     */
    public AddMedicineCommand(Medicine medicine) {
        requireNonNull(medicine);
        this.toAdd = medicine;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasMedicine(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEDICINE);
        }

        model.addMedicine(toAdd);
        model.commitAddressBook();

        EventsCenter.getInstance().post(new ShowMedicineListEvent());

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddMedicineCommand) other).toAdd));
    }
}
