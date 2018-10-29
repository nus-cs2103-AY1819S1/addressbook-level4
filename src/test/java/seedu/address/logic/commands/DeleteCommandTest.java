package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showCardAtIndex;
import static seedu.address.testutil.TypicalCards.getTypicalTriviaBundle;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_CARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CARD;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.card.Card;
import seedu.address.model.test.TriviaResults;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalTriviaBundle(),
            new TriviaResults(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Card cardToDelete = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_CARD);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_CARD_SUCCESS, cardToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getTriviaBundle(),
                new TriviaResults(), new UserPrefs());
        expectedModel.deleteCard(cardToDelete);
        expectedModel.commitTriviaBundle();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCardList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showCardAtIndex(model, INDEX_FIRST_CARD);

        Card cardToDelete = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_CARD);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_CARD_SUCCESS, cardToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getTriviaBundle(),
                new TriviaResults(), new UserPrefs());
        expectedModel.deleteCard(cardToDelete);
        expectedModel.commitTriviaBundle();
        showNoCard(expectedModel);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showCardAtIndex(model, INDEX_FIRST_CARD);

        Index outOfBoundIndex = INDEX_SECOND_CARD;
        // ensures that outOfBoundIndex is still in bounds of trivia bundle list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTriviaBundle().getCardList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Card cardToDelete = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_CARD);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getTriviaBundle(),
                new TriviaResults(), new UserPrefs());
        expectedModel.deleteCard(cardToDelete);
        expectedModel.commitTriviaBundle();

        // delete -> first card deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts triviabundle back to previous state and filtered card list to show all cards
        expectedModel.undoTriviaBundle();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first card deleted again
        expectedModel.redoTriviaBundle();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCardList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        // execution failed -> trivia bundle state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);

        // single trivia bundle state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Card} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted card in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the card object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameCardDeleted() throws Exception {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_CARD);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getTriviaBundle(),
                new TriviaResults(), new UserPrefs());

        showCardAtIndex(model, INDEX_FOURTH_CARD);
        Card cardToDelete = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        expectedModel.deleteCard(cardToDelete);
        expectedModel.commitTriviaBundle();

        // delete -> deletes second card in unfiltered card list / first card in filtered card list
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts triviabundle back to previous state and filtered card list to show all cards
        expectedModel.undoTriviaBundle();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(cardToDelete, model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased()));
        // redo -> deletes same second card in unfiltered card list
        expectedModel.redoTriviaBundle();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_CARD);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_CARD);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_CARD);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different card -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoCard(Model model) {
        model.updateFilteredCardList(p -> false);

        assertTrue(model.getFilteredCardList().isEmpty());
    }
}
