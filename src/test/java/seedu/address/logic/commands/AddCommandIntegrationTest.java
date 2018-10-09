package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalArticles.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.article.Article;
import seedu.address.testutil.ArticleBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newArticle_success() {
        Article validArticle = new ArticleBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addArticle(validArticle);
        expectedModel.commitAddressBook();

        assertCommandSuccess(new AddCommand(validArticle), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validArticle), expectedModel);
    }

    @Test
    public void execute_duplicateArticle_throwsCommandException() {
        Article articleInList = model.getAddressBook().getArticleList().get(0);
        assertCommandFailure(new AddCommand(articleInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_ARTICLE);
    }

}
