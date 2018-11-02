package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.group.Group;
import seedu.address.testutil.GroupBuilder;
import seedu.address.testutil.ModelStub;



public class AddGroupCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddGroupCommand(null);
    }

    @Test
    public void execute_groupAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingGroupAdded modelStub = new ModelStubAcceptingGroupAdded();
        Group validGroup = new GroupBuilder().build();

        CommandResult commandResult = new AddGroupCommand(validGroup).execute(modelStub, commandHistory);

        assertEquals(String.format(AddGroupCommand.MESSAGE_SUCCESS, validGroup), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validGroup), modelStub.groupsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateGroup_throwsCommandException() throws Exception {
        Group validGroup = new GroupBuilder().build();
        AddGroupCommand addGroupCommand = new AddGroupCommand(validGroup);
        ModelStub modelStub = new ModelStubWithGroup(validGroup);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddGroupCommand.MESSAGE_DUPLICATE_GROUP);
        addGroupCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Group basic = new GroupBuilder().withTitle("CS1010").build();
        Group intermediate = new GroupBuilder().withTitle("CS2106").build();
        AddGroupCommand addBasicCommand = new AddGroupCommand(basic);
        AddGroupCommand addInterCommand = new AddGroupCommand(intermediate);

        // same object -> returns true
        assertTrue(addBasicCommand.equals(addBasicCommand));

        // same values -> returns true
        AddGroupCommand addBasicCommandCopy = new AddGroupCommand(basic);
        assertTrue(addBasicCommand.equals(addBasicCommandCopy));

        // different types -> returns false
        assertFalse(addBasicCommand.equals(2));

        // null -> returns false
        assertFalse(addBasicCommand.equals(null));

        // different person -> returns false
        assertFalse(addBasicCommand.equals(addInterCommand));
    }


    /**
     * A Model stub that contains a single group.
     */
    private class ModelStubWithGroup extends ModelStub {
        private final Group group;

        ModelStubWithGroup(Group group) {
            requireNonNull(group);
            this.group = group;
        }

        @Override
        public boolean hasGroup(Group group) {
            requireNonNull(group);
            return this.group.isSameGroup(group);
        }
    }

    /**
     * A Model stub that always accept the group being added.
     */
    private class ModelStubAcceptingGroupAdded extends ModelStub {
        final ArrayList<Group> groupsAdded = new ArrayList<>();

        @Override
        public boolean hasGroup(Group group) {
            requireNonNull(group);
            return groupsAdded.stream().anyMatch(group::isSameGroup);
        }

        @Override
        public void addGroup(Group group) {
            requireNonNull(group);
            groupsAdded.add(group);
        }

        @Override
        public void commitAddressBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
