package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalSsenisub;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.PrivacyCommand.FieldsToChange;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Ssenisub;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.FieldsToChangeBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

public class PrivacyCommandTest {

    private Model model = new ModelManager(getTypicalSsenisub(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person editedPerson = TypicalPersons.ALICE;
        FieldsToChange fieldsToChange = new FieldsToChangeBuilder().withNotPrivate().build();
        PrivacyCommand privacyCommand = new PrivacyCommand(INDEX_FIRST_PERSON, fieldsToChange);

        String expectedMessage = String.format(PrivacyCommand.MESSAGE_EDIT_PRIVACY_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new Ssenisub(model.getSsenisub()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
        expectedModel.commitSsenisub();

        assertCommandSuccess(privacyCommand, model, commandHistory, expectedMessage, expectedModel);

    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());

        FieldsToChange fieldsToChange = new FieldsToChangeBuilder().withPrivatePhone().withPrivateEmail().build();
        PrivacyCommand privacyCommand = new PrivacyCommand(indexLastPerson, fieldsToChange);

        try {
            CommandResult result = privacyCommand.execute(model, commandHistory);
            Assert.assertEquals(model.getFilteredPersonList().get(indexLastPerson.getZeroBased())
                            .getPhone().isPrivate(),
                    true);
            Assert.assertEquals(model.getFilteredPersonList().get(indexLastPerson.getZeroBased())
                            .getEmail().isPrivate(),
                    true);
            Assert.assertEquals(model.getFilteredPersonList().get(indexLastPerson.getZeroBased())
                            .getAddress().isPrivate(),
                    false);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        FieldsToChange fieldsToChange = new FieldsToChangeBuilder().withPrivateEmail().build();
        PrivacyCommand privacyCommand = new PrivacyCommand(outOfBoundIndex, fieldsToChange);

        assertCommandFailure(privacyCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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
        assertTrue(outOfBoundIndex.getZeroBased() < model.getSsenisub().getPersonList().size());

        PrivacyCommand privacyCommand = new PrivacyCommand(outOfBoundIndex,
                new FieldsToChangeBuilder().withPrivateEmail().build());

        assertCommandFailure(privacyCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Person editedPerson = new PersonBuilder().build();
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        FieldsToChange fieldsToChange = new FieldsToChangeBuilder().withAllPrivate().build();
        PrivacyCommand privacyCommand = new PrivacyCommand(INDEX_FIRST_PERSON, fieldsToChange);
        Model expectedModel = new ModelManager(new Ssenisub(model.getSsenisub()), new UserPrefs());
        expectedModel.updatePerson(personToEdit, editedPerson);
        expectedModel.commitSsenisub();
        // edit -> first person edited
        privacyCommand.execute(model, commandHistory);
        Assert.assertEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased())
                        .getPhone().isPrivate(), true);
        Assert.assertEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased())
                        .getEmail().isPrivate(), true);
        Assert.assertEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased())
                        .getAddress().isPrivate(), true);

        // undo -> reverts Ssenisub back to previous state and filtered person list to show all persons
        model.undoSsenisub();
        Assert.assertEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased())
                        .getPhone().isPrivate(), false);
        Assert.assertEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased())
                        .getEmail().isPrivate(), false);
        Assert.assertEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased())
                        .getAddress().isPrivate(), false);

        // redo -> same first person edited again
        model.redoSsenisub();
        Assert.assertEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased())
                        .getPhone().isPrivate(), true);
        Assert.assertEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased())
                        .getEmail().isPrivate(), true);
        Assert.assertEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased())
                        .getAddress().isPrivate(), true);
    }
}
