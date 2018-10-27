package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_GROUP_NOT_FOUND;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;

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

    public static final String MESSAGE_CANCEL_COMMAND_SUCCESS = "Meeting for group %1$s cancelled";
    public static final String MESSAGE_GROUP_HAS_NO_MEETING = "Cancel command can only be called on a group "
            + "with meeting.";

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

        // This must be changed after find group command is implemented.
        List<Group> groupList = model.getGroupList();

        if (!model.hasGroup(group)) {
            throw new CommandException(MESSAGE_GROUP_NOT_FOUND);
        }

        Group groupToEdit = groupList.stream().filter(group::isSameGroup).findFirst().get();
        if (!groupToEdit.hasMeeting()) {
            throw new CommandException(MESSAGE_GROUP_HAS_NO_MEETING);
        }

        Group editedGroup = groupToEdit.copy();

        editedGroup.cancelMeeting();

        model.updateGroup(groupToEdit, editedGroup);

        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_CANCEL_COMMAND_SUCCESS, groupToEdit.getTitle()));
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
