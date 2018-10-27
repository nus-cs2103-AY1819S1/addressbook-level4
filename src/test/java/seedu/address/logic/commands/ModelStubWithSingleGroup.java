package seedu.address.logic.commands;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.group.Group;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Person;
import seedu.address.testutil.ModelStub;

// @@author NyxF4ll
/**
 * Model stub with only one group, used to test meet and cancel commands.
 */
public class ModelStubWithSingleGroup extends ModelStub {

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
    public void updateGroup(Group target, Group editedGroup) throws GroupNotFoundException {
        if (!target.isSameGroup(this.group)) {
            throw new GroupNotFoundException();
        }
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
