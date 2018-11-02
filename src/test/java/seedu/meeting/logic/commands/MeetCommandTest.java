package seedu.meeting.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.meeting.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.meeting.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.meeting.logic.commands.MeetCommand.MESSAGE_GROUP_HAS_NO_MEETING;
import static seedu.meeting.logic.commands.MeetCommand.MESSAGE_MEETING_CANCELLED;
import static seedu.meeting.testutil.TypicalGroups.GROUP_2101;
import static seedu.meeting.testutil.TypicalGroups.PROJECT_2103T;
import static seedu.meeting.testutil.TypicalMeetings.DISCUSSION;
import static seedu.meeting.testutil.TypicalMeetings.REHEARSAL;

import org.junit.Test;

import seedu.meeting.commons.core.Messages;
import seedu.meeting.logic.CommandHistory;
import seedu.meeting.model.Model;
import seedu.meeting.model.group.Group;
import seedu.meeting.model.shared.Title;

// @@author NyxF4ll
public class MeetCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    private Title groupTitle1 = new Title("Group1");
    private Title groupTitle2 = new Title("Group2");

    private Group group1 = new Group(groupTitle1);
    private Group group2 = new Group(groupTitle2);

    @Test
    public void execute_meetingSpecified_success() {
        Model model = new ModelStubWithSingleGroup(group1);

        Group expectedGroup = new Group(groupTitle1);
        expectedGroup.setMeeting(DISCUSSION);
        Model expectedModel = new ModelStubWithSingleGroup(expectedGroup);

        MeetCommand command = new MeetCommand(group1, DISCUSSION);
        assertCommandSuccess(command, model, EMPTY_COMMAND_HISTORY,
                String.format(MeetCommand.MESSAGE_MEET_COMMAND_SUCCESS, groupTitle1.toString(),
                        DISCUSSION.getTitle().toString()),
                expectedModel);
    }

    @Test
    public void execute_invalidGroup_failure() {
        Model model = new ModelStubWithSingleGroup(group1);

        MeetCommand command = new MeetCommand(group2, DISCUSSION);
        assertCommandFailure(command, model, EMPTY_COMMAND_HISTORY, Messages.MESSAGE_GROUP_NOT_FOUND);

        command = new MeetCommand(group2, null);
        assertCommandFailure(command, model, EMPTY_COMMAND_HISTORY, Messages.MESSAGE_GROUP_NOT_FOUND);
    }

    @Test
    public void execute_noMeetingSpecified_meetingCleared() {
        Group editedGroup1 = new Group(groupTitle1);
        editedGroup1.setMeeting(REHEARSAL);
        Model model = new ModelStubWithSingleGroup(editedGroup1);

        Model expectedModel = new ModelStubWithSingleGroup(group1);

        MeetCommand command = new MeetCommand(group1, null);
        assertCommandSuccess(command, model, EMPTY_COMMAND_HISTORY,
                String.format(MESSAGE_MEETING_CANCELLED, groupTitle1), expectedModel);
    }

    @Test
    public void execute_noMeetingSpecifiedGroupHasNoMeeting_failure() {
        Model model = new ModelStubWithSingleGroup(group1);

        MeetCommand command = new MeetCommand(group1, null);
        assertCommandFailure(command, model, EMPTY_COMMAND_HISTORY, MESSAGE_GROUP_HAS_NO_MEETING);
    }

    @Test
    public void equals() {
        final MeetCommand standardCommand = new MeetCommand(PROJECT_2103T, DISCUSSION);

        // same values -> returns true
        MeetCommand commandWithSameValues = new MeetCommand(PROJECT_2103T, DISCUSSION);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different group -> returns false
        assertFalse(standardCommand.equals(new MeetCommand(GROUP_2101, DISCUSSION)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new MeetCommand(PROJECT_2103T, REHEARSAL)));
    }
}
