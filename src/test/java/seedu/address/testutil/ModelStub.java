package seedu.address.testutil;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.group.Group;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.util.PersonPropertyComparator;
import seedu.address.model.shared.Title;



/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void addPerson(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasPerson(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deletePerson(Person target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updatePerson(Person target, Person editedPerson) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateGroup(Group target, Group editedGroup) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addGroup(Group group) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void removeGroup(Group group) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void joinGroup(Person person, Group group) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void leaveGroup(Person person, Group group) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasGroup(Group group) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Group> getGroupList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Group getGroupByTitle(Title title) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Person getPersonByName(Name name) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setMeeting(Group group, Meeting meeting) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void cancelMeeting(Group group) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Group> getFilteredGroupList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredGroupList(Predicate<Group> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Person> getSortedPersonList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateSortedPersonList(PersonPropertyComparator personPropertyComparator) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Meeting> getFilteredMeetingList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredMeetingList(Predicate<Meeting> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean canUndoAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean canRedoAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void undoAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void redoAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void commitAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void exportAddressBook(Path filepath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void importAddressBook(ReadOnlyAddressBook importAddressBook, boolean overwrite) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void changeUserPrefs(Path filepath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getAddressBookFilePath() {
        throw new AssertionError("This method should not be called.");
    }
}
