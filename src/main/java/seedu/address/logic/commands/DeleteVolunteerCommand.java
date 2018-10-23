package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.volunteer.Volunteer;

/**
 * Deletes a volunteer identified using it's displayed index from the address book.
 */
public class DeleteVolunteerCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the volunteer identified by the index number used in the displayed volunteer list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_VOLUNTEER_SUCCESS = "Deleted Volunteer: %1$s";

    private final Index targetIndex;

    public DeleteVolunteerCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Volunteer> lastShownList = model.getFilteredVolunteerList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);
        }

        Volunteer volunteerToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteVolunteer(volunteerToDelete);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_VOLUNTEER_SUCCESS, volunteerToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteVolunteerCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteVolunteerCommand) other).targetIndex)); // state check
    }
}
