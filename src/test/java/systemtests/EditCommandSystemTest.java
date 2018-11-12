package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.lostandfound.logic.commands.CommandTestUtil.DESCRIPTION_DESC_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.DESCRIPTION_DESC_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.EMAIL_DESC_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.EMAIL_DESC_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.FINDER_DESC_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.FINDER_DESC_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.lostandfound.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.lostandfound.logic.commands.CommandTestUtil.INVALID_FINDER_DESC;
import static seedu.lostandfound.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.lostandfound.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.lostandfound.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.lostandfound.logic.commands.CommandTestUtil.NAME_DESC_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.NAME_DESC_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.PHONE_DESC_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.PHONE_DESC_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.TAG_DESC_BLUE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.TAG_DESC_RED;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_DESCRIPTION_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_NAME_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_NAME_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_TAG_BLUE;
import static seedu.lostandfound.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.lostandfound.model.Model.NOT_RESOLVED_PREDICATE;
import static seedu.lostandfound.testutil.TypicalArticles.FINDER_KEYWORD_MATCHING_MEIER;
import static seedu.lostandfound.testutil.TypicalArticles.MOUSE;
import static seedu.lostandfound.testutil.TypicalArticles.POWERBANK;
import static seedu.lostandfound.testutil.TypicalIndexes.INDEX_FIRST_ARTICLE;
import static seedu.lostandfound.testutil.TypicalIndexes.INDEX_SECOND_ARTICLE;

import org.junit.Test;

import seedu.lostandfound.commons.core.Messages;
import seedu.lostandfound.commons.core.index.Index;
import seedu.lostandfound.logic.commands.EditCommand;
import seedu.lostandfound.logic.commands.RedoCommand;
import seedu.lostandfound.logic.commands.UndoCommand;
import seedu.lostandfound.model.Model;
import seedu.lostandfound.model.article.Article;
import seedu.lostandfound.model.article.Description;
import seedu.lostandfound.model.article.Email;
import seedu.lostandfound.model.article.Name;
import seedu.lostandfound.model.article.Phone;
import seedu.lostandfound.model.tag.Tag;
import seedu.lostandfound.testutil.ArticleBuilder;
import seedu.lostandfound.testutil.ArticleUtil;

public class EditCommandSystemTest extends ArticleListSystemTest {

    @Test
    public void edit() {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_ARTICLE;
        String command = " " + EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + NAME_DESC_MOUSE + "  "
                + FINDER_DESC_MOUSE + "  " + PHONE_DESC_MOUSE + " " + EMAIL_DESC_MOUSE + "  "
                + DESCRIPTION_DESC_MOUSE + " " + TAG_DESC_BLUE + " ";
        Article editedArticle = new ArticleBuilder(MOUSE).withTags(VALID_TAG_BLUE).build();
        assertCommandSuccess(command, index, editedArticle);

        /* Case: undo editing the last article in the list -> last article restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last article in the list -> last article edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateArticle(
                getModel().getFilteredArticleList().get(INDEX_FIRST_ARTICLE.getZeroBased()), editedArticle);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a article with new values same as existing values -> edited */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_MOUSE + PHONE_DESC_MOUSE
                + EMAIL_DESC_MOUSE + DESCRIPTION_DESC_MOUSE + FINDER_DESC_MOUSE + TAG_DESC_BLUE + TAG_DESC_RED;
        assertCommandSuccess(command, index, MOUSE);

        /* Case: edit a article with new values same as another article's values but with different name -> edited */
        assertTrue(getModel().getArticleList().getArticleList().contains(MOUSE));
        index = INDEX_SECOND_ARTICLE;
        assertNotEquals(getModel().getFilteredArticleList().get(index.getZeroBased()), MOUSE);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_POWERBANK + PHONE_DESC_MOUSE
                + FINDER_DESC_MOUSE + EMAIL_DESC_MOUSE + DESCRIPTION_DESC_MOUSE + TAG_DESC_BLUE + TAG_DESC_RED;
        editedArticle = new ArticleBuilder(MOUSE).withName(VALID_NAME_POWERBANK).build();
        assertCommandSuccess(command, index, editedArticle);

        /* Case: edit a article with new values same as another article's values but with different description
         * -> edited
         */
        index = INDEX_SECOND_ARTICLE;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_MOUSE + PHONE_DESC_MOUSE
                + EMAIL_DESC_MOUSE + DESCRIPTION_DESC_POWERBANK + FINDER_DESC_MOUSE + TAG_DESC_BLUE + TAG_DESC_RED;
        editedArticle = new ArticleBuilder(MOUSE).withDescription(VALID_DESCRIPTION_POWERBANK).build();
        assertCommandSuccess(command, index, editedArticle);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_ARTICLE;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        Article articleToEdit = getModel().getFilteredArticleList().get(index.getZeroBased());
        editedArticle = new ArticleBuilder(articleToEdit).withTags().build();
        assertCommandSuccess(command, index, editedArticle);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered article list, edit index within bounds of article list and article list -> edited */
        showArticlesWithName(FINDER_KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST_ARTICLE;
        assertTrue(index.getZeroBased() < getModel().getFilteredArticleList().size());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + NAME_DESC_MOUSE;
        articleToEdit = getModel().getFilteredArticleList().get(index.getZeroBased());
        editedArticle = new ArticleBuilder(articleToEdit).withName(VALID_NAME_MOUSE).build();
        assertCommandSuccess(command, index, editedArticle);

        /* Case: filtered article list, edit index within bounds of article list but out of bounds of article list
         * -> rejected
         */
        showArticlesWithName(FINDER_KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getArticleList().getArticleList().size();
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_MOUSE,
                Messages.MESSAGE_INVALID_ARTICLE_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a article card is selected ------------------------- */

        /* Case: selects first card in the article list, edit a article -> edited, card selection remains unchanged but
         * article details changes
         */
        showAllArticles();
        index = INDEX_FIRST_ARTICLE;
        selectArticle(index);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_POWERBANK + PHONE_DESC_POWERBANK
                + FINDER_DESC_POWERBANK + EMAIL_DESC_POWERBANK + DESCRIPTION_DESC_POWERBANK + TAG_DESC_RED;
        // this can be misleading: card selection actually remains unchanged but the
        // article details is updated to reflect the new article's name
        assertCommandSuccess(command, index, POWERBANK, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + NAME_DESC_MOUSE,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + NAME_DESC_MOUSE,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredArticleList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_MOUSE,
                Messages.MESSAGE_INVALID_ARTICLE_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + NAME_DESC_MOUSE,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_ARTICLE.getOneBased(),
                EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_ARTICLE.getOneBased()
                        + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        /* Case: invalid finder -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_ARTICLE.getOneBased()
                + INVALID_FINDER_DESC, Name.MESSAGE_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_ARTICLE.getOneBased()
                        + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_ARTICLE.getOneBased()
                        + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS);

        /* Case: invalid description -> rejected */
        assertCommandFailure(
                EditCommand.COMMAND_WORD + " " + INDEX_FIRST_ARTICLE.getOneBased()
                        + INVALID_DESCRIPTION_DESC, Description.MESSAGE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_ARTICLE.getOneBased()
                        + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS);

        /* Case: edit a article with new values same as another article's values -> rejected */
        executeCommand(ArticleUtil.getAddCommand(MOUSE));
        assertTrue(getModel().getArticleList().getArticleList().contains(MOUSE));
        index = INDEX_FIRST_ARTICLE;
        assertFalse(getModel().getFilteredArticleList().get(index.getZeroBased()).equals(MOUSE));
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_MOUSE + PHONE_DESC_MOUSE
                + EMAIL_DESC_MOUSE + DESCRIPTION_DESC_MOUSE + FINDER_DESC_MOUSE + TAG_DESC_BLUE + TAG_DESC_RED;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_ARTICLE);

        /* Case: edit a article with new values same as another article's values but with different tags -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_MOUSE + PHONE_DESC_MOUSE
                + EMAIL_DESC_MOUSE + DESCRIPTION_DESC_MOUSE + FINDER_DESC_MOUSE + TAG_DESC_RED;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_ARTICLE);

        /* Case: edit a article with new values same as another article's values but with different finder-> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_MOUSE + PHONE_DESC_MOUSE
                + EMAIL_DESC_MOUSE + DESCRIPTION_DESC_MOUSE + FINDER_DESC_POWERBANK + TAG_DESC_BLUE + TAG_DESC_RED;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_ARTICLE);

        /* Case: edit a article with new values same as another article's values but with different phone -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_MOUSE + PHONE_DESC_POWERBANK
                + EMAIL_DESC_MOUSE + DESCRIPTION_DESC_MOUSE + FINDER_DESC_MOUSE + TAG_DESC_BLUE + TAG_DESC_RED;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_ARTICLE);

        /* Case: edit a article with new values same as another article's values but with different email -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_MOUSE + PHONE_DESC_MOUSE
                + EMAIL_DESC_POWERBANK + DESCRIPTION_DESC_MOUSE + FINDER_DESC_MOUSE + TAG_DESC_BLUE + TAG_DESC_RED;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_ARTICLE);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Article, Index)} except that
     * the article details and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, Article, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Article editedArticle) {
        assertCommandSuccess(command, toEdit, editedArticle, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the article at index {@code toEdit} being
     * updated to values specified {@code editedArticle}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Article editedArticle,
            Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.updateArticle(expectedModel.getFilteredArticleList().get(toEdit.getZeroBased()), editedArticle);
        expectedModel.updateFilteredArticleList(NOT_RESOLVED_PREDICATE);

        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_ARTICLE_SUCCESS, editedArticle), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * article details and selected card remain unchanged.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the article details and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code ArticleListSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see ArticleListSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see ArticleListSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredArticleList(NOT_RESOLVED_PREDICATE);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the article details, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code ArticleListSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see ArticleListSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
