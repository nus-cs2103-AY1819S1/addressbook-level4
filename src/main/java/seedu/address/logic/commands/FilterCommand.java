package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.carpark.Carpark;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class FilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters the carpark depending on the tags attached.\n"
            + "Parameters: Tags ... \n"   //-----------------------------------------------------CHANGE LATER
            + "Example: " + COMMAND_WORD + "f/TRUE";

    public static final String MESSAGE_DELETE_CARPARK_SUCCESS = "Filtered Carparks.";

    //private final Index targetIndex;
    private boolean freeParking;

    public FilterCommand(boolean freeParking) {
        this.freeParking = freeParking;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Carpark> lastShownList = model.getFilteredCarparkList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CARPARK_DISPLAYED_INDEX);
        }

        Carpark carparkToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteCarpark(carparkToDelete);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_CARPARK_SUCCESS, carparkToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
