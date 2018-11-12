package seedu.learnvocabulary.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.learnvocabulary.testutil.TypicalWords.getTypicalLearnVocabulary;

import java.util.Arrays;

import org.junit.Test;

import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.ModelManager;
import seedu.learnvocabulary.model.UserPrefs;
import seedu.learnvocabulary.model.tag.Tag;
import seedu.learnvocabulary.model.word.TagContainsKeywordsPredicate;
import seedu.learnvocabulary.model.word.Word;
import seedu.learnvocabulary.testutil.WordBuilder;

//@@author Harryqu123
public class GroupdeleteCommandTest {
    private Model model = new ModelManager(getTypicalLearnVocabulary(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalLearnVocabulary(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_noexist() {
        GroupdeleteCommand command = new GroupdeleteCommand(new Tag("test"));
        assertCommandFailure(command, model, commandHistory, GroupdeleteCommand.MESSAGE_NO_GROUP);
    }

    @Test
    public void execute_normal_tag() throws Exception {
        model.addGroup(new Tag("test"));
        GroupdeleteCommand command = new GroupdeleteCommand(new Tag("test"));
        command.execute(model, commandHistory);
        assertEquals(model.getTags(), expectedModel.getTags());
    }

    @Test
    public void execute_normal_word() throws Exception {
        model.addGroup(new Tag("test"));
        Word deletedWord = new WordBuilder().withTags("test").build();
        AddCommand commandHelp = new AddCommand(deletedWord);
        commandHelp.execute(model, commandHistory);
        GroupdeleteCommand command = new GroupdeleteCommand(new Tag("test"));
        command.execute(model, commandHistory);
        model.updateFilteredWordList(new TagContainsKeywordsPredicate(Arrays.asList("test")));
        assertTrue(model.getFilteredWordList().isEmpty());
    }
}
//
//@@author
