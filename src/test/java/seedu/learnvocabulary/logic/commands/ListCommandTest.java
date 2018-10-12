package seedu.learnvocabulary.logic.commands;

import static seedu.learnvocabulary.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.showWordAtIndex;
import static seedu.learnvocabulary.testutil.TypicalIndexes.INDEX_FIRST_WORD;
import static seedu.learnvocabulary.testutil.TypicalWords.getTypicalLearnVocabulary;

import org.junit.Before;
import org.junit.Test;

import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.ModelManager;
import seedu.learnvocabulary.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalLearnVocabulary(), new UserPrefs());
        expectedModel = new ModelManager(model.getLearnVocabulary(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, commandHistory, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showWordAtIndex(model, INDEX_FIRST_WORD);
        assertCommandSuccess(new ListCommand(), model, commandHistory, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
