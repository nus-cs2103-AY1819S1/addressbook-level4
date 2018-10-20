package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalGroups.GROUP_2101;
import static seedu.address.testutil.TypicalGroups.PROJECT_2103T;
import static seedu.address.testutil.TypicalMeetings.DISCUSSION;

import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.group.Group;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Person;
import seedu.address.model.shared.Title;
import seedu.address.testutil.ModelStub;


// @@author NyxF4ll
public class CancelCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    private Title groupTitle1 = new Title("Group1");
    private Title groupTitle2 = new Title("Group2");

    private Group group1 = new Group(groupTitle1);
    private Group group2 = new Group(groupTitle2);

    @Test
    public void execute_validGroupSpecified_success() {
        Model model = new ModelStubWithSingleGroup(group1);
        ((ModelStubWithSingleGroup) model).setMeeting(DISCUSSION);

        Group expectedGroup = new Group(groupTitle1);
        Model expectedModel = new ModelStubWithSingleGroup(expectedGroup);

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

    private class ModelStubWithSingleGroup extends ModelStub {

        private Group group;

        ModelStubWithSingleGroup(Group group) {
            this.group = group;
        }

        public void setMeeting(Meeting meeting) {
            group.setMeeting(meeting);
        }

        public void cancelMeeting() {
            group.cancelMeeting();
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            // This method is called by AssertCommandFailure
            return FXCollections.observableArrayList();
        }

        @Override
        public boolean hasGroup(Group group) {
            return group.isSameGroup(this.group);
        }

        @Override
        public ObservableList<Group> getGroupList() {
            return FXCollections.observableArrayList(group);
        }

        @Override
        public void updateGroup(Group target, Group editedGroup) {
            this.group = editedGroup;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public void commitAddressBook() {
            // called by {@code MeetCommand#execute()}
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }

            if (!(other instanceof ModelStubWithSingleGroup)) {
                return false;
            }

            return this.group.equals(((ModelStubWithSingleGroup) other).group);
        }
    }
}
