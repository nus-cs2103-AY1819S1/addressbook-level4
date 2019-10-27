package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToEntryListRequestEvent;
import seedu.address.commons.events.ui.UpdateExpandedEntryRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entry.ResumeEntry;

/**
 * Adds a bullet description to a particular entry.
 */
public class AddBulletCommand extends Command {
    public static final String COMMAND_WORD = "addBullet";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a bullet description identified "
            + "by the index number used in the displayed entry list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "CONTENT_TO_ADD\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "attained Best Financial Hack Award";

    public static final String MESSAGE_ADDBULLET_SUCCESS = "Added Bullet : %1$s";
    public static final String MESSAGE_ADDBULLET_DUPLICATE_BULLET = "This bullet already exists";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final String bullet;

    /**
     * @param index  of the entry in the filtered entry list to edit
     * @param bullet details to edit the person with
     */
    public AddBulletCommand(Index index, String bullet) {
        requireNonNull(index);
        requireNonNull(bullet);

        this.index = index;
        this.bullet = bullet;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<ResumeEntry> lastShownList = model.getFilteredEntryList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
        }

        ResumeEntry entryToEdit = lastShownList.get(index.getZeroBased());

        if (entryToEdit.getDescription().contains(bullet)) {
            throw new CommandException(MESSAGE_ADDBULLET_DUPLICATE_BULLET);
        }

        ResumeEntry editedEntry = createEntryWithAddedBullet(entryToEdit, bullet);

        model.updateEntry(entryToEdit, editedEntry);
        model.commitEntryBook();

        postAddBulletCommandEvents(index, editedEntry);

        return new CommandResult(String.format(MESSAGE_ADDBULLET_SUCCESS, bullet));
    }

    /**
     * raise events to UI when a bullet is successfully added to an entry.
     */
    private void postAddBulletCommandEvents(Index index, ResumeEntry editedEntry) {
        EventsCenter.getInstance().post(new JumpToEntryListRequestEvent(index));
        EventsCenter.getInstance().post(new UpdateExpandedEntryRequestEvent(editedEntry));
    }

    /**
     * Creates and returns a {@code entry} with added bullet.
     */
    private static ResumeEntry createEntryWithAddedBullet(ResumeEntry entryToEdit, String bulletToAdd) {
        assert entryToEdit != null;
        return entryToEdit.getEntryWithAddedBullet(bulletToAdd);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddBulletCommand)) {
            return false;
        }

        // state check
        AddBulletCommand e = (AddBulletCommand) other;
        return index.equals(e.index)
                && bullet.equals(e.bullet);
    }
}
