package seedu.meeting.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.meeting.commons.core.Messages.MESSAGE_GROUP_NOT_FOUND;
import static seedu.meeting.commons.core.Messages.MESSAGE_PERSON_NOT_FOUND;
import static seedu.meeting.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.meeting.testutil.TypicalGroups.GROUP_2101;
import static seedu.meeting.testutil.TypicalPersons.ALICE;
import static seedu.meeting.testutil.TypicalPersons.BENSON;

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
import seedu.meeting.model.person.Name;
import seedu.meeting.model.person.Person;
import seedu.meeting.model.shared.Title;
import seedu.meeting.testutil.GroupBuilder;
import seedu.meeting.testutil.ModelStub;
import seedu.meeting.testutil.PersonBuilder;
import seedu.meeting.testutil.TypicalGroups;
import seedu.meeting.testutil.TypicalPersons;



public class LeaveCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullGroup_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        Person person = new PersonBuilder().build();
        new LeaveCommand(person, null);
    }

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        Group group = new GroupBuilder().build();
        new LeaveCommand(null, group);
    }

    @Test
    public void execute_acceptedByModel_leaveSuccessful() throws Exception {
        ModelStubAcceptingPersonLeft modelStub = new ModelStubAcceptingPersonLeft();
        Group validGroup = GROUP_2101.copy();
        Person validPerson = BENSON.copy();

        validGroup.addMember(validPerson);

        modelStub.updatePerson(BENSON, validPerson);
        modelStub.updateGroup(GROUP_2101, validGroup);

        CommandResult commandResult = new LeaveCommand(validPerson, validGroup).execute(modelStub, commandHistory);

        assertEquals(String.format(LeaveCommand.MESSAGE_LEAVE_SUCCESS, validPerson.getName().fullName,
            validGroup.getTitle().fullTitle),
                commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_groupNotFound_throwsCommandException() throws Exception {
        Person validPerson = ALICE.copy();
        Group invalidGroup = new GroupBuilder().withTitle("Mystery").build();

        LeaveCommand leaveCommand = new LeaveCommand(validPerson, invalidGroup);
        ModelStubAcceptingPersonLeft modelStub = new ModelStubAcceptingPersonLeft();

        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_GROUP_NOT_FOUND);
        leaveCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_personNotFound_throwsCommandException() throws Exception {
        Person invalidPerson = new PersonBuilder().withName("Derek").build();
        Group validGroup = GROUP_2101.copy();

        LeaveCommand leaveCommand = new LeaveCommand(invalidPerson, validGroup);
        ModelStubAcceptingPersonLeft modelStub = new ModelStubAcceptingPersonLeft();

        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_PERSON_NOT_FOUND);
        leaveCommand.execute(modelStub, commandHistory);
    }


    @Test
    public void equals() {
        Group network = new GroupBuilder().withTitle("CS2105").build();
        Person hardy = new PersonBuilder().withName("hardy").build();
        Group software = new GroupBuilder().withTitle("CS2103").build();
        Person derek = new PersonBuilder().withName("derek").build();

        LeaveCommand leaveCommand1 = new LeaveCommand(hardy, network);
        LeaveCommand leaveCommand2 = new LeaveCommand(derek, software);

        // same object -> returns true
        assertTrue(leaveCommand1.equals(leaveCommand1));

        // same values -> returns true
        LeaveCommand leaveCommand1Copy = new LeaveCommand(hardy, network);
        assertTrue(leaveCommand1Copy.equals(leaveCommand1));

        // different types -> returns false
        assertFalse(leaveCommand1.equals(2));

        // null -> returns false
        assertFalse(leaveCommand1.equals(null));

        // different person -> returns false
        assertFalse(leaveCommand1.equals(leaveCommand2));
    }


    /**
     * A Model stub that always accept the group being removed.
     */
    private class ModelStubAcceptingPersonLeft extends ModelStub {
        private List<Group> groupsJoined = new ArrayList<>(TypicalGroups.getTypicalGroups());
        private List<Person> personList = new ArrayList<>(TypicalPersons.getTypicalPersons());

        @Override
        public void updatePerson(Person target, Person editedPerson) {
            requireAllNonNull(target, editedPerson);

            personList.remove(target);
            personList.add(editedPerson);
        }

        @Override
        public void updateGroup(Group target, Group editedGroup) {
            requireAllNonNull(target, editedGroup);

            groupsJoined.remove(target);
            groupsJoined.add(editedGroup);
        }

        @Override
        public void leaveGroup(Person person, Group group) {
            requireAllNonNull(person, group);
            Person personCopy = person.copy();
            Group groupCopy = group.copy();

            group.removeMember(person);

            updatePerson(personCopy, person);
            updateGroup(groupCopy, group);
        }

        @Override
        public Group getGroupByTitle(Title title) {
            requireNonNull(title);
            for (Group group : groupsJoined) {
                Title groupTitle = group.getTitle();
                if (groupTitle.equals(title)) {
                    return group.copy();
                }
            }
            return null;
        }

        @Override
        public Person getPersonByName(Name name) {
            requireNonNull(name);
            for (Person person : personList) {
                Name personName = person.getName();
                if (personName.equals(name)) {
                    return person.copy();
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
