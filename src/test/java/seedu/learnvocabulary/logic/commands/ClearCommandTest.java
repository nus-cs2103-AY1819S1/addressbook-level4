package seedu.learnvocabulary.logic.commands;

import static seedu.learnvocabulary.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.learnvocabulary.testutil.TypicalWords.getTypicalLearnVocabulary;

import org.junit.Test;

import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.model.LearnVocabulary;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.ModelManager;
import seedu.learnvocabulary.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyLearnVocabulary_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitLearnVocabulary();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyLearnVocabulary_success() {
        Model model = new ModelManager(getTypicalLearnVocabulary(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalLearnVocabulary(), new UserPrefs());
        expectedModel.resetData(new LearnVocabulary());
        expectedModel.commitLearnVocabulary();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
