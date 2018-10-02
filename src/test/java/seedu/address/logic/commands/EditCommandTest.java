package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_EARTH_FLAT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_GIT_COMMIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_GIT_COMMIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PHYSICS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showCardAtIndex;
import static seedu.address.testutil.TypicalCards.getTypicalTriviaBundle;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CARD;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditCommand.EditCardDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TriviaBundle;
import seedu.address.model.UserPrefs;
import seedu.address.model.card.Card;
import seedu.address.testutil.CardBuilder;
import seedu.address.testutil.EditCardDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalTriviaBundle(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allTriviaFieldsSpecifiedUnfilteredList_success() {
        Card editedCard = new CardBuilder().build();
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder(editedCard).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new TriviaBundle(model.getTriviaBundle()), new UserPrefs());
        expectedModel.updateCard(model.getFilteredCardList().get(0), editedCard);
        expectedModel.commitTriviaBundle();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someTriviaFieldsSpecifiedUnfilteredList_success() {
        Index indexLastCard = Index.fromOneBased(model.getFilteredCardList().size());
        Card lastCard = model.getFilteredCardList().get(indexLastCard.getZeroBased());

        CardBuilder cardInList = new CardBuilder(lastCard);
        Card editedCard = cardInList.withQuestion(VALID_QUESTION_GIT_COMMIT)
                .withTags(VALID_TAG_PHYSICS).build();

        EditCardDescriptor descriptor = new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_GIT_COMMIT)
                .withTags(VALID_TAG_PHYSICS).build();
        EditCommand editCommand = new EditCommand(indexLastCard, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new TriviaBundle(model.getTriviaBundle()), new UserPrefs());
        expectedModel.updateCard(lastCard, editedCard);
        expectedModel.commitTriviaBundle();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noTriviaFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CARD, new EditCardDescriptor());
        Card editedCard = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new TriviaBundle(model.getTriviaBundle()), new UserPrefs());
        expectedModel.commitTriviaBundle();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredTriviaList_success() {
        showCardAtIndex(model, INDEX_FIRST_CARD);

        Card cardInFilteredList = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        Card editedCard = new CardBuilder(cardInFilteredList).withQuestion(VALID_QUESTION_GIT_COMMIT).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CARD,
                new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_GIT_COMMIT).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new TriviaBundle(model.getTriviaBundle()), new UserPrefs());
        expectedModel.updateCard(model.getFilteredCardList().get(0), editedCard);
        expectedModel.commitTriviaBundle();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateCardUnfilteredList_failure() {
        Card firstCard = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder(firstCard).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_CARD, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_CARD);
    }

    @Test
    public void execute_duplicateCardFilteredList_failure() {
        showCardAtIndex(model, INDEX_FIRST_CARD);

        // edit card in filtered list into a duplicate in trivia bundle
        Card cardInList = model.getTriviaBundle().getCardList().get(INDEX_SECOND_CARD.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CARD,
                new EditCardDescriptorBuilder(cardInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_CARD);
    }

    @Test
    public void execute_invalidCardIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCardList().size() + 1);
        EditCardDescriptor descriptor = new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_GIT_COMMIT).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidCardIndexFilteredList_failure() {
        showCardAtIndex(model, INDEX_FIRST_CARD);
        Index outOfBoundIndex = INDEX_SECOND_CARD;
        // ensures that outOfBoundIndex is still in bounds of trivia bundle list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTriviaBundle().getCardList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_GIT_COMMIT).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
    }

    // TODO To enable this after undo/redo command is implemented on trivia.
    //    @Test
    //    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
    //        Person editedPerson = new PersonBuilder().build();
    //        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
    //        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
    //        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
    //        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
    //        expectedModel.updatePerson(personToEdit, editedPerson);
    //        expectedModel.commitAddressBook();
    //
    //        // edit -> first person edited
    //        editCommand.execute(model, commandHistory);
    //
    //        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
    //        expectedModel.undoAddressBook();
    //        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS,
    // expectedModel);
    //
    //        // redo -> same first person edited again
    //        expectedModel.redoAddressBook();
    //        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS,
    // expectedModel);
    //    }
    //
    //    @Test
    //    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
    //        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
    //        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
    //        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);
    //
    //        // execution failed -> address book state not added into model
    //        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    //
    //        // single address book state in model -> undoCommand and redoCommand fail
    //        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
    //        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    //    }
    //
    //    /**
    //     * 1. Edits a {@code Person} from a filtered list.
    //     * 2. Undo the edit.
    //     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited person in the
    //     * unfiltered list is different from the index at the filtered list.
    //     * 4. Redo the edit. This ensures {@code RedoCommand} edits the person object regardless of indexing.
    //     */
    //    @Test
    //    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
    //        Person editedPerson = new PersonBuilder().build();
    //        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
    //        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
    //        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
    //
    //        showPersonAtIndex(model, INDEX_SECOND_PERSON);
    //        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
    //        expectedModel.updatePerson(personToEdit, editedPerson);
    //        expectedModel.commitAddressBook();
    //
    //        // edit -> edits second person in unfiltered person list / first person in filtered person list
    //        editCommand.execute(model, commandHistory);
    //
    //        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
    //        expectedModel.undoAddressBook();
    //        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS,
    // expectedModel);
    //
    //        assertNotEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), personToEdit);
    //        // redo -> edits same second person in unfiltered person list
    //        expectedModel.redoAddressBook();
    //        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS,
    // expectedModel);
    //    }


    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_CARD, DESC_EARTH_FLAT);

        // same values -> returns true
        EditCardDescriptor copyDescriptor = new EditCardDescriptor(DESC_EARTH_FLAT);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_CARD, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(1));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_CARD, DESC_EARTH_FLAT)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_CARD, DESC_GIT_COMMIT)));
    }
}
