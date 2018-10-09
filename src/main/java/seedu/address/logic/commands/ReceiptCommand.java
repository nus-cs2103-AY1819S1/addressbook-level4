package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.Receipt;
import seedu.address.model.person.Patient;

//integrate select command
/**
 * Generates a receipt for {@code Patient} specified by {@code index} that appears in the GUI and in a pdf.
 */
public class ReceiptCommand extends Command {
    public static final String COMMAND_WORD = "receipt";
    public static final String COMMAND_ALIAS = "rct";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Generates a receipt for the patient in the specified"
            + " index. Includes information like the date of visit, consultation fee, medicine sold etc. \n"
            + "Example: " + COMMAND_WORD + "<person's index>";

    public static final String MESSAGE_SUCCESS = "Receipt generated for patient: %1$s !";

    private final Index index;

    /**
     * Creates a ReceiptCommand for the {@code Patient} specified by {@code index}
     */
    public ReceiptCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Patient> filteredPatientList = model.getFilteredPersonList();

        if (index.getZeroBased() >= filteredPatientList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Receipt receipt;

        return new CommandResult(receipt.display());

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReceiptCommand // instanceof handles nulls
                && index.equals(((ReceiptCommand) other).index)); // state check
    }
}
