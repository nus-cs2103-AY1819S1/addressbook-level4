package seedu.inventory.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.inventory.commons.core.Messages;
import seedu.inventory.commons.core.index.Index;
import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.model.Model;
import seedu.inventory.model.staff.Staff;

/**
 * Deletes a staff identified using its displayed index from the inventory.
 */
public class DeleteStaffCommand extends Command {

    public static final String COMMAND_WORD = "delete_staff";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the item identified by the index number used in the display item list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_STAFF_SUCCESS = "Deleted Staff: %1$s";

    private final Index targetIndex;

    public DeleteStaffCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Staff> lastShownList = model.getFilteredStaffList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STAFF_DISPLAYED_INDEX);
        }

        Staff staffToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteStaff(staffToDelete);
        model.commitInventory();
        return new CommandResult(String.format(MESSAGE_DELETE_STAFF_SUCCESS, staffToDelete));
    }
}
