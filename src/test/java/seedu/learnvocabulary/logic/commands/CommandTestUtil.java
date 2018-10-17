package seedu.learnvocabulary.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.learnvocabulary.logic.parser.CliSyntax.PREFIX_MEANING;
import static seedu.learnvocabulary.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.learnvocabulary.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.learnvocabulary.commons.core.index.Index;
import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.logic.commands.exceptions.CommandException;
import seedu.learnvocabulary.model.LearnVocabulary;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.word.NameContainsKeywordsPredicate;
import seedu.learnvocabulary.model.word.Word;
import seedu.learnvocabulary.testutil.EditWordDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_MEANING = "Test";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String MEANING_DESC = " " + PREFIX_MEANING + VALID_MEANING;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditWordDescriptor DESC_AMY;
    public static final EditCommand.EditWordDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditWordDescriptorBuilder().withName(VALID_NAME_AMY).withMeaning(VALID_MEANING)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditWordDescriptorBuilder().withName(VALID_NAME_BOB).withMeaning(VALID_MEANING)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the learnvocabulary book and the filtered word list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        LearnVocabulary expectedLearnVocabulary = new LearnVocabulary(actualModel.getLearnVocabulary());
        List<Word> expectedFilteredList = new ArrayList<>(actualModel.getFilteredWordList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedLearnVocabulary, actualModel.getLearnVocabulary());
            assertEquals(expectedFilteredList, actualModel.getFilteredWordList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the word at the given {@code targetIndex} in the
     * {@code model}'s learnvocabulary book.
     */
    public static void showWordAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredWordList().size());

        Word word = model.getFilteredWordList().get(targetIndex.getZeroBased());
        final String[] splitName = word.getName().fullName.split("\\s+");
        model.updateFilteredWordList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredWordList().size());
    }

    /**
     * Deletes the first word in {@code model}'s filtered list from {@code model}'s learnvocabulary book.
     */
    public static void deleteFirstWord(Model model) {
        Word firstWord = model.getFilteredWordList().get(0);
        model.deleteWord(firstWord);
        model.commitLearnVocabulary();
    }

}
