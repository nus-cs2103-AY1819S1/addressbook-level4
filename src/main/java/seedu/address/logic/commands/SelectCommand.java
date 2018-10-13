package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToGroupListRequestEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Selects a person identified using it's displayed index from the address book.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Selected Person: %1$s";
    public static final String MESSAGE_SELECT_GROUP_SUCCESS = "Selected Group: %1$s";

    public static final int SELECT_TYPE_GROUP = 0;
    public static final int SELECT_TYPE_PERSON = 1;

    private final Index targetIndex;
    private final int selectType;

    public SelectCommand(Index targetIndex, int selectType) {
        this.targetIndex = targetIndex;
        this.selectType = selectType;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (selectType == SELECT_TYPE_GROUP) {
            List<Tag> filteredGroupList = model.getFilteredGroupList();

            if (targetIndex.getZeroBased() >= filteredGroupList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
            }

            EventsCenter.getInstance().post(new JumpToGroupListRequestEvent(targetIndex));
            return new CommandResult(String.format(MESSAGE_SELECT_GROUP_SUCCESS, targetIndex.getOneBased()));
        } else {
            List<Person> filteredPersonList = model.getFilteredPersonList();

            if (targetIndex.getZeroBased() >= filteredPersonList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
            return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, targetIndex.getOneBased()));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}
