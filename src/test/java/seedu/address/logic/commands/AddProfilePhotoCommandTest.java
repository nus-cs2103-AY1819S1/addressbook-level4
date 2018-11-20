package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCAL_IMAGE_JPG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCAL_IMAGE_PNG;
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
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class AddProfilePhotoCommandTest {

    private ModelManager model =
            new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Index index = INDEX_FIRST_PERSON;
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validFilepathUnfilteredList_success() throws Exception {


        Person toUpdatePerson = model.getFilteredPersonList().get(index.getZeroBased());
        Person updatedPerson = new Person(toUpdatePerson);

        updatedPerson.setProfilePhoto(VALID_LOCAL_IMAGE_JPG);
        AddProfilePhotoCommand addProfilePhotoCommand = prepareCommand(index, VALID_LOCAL_IMAGE_JPG);
        String expectedMessage = String.format(AddProfilePhotoCommand.MESSAGE_EDIT_PERSON_SUCCESS, index.getOneBased());
        ModelManager expectedModel =
                new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(addProfilePhotoCommand, model, commandHistory, expectedMessage, expectedModel);

    }

    @Test
    public void execute_validFilepathFilteredList_success() throws Exception {

        showPersonAtIndex(model, index);

        Person toUpdatePerson = model.getFilteredPersonList().get(index.getZeroBased());
        Person updatedPerson = new Person(toUpdatePerson);

        updatedPerson.setProfilePhoto(VALID_LOCAL_IMAGE_JPG);
        AddProfilePhotoCommand addProfilePhotoCommand = prepareCommand(index, VALID_LOCAL_IMAGE_JPG);
        String expectedMessage = String.format(AddProfilePhotoCommand.MESSAGE_EDIT_PERSON_SUCCESS, index.getOneBased());
        Model expectedModel =
                new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);
        expectedModel.commitAddressBook();

        assertCommandSuccess(addProfilePhotoCommand, model, commandHistory, expectedMessage, expectedModel);

    }

    @Test
    public void execute_filteredListInvalidIndex_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddProfilePhotoCommand addProfilePhotoCommand = prepareCommand(outOfBoundIndex, VALID_LOCAL_IMAGE_JPG);

        assertCommandFailure(addProfilePhotoCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        AddProfilePhotoCommand addPhotoCommandFirst = prepareCommand(INDEX_FIRST_PERSON, VALID_LOCAL_IMAGE_JPG);
        AddProfilePhotoCommand addPhotoCommandSecond = prepareCommand(INDEX_SECOND_PERSON, VALID_LOCAL_IMAGE_PNG);

        // same object -> returns true
        assertTrue(addPhotoCommandFirst.equals(addPhotoCommandFirst));

        // same values -> returns true
        AddProfilePhotoCommand addPhotoCommandFirstCopy = prepareCommand(INDEX_FIRST_PERSON, VALID_LOCAL_IMAGE_JPG);
        assertTrue(addPhotoCommandFirstCopy.equals(addPhotoCommandFirst));

        // different types -> returns false
        assertFalse(addPhotoCommandFirst.equals(1));

        // null -> returns false
        assertFalse(addPhotoCommandFirst.equals(null));

        // different indexes -> returns false
        AddProfilePhotoCommand apc = prepareCommand(INDEX_SECOND_PERSON, VALID_LOCAL_IMAGE_JPG);
        assertFalse(apc.equals(addPhotoCommandFirst));

        // same indexes, different files -> returns false
        assertFalse(apc.equals(addPhotoCommandSecond));

        // both index and files different -> returns false
        assertFalse(addPhotoCommandSecond.equals(addPhotoCommandFirst));

    }


    /**
     * Returns an {@code AddPictureCommand} with parameters {@code index} and {@code path}
     */
    private AddProfilePhotoCommand prepareCommand(Index index, String path) {
        AddProfilePhotoCommand addPhotoCommand = new AddProfilePhotoCommand(index, path);
        return addPhotoCommand;
    }
}
