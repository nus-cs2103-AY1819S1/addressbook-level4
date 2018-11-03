package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalArchiveList;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.User;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.SelfEditCommandParserBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class SelfEditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalArchiveList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    public void setUpAsAdmin() {
        model.setLoggedInUser(User.getAdminUser());
    }

    public void setUpAsUser(int index) {
        model.setLoggedInUser(new User(model.getAddressBook().getPersonList().get(index)));
    }

    @Test
    public void execute_allFieldsSpecified_success() {
        setUpAsUser(0);

        Person editedPerson = new PersonBuilder().build();
        SelfEditCommand selfEditCommand = null;
        try {
            SelfEditCommandParserBuilder descriptor = new SelfEditCommandParserBuilder()
                .withPhone(editedPerson.getPhone().value)
                .withAddress(editedPerson.getAddress().value)
                .withEmail(editedPerson.getEmail().value)
                .withProjects(editedPerson.getProjects());
            selfEditCommand = descriptor.getCommand();
        } catch (ParseException pe) {
            throw new AssertionError("Failed to build appropiate SelfEditCommand! ", pe);
        }

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            model.getArchiveList(), new UserPrefs());
        Person originalPerson = model.getFilteredPersonList().get(0);
        editedPerson = new PersonBuilder(originalPerson).withPhone(editedPerson.getPhone().value)
            .withAddress(editedPerson.getAddress().value)
            .withEmail(editedPerson.getEmail().value).withProjects(editedPerson.getProjects()).build();

        String expectedMessage = String.format(SelfEditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(selfEditCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        setUpAsUser(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
            .build();

        SelfEditCommand selfEditCommand = null;
        try {
            SelfEditCommandParserBuilder selfEditCommandParserBuilder = new SelfEditCommandParserBuilder()
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB);
            selfEditCommand = selfEditCommandParserBuilder.getCommand();
        } catch(ParseException pe) {
            throw new AssertionError("Failed to build appropiate SelfEditCommand! ", pe);
        }
        String expectedMessage = String.format(SelfEditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            model.getArchiveList(), new UserPrefs());
        expectedModel.updatePerson(lastPerson, editedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(selfEditCommand, model, commandHistory, expectedMessage, expectedModel);
    }


    @Test
    public void execute_admin_failure() {
        setUpAsAdmin();

        SelfEditCommand selfEditCommand = null;
        try {
            SelfEditCommandParserBuilder selfEditCommandParserBuilder = new SelfEditCommandParserBuilder()
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB);
            selfEditCommand = selfEditCommandParserBuilder.getCommand();
        } catch(ParseException pe) {
            throw new AssertionError("Failed to build appropiate SelfEditCommand! ", pe);
        }
        String expectedMessage = SelfEditCommand.ADMIN_EDIT_ERROR;

        assertCommandFailure(selfEditCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void executeUndoRedo_success() throws Exception {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        setUpAsUser(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
            .build();

        SelfEditCommand selfEditCommand = null;
        try {
            SelfEditCommandParserBuilder selfEditCommandParserBuilder = new SelfEditCommandParserBuilder()
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB);
            selfEditCommand = selfEditCommandParserBuilder.getCommand();
        } catch(ParseException pe) {
            throw new AssertionError("Failed to build appropiate SelfEditCommand! ", pe);
        }

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            model.getArchiveList(), new UserPrefs());
        expectedModel.updatePerson(lastPerson, editedPerson);
        expectedModel.commitAddressBook();

        // edit -> modify person data
        selfEditCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_admin_failure() {
        execute_admin_failure();

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Person} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);

        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder().withUsername(personToEdit.getUsername().username)
            .withPassword(personToEdit.getPassword().password)
            .withLeaveApplications(personToEdit.getLeaveApplications())
            .build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
            model.getArchiveList(), new UserPrefs());

        expectedModel.updatePerson(personToEdit, editedPerson);
        expectedModel.commitAddressBook();

        // edit -> edits second person in unfiltered person list / first person in filtered person list
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), personToEdit);
        // redo -> edits same second person in unfiltered person list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

}
