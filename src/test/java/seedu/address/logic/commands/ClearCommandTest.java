package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalArticles.getTypicalArticleList;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.ArticleList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyArticleList_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitArticleList();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyArticleList_success() {
        Model model = new ModelManager(getTypicalArticleList(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalArticleList(), new UserPrefs());
        expectedModel.resetData(new ArticleList());
        expectedModel.commitArticleList();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
