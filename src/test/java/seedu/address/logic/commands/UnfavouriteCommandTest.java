package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalSsenisub;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code FavouriteCommand}.
 */
public class UnfavouriteCommandTest {

    private Model model = new ModelManager(getTypicalSsenisub(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_success() {

        // person needs to be favourited first

        Person personToFavourite = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person favouritedPerson = new PersonBuilder(personToFavourite).withFavourite(true).build();

        FavouriteCommand favouriteCommand = new FavouriteCommand(INDEX_FIRST_PERSON);

        String expectedFavouriteMessage = String.format(FavouriteCommand.MESSAGE_FAVOURITE_PERSON_SUCCESS,
                favouritedPerson.getName().fullName);

        Model expectedFavouriteModel = new ModelManager(model.getSsenisub(), new UserPrefs());
        expectedFavouriteModel.favouritePerson(personToFavourite, favouritedPerson);
        expectedFavouriteModel.commitSsenisub();

        assertCommandSuccess(favouriteCommand, model, commandHistory, expectedFavouriteMessage, model);

        Person personToUnfavourite = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person unfavouritedPerson = new PersonBuilder(personToUnfavourite).withFavourite(false).build();

        UnfavouriteCommand unfavouriteCommand = new UnfavouriteCommand(INDEX_FIRST_PERSON);

        String expectedUnfavouriteMessage = String.format(UnfavouriteCommand.MESSAGE_UNFAVOURITE_PERSON_SUCCESS,
                unfavouritedPerson.getName().fullName);

        Model expectedModel = new ModelManager(model.getSsenisub(), new UserPrefs());
        expectedModel.unfavouritePerson(personToUnfavourite, unfavouritedPerson);
        expectedModel.commitSsenisub();

        assertCommandSuccess(unfavouriteCommand, model, commandHistory, expectedUnfavouriteMessage, model);

        // unfavourite fail

        UnfavouriteCommand failUnfavouriteCommand = new UnfavouriteCommand(INDEX_FIRST_PERSON);

        String expectedFailUnfavouriteMessage = String.format(UnfavouriteCommand.MESSAGE_UNFAVOURITE_PERSON_FAIL,
                unfavouritedPerson.getName().fullName);

        assertCommandSuccess(failUnfavouriteCommand, model, commandHistory, expectedFailUnfavouriteMessage, model);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnfavouriteCommand unfavouriteCommand = new UnfavouriteCommand(outOfBoundIndex);

        assertCommandFailure(unfavouriteCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getSsenisub().getPersonList().size());

        UnfavouriteCommand unfavouriteCommand = new UnfavouriteCommand(outOfBoundIndex);

        assertCommandFailure(unfavouriteCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnfavouriteCommand unfavouriteCommand = new UnfavouriteCommand(outOfBoundIndex);

        // execution failed -> address book state not added into model
        assertCommandFailure(unfavouriteCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        UnfavouriteCommand unfavouriteFirstCommand = new UnfavouriteCommand(INDEX_FIRST_PERSON);
        UnfavouriteCommand unfavouriteSecondCommand = new UnfavouriteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(unfavouriteFirstCommand.equals(unfavouriteFirstCommand));

        // same values -> returns true
        UnfavouriteCommand unfavouriteFirstCommandCopy = new UnfavouriteCommand(INDEX_FIRST_PERSON);
        assertTrue(unfavouriteFirstCommand.equals(unfavouriteFirstCommandCopy));

        // different types -> returns false
        assertFalse(unfavouriteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unfavouriteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(unfavouriteFirstCommand.equals(unfavouriteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
