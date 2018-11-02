package seedu.meeting.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.meeting.commons.core.Messages.MESSAGE_GROUP_NOT_FOUND;

import seedu.meeting.logic.CommandHistory;
import seedu.meeting.logic.commands.exceptions.CommandException;
import seedu.meeting.model.Model;
import seedu.meeting.model.group.Group;
import seedu.meeting.model.group.exceptions.GroupHasNoMeetingException;
import seedu.meeting.model.group.exceptions.GroupNotFoundException;

// @@author NyxF4ll
/**
 * Cancel the meeting associated with a group.
 */
public class CancelCommand extends Command {

    public static final String COMMAND_WORD = "cancel";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " [GROUP_NAME] : Cancel the meeting associated with the "
            + "group identified by its name. If the group does not exists or does not have a meeting associated with "
            + "it, the command will return an error.\n"
            + "[GROUP_NAME] (must be a alphanumeric string with out spaces)\n"

            + "Example: " + COMMAND_WORD + " CS2103 ";

    public static final String MESSAGE_CANCEL_COMMAND_SUCCESS = "Meeting for group %1$s cancelled.";
    public static final String MESSAGE_GROUP_HAS_NO_MEETING = "Error: Group does not have a meeting to be cancelled.";

    public final Group group;

    /**
     * Constructor for the MeetCommand Class.
     * @param group The group to cancel the meeting.
     */
    public CancelCommand(Group group) {
        requireNonNull(group);
        this.group = group;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        try {
            model.cancelMeeting(group);
            model.commitMeetingBook();
            return new CommandResult(String.format(MESSAGE_CANCEL_COMMAND_SUCCESS, group.getTitle()));
        } catch (GroupNotFoundException gnfe) {
            throw new CommandException(MESSAGE_GROUP_NOT_FOUND);
        } catch (GroupHasNoMeetingException gnme) {
            throw new CommandException(MESSAGE_GROUP_HAS_NO_MEETING);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CancelCommand)) {
            return false;
        }

        // state check
        CancelCommand e = (CancelCommand) other;
        return this.group.isSameGroup(e.group);
    }
}
