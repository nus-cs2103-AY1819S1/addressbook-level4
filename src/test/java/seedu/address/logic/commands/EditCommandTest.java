package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showArticleAtIndex;
import static seedu.address.testutil.TypicalArticles.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ARTICLE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ARTICLE;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditCommand.EditArticleDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.article.Article;
import seedu.address.testutil.ArticleBuilder;
import seedu.address.testutil.EditArticleDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Article editedArticle = new ArticleBuilder().build();
        EditArticleDescriptor descriptor = new EditArticleDescriptorBuilder(editedArticle).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ARTICLE, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ARTICLE_SUCCESS, editedArticle);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateArticle(model.getFilteredArticleList().get(0), editedArticle);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastArticle = Index.fromOneBased(model.getFilteredArticleList().size());
        Article lastArticle = model.getFilteredArticleList().get(indexLastArticle.getZeroBased());

        ArticleBuilder articleInList = new ArticleBuilder(lastArticle);
        Article editedArticle = articleInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditArticleDescriptor descriptor = new EditArticleDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastArticle, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ARTICLE_SUCCESS, editedArticle);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateArticle(lastArticle, editedArticle);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ARTICLE, new EditArticleDescriptor());
        Article editedArticle = model.getFilteredArticleList().get(INDEX_FIRST_ARTICLE.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ARTICLE_SUCCESS, editedArticle);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showArticleAtIndex(model, INDEX_FIRST_ARTICLE);

        Article articleInFilteredList = model.getFilteredArticleList().get(INDEX_FIRST_ARTICLE.getZeroBased());
        Article editedArticle = new ArticleBuilder(articleInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ARTICLE,
                new EditArticleDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ARTICLE_SUCCESS, editedArticle);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateArticle(model.getFilteredArticleList().get(0), editedArticle);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateArticleUnfilteredList_failure() {
        Article firstArticle = model.getFilteredArticleList().get(INDEX_FIRST_ARTICLE.getZeroBased());
        EditArticleDescriptor descriptor = new EditArticleDescriptorBuilder(firstArticle).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_ARTICLE, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_ARTICLE);
    }

    @Test
    public void execute_duplicateArticleFilteredList_failure() {
        showArticleAtIndex(model, INDEX_FIRST_ARTICLE);

        // edit article in filtered list into a duplicate in address book
        Article articleInList = model.getAddressBook().getArticleList().get(INDEX_SECOND_ARTICLE.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ARTICLE,
                new EditArticleDescriptorBuilder(articleInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_ARTICLE);
    }

    @Test
    public void execute_invalidArticleIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredArticleList().size() + 1);
        EditArticleDescriptor descriptor = new EditArticleDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_ARTICLE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidArticleIndexFilteredList_failure() {
        showArticleAtIndex(model, INDEX_FIRST_ARTICLE);
        Index outOfBoundIndex = INDEX_SECOND_ARTICLE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getArticleList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditArticleDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_ARTICLE_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Article editedArticle = new ArticleBuilder().build();
        Article articleToEdit = model.getFilteredArticleList().get(INDEX_FIRST_ARTICLE.getZeroBased());
        EditArticleDescriptor descriptor = new EditArticleDescriptorBuilder(editedArticle).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ARTICLE, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateArticle(articleToEdit, editedArticle);
        expectedModel.commitAddressBook();

        // edit -> first article edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered article list to show all articles
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first article edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredArticleList().size() + 1);
        EditArticleDescriptor descriptor = new EditArticleDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_ARTICLE_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Article} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited article in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the article object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameArticleEdited() throws Exception {
        Article editedArticle = new ArticleBuilder().build();
        EditArticleDescriptor descriptor = new EditArticleDescriptorBuilder(editedArticle).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ARTICLE, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        showArticleAtIndex(model, INDEX_SECOND_ARTICLE);
        Article articleToEdit = model.getFilteredArticleList().get(INDEX_FIRST_ARTICLE.getZeroBased());
        expectedModel.updateArticle(articleToEdit, editedArticle);
        expectedModel.commitAddressBook();

        // edit -> edits second article in unfiltered article list / first article in filtered article list
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered article list to show all articles
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredArticleList().get(INDEX_FIRST_ARTICLE.getZeroBased()), articleToEdit);
        // redo -> edits same second article in unfiltered article list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_ARTICLE, DESC_AMY);

        // same values -> returns true
        EditArticleDescriptor copyDescriptor = new EditArticleDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_ARTICLE, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_ARTICLE, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_ARTICLE, DESC_BOB)));
    }

}
