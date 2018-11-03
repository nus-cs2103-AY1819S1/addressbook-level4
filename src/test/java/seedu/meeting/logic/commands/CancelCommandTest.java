package seedu.meeting.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.meeting.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.meeting.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.meeting.testutil.TypicalGroups.GROUP_2101;
import static seedu.meeting.testutil.TypicalGroups.PROJECT_2103T;
import static seedu.meeting.testutil.TypicalMeetings.DISCUSSION;

import org.junit.Test;

import seedu.meeting.commons.core.Messages;
import seedu.meeting.logic.CommandHistory;
import seedu.meeting.model.Model;
import seedu.meeting.model.group.Group;
import seedu.meeting.model.shared.Title;


// @@author NyxF4ll
public class CancelCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    private Title groupTitle1 = new Title("Group1");
    private Title groupTitle2 = new Title("Group2");

    private Group group1 = new Group(groupTitle1);
    private Group group2 = new Group(groupTitle2);

    @Test
    public void execute_validGroupSpecified_success() {
        Group groupWithMeeting = new Group(groupTitle1);
        groupWithMeeting.setMeeting(DISCUSSION);
        Model model = new ModelStubWithSingleGroup(groupWithMeeting);

        Model expectedModel = new ModelStubWithSingleGroup(group1);

        CancelCommand command = new CancelCommand(group1);
        assertCommandSuccess(command, model, EMPTY_COMMAND_HISTORY,
                String.format(CancelCommand.MESSAGE_CANCEL_COMMAND_SUCCESS, groupTitle1.toString()), expectedModel);
    }

    @Test
    public void execute_invalidGroup_failure() {
        Model model = new ModelStubWithSingleGroup(group1);

        CancelCommand command = new CancelCommand(group2);
        assertCommandFailure(command, model, EMPTY_COMMAND_HISTORY, Messages.MESSAGE_GROUP_NOT_FOUND);
    }

    @Test
    public void execute_groupHasNoMeeting_failure() {
        Model model = new ModelStubWithSingleGroup(group1);

        CancelCommand command = new CancelCommand(group1);
        assertCommandFailure(command, model, EMPTY_COMMAND_HISTORY, CancelCommand.MESSAGE_GROUP_HAS_NO_MEETING);
    }


    @Test
    public void equals() {
        final CancelCommand standardCommand = new CancelCommand(PROJECT_2103T);

        // same values -> returns true
        CancelCommand commandWithSameValues = new CancelCommand(PROJECT_2103T);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different group -> returns false
        assertFalse(standardCommand.equals(new CancelCommand(GROUP_2101)));
    }
    // @@author
}
