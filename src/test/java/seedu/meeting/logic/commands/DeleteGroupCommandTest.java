package seedu.meeting.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.meeting.commons.core.Messages.MESSAGE_GROUP_NOT_FOUND;
import static seedu.meeting.testutil.TypicalGroups.PROJECT_2103T;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.meeting.logic.CommandHistory;
import seedu.meeting.logic.commands.exceptions.CommandException;
import seedu.meeting.model.MeetingBook;
import seedu.meeting.model.ReadOnlyMeetingBook;
import seedu.meeting.model.group.Group;
import seedu.meeting.model.shared.Title;
import seedu.meeting.testutil.GroupBuilder;
import seedu.meeting.testutil.ModelStub;
import seedu.meeting.testutil.TypicalGroups;



// @@author Derek-Hardy
public class DeleteGroupCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new DeleteGroupCommand(null);
    }

    @Test
    public void execute_groupAcceptedByModel_removeSuccessful() throws Exception {
        ModelStubAcceptingGroupRemoved modelStub = new ModelStubAcceptingGroupRemoved();
        Group validGroup = PROJECT_2103T.copy();

        CommandResult commandResult = new DeleteGroupCommand(validGroup).execute(modelStub, commandHistory);

        assertEquals(String.format(DeleteGroupCommand.MESSAGE_DELETE_GROUP_SUCCESS, validGroup),
                commandResult.feedbackToUser);

        List<Group> standardGroups = new ArrayList<>(TypicalGroups.getTypicalGroups());
        standardGroups.remove(validGroup);

        assertEquals(modelStub.groupsRemoved, standardGroups);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_groupNotFound_throwsCommandException() throws Exception {
        Group invalidGroup = new GroupBuilder().withTitle("Mystery").build();
        DeleteGroupCommand deleteGroupCommand = new DeleteGroupCommand(invalidGroup);
        ModelStubAcceptingGroupRemoved modelStub = new ModelStubAcceptingGroupRemoved();

        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_GROUP_NOT_FOUND);
        deleteGroupCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Group network = new GroupBuilder().withTitle("CS2105").build();
        Group software = new GroupBuilder().withTitle("CS2103").build();
        DeleteGroupCommand deleteNetworkCommand = new DeleteGroupCommand(network);
        DeleteGroupCommand deleteSoftwareCommand = new DeleteGroupCommand(software);

        // same object -> returns true
        assertTrue(deleteNetworkCommand.equals(deleteNetworkCommand));

        // same values -> returns true
        DeleteGroupCommand deleteNetworkCommandCopy = new DeleteGroupCommand(network);
        assertTrue(deleteNetworkCommand.equals(deleteNetworkCommandCopy));

        // different types -> returns false
        assertFalse(deleteNetworkCommand.equals(2));

        // null -> returns false
        assertFalse(deleteNetworkCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteNetworkCommand.equals(deleteSoftwareCommand));
    }


    /**
     * A Model stub that always accept the group being removed.
     */
    private class ModelStubAcceptingGroupRemoved extends ModelStub {
        private List<Group> groupsRemoved = new ArrayList<>(TypicalGroups.getTypicalGroups());

        @Override
        public void removeGroup(Group group) {
            requireNonNull(group);
            groupsRemoved.remove(group);
        }

        @Override
        public Group getGroupByTitle(Title title) {
            requireNonNull(title);
            for (Group group : groupsRemoved) {
                Title groupTitle = group.getTitle();
                if (groupTitle.equals(title)) {
                    return group.copy();
                }
            }

            return null;
        }

        @Override
        public void commitMeetingBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyMeetingBook getMeetingBook() {
            return new MeetingBook();
        }
    }
}
