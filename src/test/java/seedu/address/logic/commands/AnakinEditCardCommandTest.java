package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.AnakinCommandTestUtil.VALID_ANSWER_A;
import static seedu.address.logic.commands.AnakinCommandTestUtil.VALID_ANSWER_B;
import static seedu.address.logic.commands.AnakinCommandTestUtil.VALID_CARD_A_ARGS;
import static seedu.address.logic.commands.AnakinCommandTestUtil.VALID_QUESTION_A;
import static seedu.address.logic.commands.AnakinCommandTestUtil.VALID_QUESTION_B;
import static seedu.address.logic.commands.AnakinCommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.AnakinCommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.testutil.AnakinTypicalDecks.getTypicalAnakin;
import static seedu.address.testutil.AnakinTypicalDecks.getTypicalAnakinInDeck;
import static seedu.address.testutil.AnakinTypicalIndexes.INDEX_FIRST_CARD;
import static seedu.address.testutil.AnakinTypicalIndexes.INDEX_SECOND_CARD;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.anakincommands.AnakinEditCardCommand;
import seedu.address.logic.anakincommands.AnakinEditCardCommand.EditCardDescriptor;
import seedu.address.logic.anakincommands.AnakinEditDeckCommand;
import seedu.address.logic.anakincommands.AnakinRedoCommand;
import seedu.address.logic.anakincommands.AnakinUndoCommand;
import seedu.address.model.Anakin;
import seedu.address.model.AnakinModel;
import seedu.address.model.AnakinModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.anakindeck.AnakinCard;
import seedu.address.testutil.AnakinCardBuilder;
import seedu.address.testutil.EditCardDescriptorBuilder;

/**
 * Contains integration tests (interaction with the AnakinModel, UndoCommand and RedoCommand) and unit tests for AnakinEditCardCommand.
 */
public class AnakinEditCardCommandTest {

    private AnakinModel model = new AnakinModelManager(getTypicalAnakinInDeck(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        AnakinCard editedCard = new AnakinCardBuilder().build();
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder(editedCard).build();
        AnakinEditCardCommand anakinEditCardCommand = new AnakinEditCardCommand(INDEX_FIRST_CARD, descriptor);

        String expectedMessage = String.format(AnakinEditCardCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard);

        AnakinModel expectedModel = new AnakinModelManager(model.getAnakin(), new UserPrefs());
        expectedModel.updateCard(model.getFilteredCardList().get(INDEX_FIRST_CARD.getOneBased()), editedCard);
        expectedModel.commitAnakin();

        assertCommandSuccess(anakinEditCardCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastAnakinCard = Index.fromOneBased(model.getFilteredCardList().size());
        AnakinCard lastAnakinCard = model.getFilteredCardList().get(indexLastAnakinCard.getZeroBased());

        AnakinCardBuilder anakinCardInList = new AnakinCardBuilder(lastAnakinCard);
        AnakinCard editedAnakinCard = anakinCardInList
                .withQuestion(VALID_QUESTION_A).withAnswer(VALID_ANSWER_A)
                .build();

        EditCardDescriptor descriptor = new EditCardDescriptorBuilder()
                .withQuestion(VALID_QUESTION_A)
                .withAnswer(VALID_ANSWER_A).build();
        AnakinEditCardCommand anakinEditCardCommand = new AnakinEditCardCommand(indexLastAnakinCard, descriptor);

        String expectedMessage = String.format(AnakinEditCardCommand.MESSAGE_EDIT_CARD_SUCCESS, editedAnakinCard);

        AnakinModel expectedModel = new AnakinModelManager(new Anakin(model.getAnakin()), new UserPrefs());
        expectedModel.updateCard(lastAnakinCard, editedAnakinCard);
        expectedModel.commitAnakin();

        assertCommandSuccess(anakinEditCardCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        AnakinEditCardCommand anakinEditCardCommand = new AnakinEditCardCommand(INDEX_FIRST_CARD, new EditCardDescriptor());
        AnakinCard editedAnakinCard = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());

        String expectedMessage = String.format(AnakinEditCardCommand.MESSAGE_EDIT_CARD_SUCCESS, editedAnakinCard);

        AnakinModel expectedModel = new AnakinModelManager(new Anakin(model.getAnakin()), new UserPrefs());
        expectedModel.commitAnakin();

        assertCommandSuccess(anakinEditCardCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        //showCardAtIndex(model, INDEX_FIRST_CARD);

        AnakinCard anakinCardInFilteredList = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        AnakinCard editedAnakinCard = new AnakinCardBuilder(anakinCardInFilteredList).withQuestion(VALID_QUESTION_A).build();
        AnakinEditCardCommand anakinEditCardCommand = new AnakinEditCardCommand(INDEX_FIRST_CARD,
                new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_A).build());

        String expectedMessage = String.format(AnakinEditCardCommand.MESSAGE_EDIT_CARD_SUCCESS, editedAnakinCard);

        AnakinModel expectedModel = new AnakinModelManager(new Anakin(model.getAnakin()), new UserPrefs());
        expectedModel.updateCard(model.getFilteredCardList().get(0), editedAnakinCard);
        expectedModel.commitAnakin();

        assertCommandSuccess(anakinEditCardCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateAnakinCardUnfilteredList_failure() {
        AnakinCard firstAnakinCard = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder(firstAnakinCard).build();
        AnakinEditCardCommand anakinEditCardCommand = new AnakinEditCardCommand(INDEX_SECOND_CARD, descriptor);

        assertCommandFailure(anakinEditCardCommand, model, commandHistory, AnakinEditCardCommand.MESSAGE_DUPLICATE_CARD);
    }

    @Test
    public void execute_duplicateAnakinCardFilteredList_failure() {
        //showCardAtIndex(model, INDEX_FIRST_CARD);

        // edit anakincard in filtered list into a duplicate in address book
        AnakinCard anakincardInList = model.getAnakin().getCardList().get(INDEX_SECOND_CARD.getZeroBased());
        AnakinEditCardCommand anakinEditCardCommand = new AnakinEditCardCommand(INDEX_FIRST_CARD,
                new EditCardDescriptorBuilder(anakincardInList).build());

        assertCommandFailure(anakinEditCardCommand, model, commandHistory, AnakinEditCardCommand.MESSAGE_DUPLICATE_CARD);
    }

    @Test
    public void execute_invalidAnakinCardIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCardList().size() + 1);
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_A).build();
        AnakinEditCardCommand anakinEditCardCommand = new AnakinEditCardCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(anakinEditCardCommand, model, commandHistory, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
      // TODO: When filter functions are available write filter test
//    @Test
//    public void execute_invalidAnakinCardIndexFilteredList_failure() {
//        showCardAtIndex(model, INDEX_FIRST_CARD);
//        Index outOfBoundIndex = INDEX_SECOND_CARD;
//        // ensures that outOfBoundIndex is still in bounds of address book list
//        assertTrue(outOfBoundIndex.getZeroBased() < model.getAnakin().getCardList().size());
//
//        AnakinEditCardCommand anakinEditCardCommand = new AnakinEditCardCommand(outOfBoundIndex,
//                new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_A).build());
//
//        assertCommandFailure(anakinEditCardCommand, model, commandHistory, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
//    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        AnakinCard editedAnakinCard = new AnakinCardBuilder().build();
        AnakinCard anakincardToEdit = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder(editedAnakinCard).build();
        AnakinEditCardCommand anakinEditCardCommand = new AnakinEditCardCommand(INDEX_FIRST_CARD, descriptor);
        AnakinModel expectedModel = new AnakinModelManager(new Anakin(model.getAnakin()), new UserPrefs());
        expectedModel.updateCard(anakincardToEdit, editedAnakinCard);
        expectedModel.commitAnakin();

        // edit -> first anakincard edited
        anakinEditCardCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered anakincard list to show all anakincards
        expectedModel.undoAnakin();
        assertCommandSuccess(new AnakinUndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first anakincard edited again
        expectedModel.redoAnakin();
        assertCommandSuccess(new AnakinRedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCardList().size() + 1);
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_A).build();
        AnakinEditCardCommand anakinEditCardCommand = new AnakinEditCardCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(anakinEditCardCommand, model, commandHistory, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new AnakinUndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new AnakinRedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code AnakinCard} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited anakincard in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the anakincard object regardless of indexing.
     */

      //TODO when undo/redo is supported
//    @Test
//    public void executeUndoRedo_validIndexFilteredList_sameAnakinCardEdited() throws Exception {
//        AnakinCard editedAnakinCard = new AnakinCardBuilder().build();
//        EditCardDescriptor descriptor = new EditCardDescriptorBuilder(editedAnakinCard).build();
//        AnakinEditCardCommand anakinEditCardCommand = new AnakinEditCardCommand(INDEX_FIRST_CARD, descriptor);
//        AnakinModel expectedModel = new AnakinModelManager(new Anakin(model.getAnakin()), new UserPrefs());
//
//        //showAnakinCardAtIndex(model, INDEX_SECOND_CARD);
//        AnakinCard anakincardToEdit = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
//        expectedModel.updateCard(anakincardToEdit, editedAnakinCard);
//        expectedModel.commitAnakin();
//
//        // edit -> edits second anakincard in unfiltered anakincard list / first anakincard in filtered anakincard list
//        anakinEditCardCommand.execute(model, commandHistory);
//
//        // undo -> reverts addressbook back to previous state and filtered anakincard list to show all anakincards
//        expectedModel.undoAnakin();
//        assertCommandSuccess(new AnakinUndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);
//
//        assertNotEquals(model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased()), anakincardToEdit);
//        // redo -> edits same second anakincard in unfiltered anakincard list
//        expectedModel.redoAnakin();
//        assertCommandSuccess(new AnakinRedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
//    }

    @Test
    public void equals() {
        EditCardDescriptor CARD_A_DESC = new EditCardDescriptorBuilder()
                .withAnswer(VALID_ANSWER_A).withQuestion(VALID_QUESTION_A)
                .build();
        EditCardDescriptor CARD_B_DESC = new EditCardDescriptorBuilder()
                .withAnswer(VALID_ANSWER_B).withQuestion(VALID_QUESTION_B)
                .build();
        final AnakinEditCardCommand standardCommand = new AnakinEditCardCommand(INDEX_FIRST_CARD, CARD_A_DESC);

        // same values -> returns true
        EditCardDescriptor copyDescriptor = new EditCardDescriptor(CARD_A_DESC);
        AnakinEditCardCommand commandWithSameValues = new AnakinEditCardCommand(INDEX_FIRST_CARD, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AnakinEditCardCommand(INDEX_SECOND_CARD, CARD_A_DESC)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new AnakinEditCardCommand(INDEX_FIRST_CARD, CARD_B_DESC)));
    }

}
