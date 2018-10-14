package seedu.learnvocabulary.logic.commands;

import static seedu.learnvocabulary.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.learnvocabulary.testutil.TypicalWords.getTypicalLearnVocabulary;

import org.junit.Before;
import org.junit.Test;

import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.ModelManager;
import seedu.learnvocabulary.model.UserPrefs;
import seedu.learnvocabulary.model.word.Word;
import seedu.learnvocabulary.testutil.WordBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalLearnVocabulary(), new UserPrefs());
    }


    @Test
    public void execute_newWord_success() {
        Word validWord = new WordBuilder().build();

        Model expectedModel = new ModelManager(model.getLearnVocabulary(), new UserPrefs());
        expectedModel.addWord(validWord);
        expectedModel.commitLearnVocabulary();

        assertCommandSuccess(new AddCommand(validWord), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validWord), expectedModel);
    }


    @Test
    public void execute_duplicateWord_throwsCommandException() {
        Word wordInList = model.getLearnVocabulary().getWordList().get(0);
        assertCommandFailure(new AddCommand(wordInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_WORD);
    }

}
