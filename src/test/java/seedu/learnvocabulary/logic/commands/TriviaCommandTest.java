package seedu.learnvocabulary.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static seedu.learnvocabulary.testutil.TypicalWords.getTypicalLearnVocabulary;

import org.junit.Test;

import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.ModelManager;
import seedu.learnvocabulary.model.UserPrefs;
import seedu.learnvocabulary.model.word.Word;
import seedu.learnvocabulary.testutil.WordBuilder;

public class TriviaCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    private Model model = new ModelManager(getTypicalLearnVocabulary(), new UserPrefs());


    @Test
    public void execute_triviaMode_unsuccessful() {
        Model modelNoTrivia = new ModelManager();
        CommandResult commandResult = new TriviaCommand().execute(modelNoTrivia, commandHistory);

        assertEquals(TriviaCommand.MESSAGE_FAIL, commandResult.feedbackToUser);
        assertFalse(modelNoTrivia.isTriviaMode());

    }

    @Test
    public void execute_triviaMode_successful() {
        CommandResult commandResult = new TriviaCommand().execute(model, commandHistory);

        assertEquals(TriviaCommand.MESSAGE_SUCCESS,
                commandResult.feedbackToUser.substring(0, 16));
        assertTrue(model.isTriviaMode());
    }

    @Test
    public void answer_triviaMode_correctly() {
        CommandResult triviaCommandResult = new TriviaCommand().execute(model, commandHistory);
        Word triviaQuestion = model.getTrivia();

        CommandResult commandResult = new TriviaAnsCommand(triviaQuestion.getName().toString())
                .execute(model, commandHistory);

        assertEquals(TriviaAnsCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser.substring(0, 8));
    }

    @Test
    public void answer_triviaMode_wrongly() {
        CommandResult triviaCommandResult = new TriviaCommand().execute(model, commandHistory);

        CommandResult commandResult = new TriviaAnsCommand("This is wrong").execute(model, commandHistory);

        assertEquals(TriviaAnsCommand.MESSAGE_WRONG, commandResult.feedbackToUser.substring(0, 6));
    }

    @Test
    public void score_increases_correct() {
        CommandResult triviaCommandResult = new TriviaCommand().execute(model, commandHistory);
        Word triviaQuestion = model.getTrivia();

        CommandResult commandResult = new TriviaAnsCommand(triviaQuestion.getName().toString())
                .execute(model, commandHistory);

        assertEquals(1, model.currentScore());
    }

    @Test
    public void score_remainsSame_wrong() {
        CommandResult triviaCommandResult = new TriviaCommand().execute(model, commandHistory);

        CommandResult commandResult = new TriviaAnsCommand("This is wrong").execute(model, commandHistory);

        assertEquals(0, model.currentScore());
    }

    @Test
    public void questions_withlessThanTenWords() {
        CommandResult triviaCommandResult = new TriviaCommand().execute(model, commandHistory);
        int questionCount = 0;
        int listSize = model.getTriviaList().size();

        while (model.isTriviaMode()) {
            CommandResult commandResult = new TriviaAnsCommand("This is wrong").execute(model, commandHistory);
            questionCount++;
        }

        assertEquals(listSize, questionCount);


    }

    @Test
    public void questions_withTenWords() {
        Word hi = new WordBuilder().withName("hi")
                .withMeaning("used as an exclamation of greeting; hello!").build();
        Word sane = new WordBuilder().withName("sane")
                .withMeaning("free from mental derangement; having a sound, healthy mind: a sane person.")
                .build();
        Word test = new WordBuilder().withName("test").withMeaning("test").build();

        model.addWord(hi);
        model.addWord(sane);
        model.addWord(test);

        CommandResult triviaCommandResult = new TriviaCommand().execute(model, commandHistory);
        int questionCount = 0;
        int listSize = model.getTriviaList().size();

        while (model.isTriviaMode()) {
            CommandResult commandResult = new TriviaAnsCommand("This is wrong").execute(model, commandHistory);
            questionCount++;
        }

        assertEquals(10, listSize);
        assertEquals(listSize, questionCount);

    }

    @Test
    public void questions_withMoreThanTenWords() {
        Word hi = new WordBuilder().withName("hi")
                .withMeaning("used as an exclamation of greeting; hello!").build();
        Word sane = new WordBuilder().withName("sane")
                .withMeaning("free from mental derangement; having a sound, healthy mind: a sane person.")
                .build();
        Word test = new WordBuilder().withName("test").withMeaning("test").build();
        Word another = new WordBuilder().withName("another").withMeaning("another test").build();

        model.addWord(hi);
        model.addWord(sane);
        model.addWord(test);
        model.addWord(another);

        CommandResult triviaCommandResult = new TriviaCommand().execute(model, commandHistory);
        int questionCount = 0;
        int listSize = model.getTriviaList().size();

        while (model.isTriviaMode()) {
            CommandResult commandResult = new TriviaAnsCommand("This is wrong").execute(model, commandHistory);
            questionCount++;
        }

        assertNotEquals(11, listSize);
        assertEquals(listSize, questionCount);

    }

    @Test
    public void exit_triviaMode_success() {
        CommandResult triviaCommandResult = new TriviaCommand().execute(model, commandHistory);
        CommandResult commandResult = new TriviaAnsCommand("triviaExit").execute(model, commandHistory);
        assertEquals(TriviaAnsCommand.MESSAGE_END, commandResult.feedbackToUser);
    }

}
