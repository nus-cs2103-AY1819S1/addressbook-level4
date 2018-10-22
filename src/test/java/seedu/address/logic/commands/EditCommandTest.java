package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DESC_VOLUNTEER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_VOLUNTEER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertVolunteerCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showVolunteerAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalVolunteers.getTypicalVolunteerAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.EditVolunteerCommand.EditVolunteerDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EditVolunteerDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.VolunteerBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model modelVolunteer = new ModelManager(getTypicalVolunteerAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(lastPerson, editedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditPersonDescriptor());
        Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in address book
        Person personInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder(personInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Person editedPerson = new PersonBuilder().build();
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(personToEdit, editedPerson);
        expectedModel.commitAddressBook();

        // edit -> first person edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

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
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
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
    public void execute_allFieldsSpecifiedUnfilteredVolunteerList_success() {
        Volunteer editedVolunteer = new VolunteerBuilder().build();
        EditVolunteerDescriptor descriptor = new EditVolunteerDescriptorBuilder(editedVolunteer).build();
        EditVolunteerCommand editVolunteerCommand = new EditVolunteerCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditVolunteerCommand.MESSAGE_EDIT_VOLUNTEER_SUCCESS, editedVolunteer);

        Model expectedModel = new ModelManager(new AddressBook(modelVolunteer.getAddressBook()), new UserPrefs());
        expectedModel.updateVolunteer(modelVolunteer.getFilteredVolunteerList().get(0), editedVolunteer);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editVolunteerCommand, modelVolunteer, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredVolunteerList_success() {
        Index indexLastVolunteer = Index.fromOneBased(model.getFilteredVolunteerList().size());
        Volunteer lastVolunteer = modelVolunteer.getFilteredVolunteerList().get(indexLastVolunteer.getZeroBased());

        VolunteerBuilder volunteerInList = new VolunteerBuilder(lastVolunteer);
        Volunteer editedVolunteer = volunteerInList.withName(VALID_VOLUNTEER_NAME_BOB)
                .withPhone(VALID_VOLUNTEER_PHONE_BOB).withTags(VALID_VOLUNTEER_TAG_HUSBAND).build();

        EditVolunteerDescriptor descriptor = new EditVolunteerDescriptorBuilder().withName(VALID_VOLUNTEER_NAME_BOB)
                .withPhone(VALID_VOLUNTEER_PHONE_BOB).withTags(VALID_VOLUNTEER_TAG_HUSBAND).build();
        EditVolunteerCommand editVolunteerCommand = new EditVolunteerCommand(indexLastVolunteer, descriptor);

        String expectedMessage = String.format(EditVolunteerCommand.MESSAGE_EDIT_VOLUNTEER_SUCCESS, editedVolunteer);

        Model expectedModel = new ModelManager(new AddressBook(modelVolunteer.getAddressBook()), new UserPrefs());
        expectedModel.updateVolunteer(lastVolunteer, editedVolunteer);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editVolunteerCommand, modelVolunteer, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredVolunteerList_success() {
        EditVolunteerCommand editVolunteerCommand = new EditVolunteerCommand(INDEX_FIRST_PERSON,
                new EditVolunteerDescriptor());
        Volunteer editedVolunteer = modelVolunteer.getFilteredVolunteerList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditVolunteerCommand.MESSAGE_EDIT_VOLUNTEER_SUCCESS, editedVolunteer);

        Model expectedModel = new ModelManager(new AddressBook(modelVolunteer.getAddressBook()), new UserPrefs());
        expectedModel.commitAddressBook();

        assertCommandSuccess(editVolunteerCommand, modelVolunteer, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredVolunteerList_success() {
        showVolunteerAtIndex(modelVolunteer, INDEX_FIRST_PERSON);

        Volunteer volunteerInFilteredList = modelVolunteer.getFilteredVolunteerList()
                .get(INDEX_FIRST_PERSON.getZeroBased());
        Volunteer editedVolunteer = new VolunteerBuilder(volunteerInFilteredList)
                .withName(VALID_VOLUNTEER_NAME_BOB).build();
        EditVolunteerCommand editVolunteerCommand = new EditVolunteerCommand(INDEX_FIRST_PERSON,
                new EditVolunteerDescriptorBuilder().withName(VALID_VOLUNTEER_NAME_BOB).build());

        String expectedMessage = String.format(EditVolunteerCommand.MESSAGE_EDIT_VOLUNTEER_SUCCESS, editedVolunteer);

        Model expectedModel = new ModelManager(new AddressBook(modelVolunteer.getAddressBook()), new UserPrefs());
        expectedModel.updateVolunteer(modelVolunteer.getFilteredVolunteerList().get(0), editedVolunteer);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editVolunteerCommand, modelVolunteer, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateVolunteerUnfilteredList_failure() {
        Volunteer firstVolunteer = modelVolunteer.getFilteredVolunteerList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditVolunteerDescriptor descriptor = new EditVolunteerDescriptorBuilder(firstVolunteer).build();
        EditVolunteerCommand editVolunteerCommand = new EditVolunteerCommand(INDEX_SECOND_PERSON, descriptor);

        assertVolunteerCommandFailure(editVolunteerCommand, modelVolunteer, commandHistory,
                EditVolunteerCommand.MESSAGE_DUPLICATE_VOLUNTEER);
    }

    @Test
    public void execute_duplicateVolunteerFilteredList_failure() {
        showVolunteerAtIndex(modelVolunteer, INDEX_FIRST_PERSON);

        // edit volunteer in filtered list into a duplicate in address book
        Volunteer volunteerInList = modelVolunteer.getAddressBook().getVolunteerList()
                .get(INDEX_SECOND_PERSON.getZeroBased());
        EditVolunteerCommand editVolunteerCommand = new EditVolunteerCommand(INDEX_FIRST_PERSON,
                new EditVolunteerDescriptorBuilder(volunteerInList).build());

        assertVolunteerCommandFailure(editVolunteerCommand, modelVolunteer, commandHistory,
                EditVolunteerCommand.MESSAGE_DUPLICATE_VOLUNTEER);
    }

    @Test
    public void execute_invalidVolunteerIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(modelVolunteer.getFilteredVolunteerList().size() + 1);
        EditVolunteerDescriptor descriptor = new EditVolunteerDescriptorBuilder()
                .withName(VALID_VOLUNTEER_NAME_BOB).build();
        EditVolunteerCommand editVolunteerCommand = new EditVolunteerCommand(outOfBoundIndex, descriptor);

        assertVolunteerCommandFailure(editVolunteerCommand, modelVolunteer, commandHistory,
                Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidVolunteerIndexFilteredList_failure() {
        showVolunteerAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < modelVolunteer.getAddressBook().getVolunteerList().size());

        EditVolunteerCommand editVolunteerCommand = new EditVolunteerCommand(outOfBoundIndex,
                new EditVolunteerDescriptorBuilder().withName(VALID_VOLUNTEER_NAME_BOB).build());

        assertVolunteerCommandFailure(editVolunteerCommand, modelVolunteer, commandHistory,
                Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredVolunteerList_success() throws Exception {
        Volunteer editedVolunteer = new VolunteerBuilder().build();
        Volunteer volunteerToEdit = modelVolunteer.getFilteredVolunteerList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditVolunteerDescriptor descriptor = new EditVolunteerDescriptorBuilder(editedVolunteer).build();
        EditVolunteerCommand editVolunteerCommand = new EditVolunteerCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(modelVolunteer.getAddressBook()), new UserPrefs());
        expectedModel.updateVolunteer(volunteerToEdit, editedVolunteer);
        expectedModel.commitAddressBook();

        // edit -> first volunteer edited
        editVolunteerCommand.execute(modelVolunteer, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered volunteer list to show all volunteers
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), modelVolunteer, commandHistory,
                UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first volunteer edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), modelVolunteer, commandHistory,
                RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredVolunteerList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(modelVolunteer.getFilteredVolunteerList().size() + 1);
        EditVolunteerDescriptor descriptor = new EditVolunteerDescriptorBuilder()
                .withName(VALID_VOLUNTEER_NAME_BOB).build();
        EditVolunteerCommand editVolunteerCommand = new EditVolunteerCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertVolunteerCommandFailure(editVolunteerCommand, modelVolunteer, commandHistory,
                Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertVolunteerCommandFailure(new UndoCommand(), modelVolunteer, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertVolunteerCommandFailure(new RedoCommand(), modelVolunteer, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Volunteer} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited volunteer in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the volunteer object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameVolunteerEdited() throws Exception {
        Volunteer editedVolunteer = new VolunteerBuilder().build();
        EditVolunteerDescriptor descriptor = new EditVolunteerDescriptorBuilder(editedVolunteer).build();
        EditVolunteerCommand editVolunteerCommand = new EditVolunteerCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(modelVolunteer.getAddressBook()), new UserPrefs());

        showVolunteerAtIndex(modelVolunteer, INDEX_SECOND_PERSON);
        Volunteer volunteerToEdit = modelVolunteer.getFilteredVolunteerList().get(INDEX_FIRST_PERSON.getZeroBased());
        expectedModel.updateVolunteer(volunteerToEdit, editedVolunteer);
        expectedModel.commitAddressBook();

        // edit -> edits second volunteer in unfiltered volunteer list / first volunteer in filtered volunteer list
        editVolunteerCommand.execute(modelVolunteer, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered volunteer list to show all volunteers
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), modelVolunteer, commandHistory,
                UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredVolunteerList().get(INDEX_FIRST_PERSON.getZeroBased()), volunteerToEdit);
        // redo -> edits same second volunteer in unfiltered volunteer list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), modelVolunteer, commandHistory,
                RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);
        final EditVolunteerCommand standardVolunteerCommand =
                new EditVolunteerCommand(INDEX_FIRST_PERSON, DESC_VOLUNTEER_AMY);

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

        // same values -> returns true
        EditVolunteerDescriptor copyVolunteerDescriptor = new EditVolunteerDescriptor(DESC_VOLUNTEER_AMY);
        EditVolunteerCommand commandVolunteerWithSameValues = new EditVolunteerCommand(INDEX_FIRST_PERSON,
                copyVolunteerDescriptor);
        assertTrue(standardVolunteerCommand.equals(commandVolunteerWithSameValues));

        // same object -> returns true
        assertTrue(standardVolunteerCommand.equals(standardVolunteerCommand));

        // null -> returns false
        assertFalse(standardVolunteerCommand.equals(null));

        // different types -> returns false
        assertFalse(standardVolunteerCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardVolunteerCommand.equals(new EditVolunteerCommand(INDEX_SECOND_PERSON, DESC_VOLUNTEER_AMY)));

        // different descriptor -> returns false
        assertFalse(standardVolunteerCommand.equals(new EditVolunteerCommand(INDEX_FIRST_PERSON, DESC_VOLUNTEER_BOB)));
    }

}
