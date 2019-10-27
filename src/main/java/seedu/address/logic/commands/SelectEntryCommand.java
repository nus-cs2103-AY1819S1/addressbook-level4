package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToEntryListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entry.ResumeEntry;

/**
 * displays a selected entry in ExpandedEntryPanel
 */
public class SelectEntryCommand extends Command {
    public static final String COMMAND_WORD = "selectEntry";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the entry identified by the index number used in the displayed entry list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_ENTRY_SUCCESS = "Selected Entry: %1$s";

    private final Index targetIndex;

    public SelectEntryCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<ResumeEntry> filteredEntryList = model.getFilteredEntryList();

        if (targetIndex.getZeroBased() >= filteredEntryList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToEntryListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_ENTRY_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectEntryCommand // instanceof handles nulls
                && targetIndex.equals(((SelectEntryCommand) other).targetIndex)); // state check
    }
}
