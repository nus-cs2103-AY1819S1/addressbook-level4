package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalAddressbook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class DeleteProfilePhotoCommandTest {

    private ModelManager model =
            new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Index index = INDEX_FIRST_PERSON;

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {


        Person toUpdatePerson = model.getFilteredPersonList().get(index.getZeroBased());
        Person updatedPerson = new Person(toUpdatePerson);

        updatedPerson.deleteProfilePhoto();
        DeleteProfilePhotoCommand deleteProfilePhotoCommand = prepareCommand(index);
        String expectedMessage = String.format(DeleteProfilePhotoCommand.MESSAGE_DELETE_PROFILE_PIC_SUCCESS,
                index.getOneBased());
        ModelManager expectedModel =
                new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deleteProfilePhotoCommand, model, commandHistory, expectedMessage, expectedModel);

    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {

        showPersonAtIndex(model, index);

        Person toUpdatePerson = model.getFilteredPersonList().get(index.getZeroBased());
        Person updatedPerson = new Person(toUpdatePerson);

        updatedPerson.deleteProfilePhoto();
        DeleteProfilePhotoCommand deleteProfilePhotoCommand = prepareCommand(index);
        String expectedMessage = String.format(DeleteProfilePhotoCommand.MESSAGE_DELETE_PROFILE_PIC_SUCCESS,
                index.getOneBased());
        ModelManager expectedModel =
                new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deleteProfilePhotoCommand, model, commandHistory, expectedMessage, expectedModel);

    }

    @Test
    public void execute_filteredListInvalidIndex_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteProfilePhotoCommand deleteProfilePhotoCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteProfilePhotoCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        DeleteProfilePhotoCommand deletePhotoCommandFirst = prepareCommand(INDEX_FIRST_PERSON);
        DeleteProfilePhotoCommand deletePhotoCommandSecond = prepareCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deletePhotoCommandFirst.equals(deletePhotoCommandFirst));

        // same values -> returns true
        DeleteProfilePhotoCommand deleteProfilePhotoCommandFirstCopy = prepareCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteProfilePhotoCommandFirstCopy.equals(deletePhotoCommandFirst));

        // different types -> returns false
        assertFalse(deletePhotoCommandFirst.equals(1));

        // null -> returns false
        assertFalse(deletePhotoCommandFirst.equals(null));

        // different indexes -> returns false
        assertFalse(deletePhotoCommandSecond.equals(deletePhotoCommandFirst));

    }


    /**
     * Returns an {@code ResetPictureCommand} with parameters {@code index}
     */
    private DeleteProfilePhotoCommand prepareCommand(Index index) {
        DeleteProfilePhotoCommand deleteProfilePhotoCommand = new DeleteProfilePhotoCommand(index);
        return deleteProfilePhotoCommand;
    }
}
