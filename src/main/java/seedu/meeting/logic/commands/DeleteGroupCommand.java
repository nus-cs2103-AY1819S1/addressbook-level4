package seedu.meeting.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.meeting.commons.core.Messages.MESSAGE_GROUP_NOT_FOUND;

import seedu.meeting.logic.CommandHistory;
import seedu.meeting.logic.commands.exceptions.CommandException;
import seedu.meeting.model.Model;
import seedu.meeting.model.group.Group;
import seedu.meeting.model.shared.Title;


// @@author Derek-Hardy
/**
 * Deletes a group identified using it's displayed title from the MeetingBook.
 */
public class DeleteGroupCommand extends Command {

    public static final String COMMAND_WORD = "deleteGroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the group identified by the title used in the displayed group list.\n"
            + "Parameters: title (must be a valid title)\n"
            + "Example: " + COMMAND_WORD + " CS2103T";

    public static final String MESSAGE_DELETE_GROUP_SUCCESS = "Deleted Group: %1$s";

    private final Title groupName;
    private Group matchedGroupByName;

    /**
     * Creates an DeleteGroupCommand to delete the specified {@code Group}
     */
    public DeleteGroupCommand(Group toDelete) {
        requireNonNull(toDelete);
        this.groupName = toDelete.getTitle();
        this.matchedGroupByName = null;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        matchedGroupByName = model.getGroupByTitle(groupName);

        if (matchedGroupByName == null) {
            throw new CommandException(MESSAGE_GROUP_NOT_FOUND);
        }

        model.removeGroup(matchedGroupByName);
        model.commitMeetingBook();
        return new CommandResult(String.format(MESSAGE_DELETE_GROUP_SUCCESS, matchedGroupByName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteGroupCommand // instanceof handles nulls
                && groupName.equals(((DeleteGroupCommand) other).groupName)); // state check
    }
}
