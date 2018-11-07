package seedu.learnvocabulary.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.ModelManager;
import seedu.learnvocabulary.model.word.Word;
import seedu.learnvocabulary.testutil.WordBuilder;

public class WordOfTheDayCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullWord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new WordOfTheDayCommand(null);
    }

    @Test
    public void execute_displayWordOfTheDay_Successful() {
        Model model = new ModelManager();
        Word validWord = new WordBuilder().build();
        String validOutput = WordOfTheDayCommand.MESSAGE_SUCCESS.replace("%1$s\n", validWord.toString());
        validOutput += "\n";

        CommandResult commandResult = new WordOfTheDayCommand(validWord).execute(model, commandHistory);
        assertNotNull(commandResult.feedbackToUser);
        assertEquals(validOutput, commandResult.feedbackToUser);
    }

}
