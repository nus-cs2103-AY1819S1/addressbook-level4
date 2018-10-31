package seedu.learnvocabulary.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.learnvocabulary.testutil.TypicalWords.getTypicalLearnVocabulary;

import org.junit.Test;

import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.ModelManager;
import seedu.learnvocabulary.model.UserPrefs;
import seedu.learnvocabulary.model.tag.Tag;
//@@author Harryqu123
public class GroupaddCommandTest {
    private Model model = new ModelManager(getTypicalLearnVocabulary(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalLearnVocabulary(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_duplicate() {
        GroupaddCommand command = new GroupaddCommand(new Tag("toLearn"));
        assertCommandFailure(command, model, commandHistory, GroupaddCommand.MESSAGE_NO_GROUP);
    }

    @Test
    public void execute_normal() throws Exception {
        GroupaddCommand command = new GroupaddCommand(new Tag("test"));
        command.execute(model, commandHistory);
        expectedModel.addGroup(new Tag("test"));
        assertEquals(model.getTags(), expectedModel.getTags());
    }
    @Test
    public void execute_undo() throws Exception {
        GroupaddCommand command = new GroupaddCommand(new Tag("test"));
        command.execute(model, commandHistory);
        model.undoLearnVocabulary();
        assertEquals(model.getTags(), expectedModel.getTags());
    }
    @Test
    public void execute_clear() throws Exception {
        GroupaddCommand command = new GroupaddCommand(new Tag("test"));
        command.execute(model, commandHistory);
        model.resetData(getTypicalLearnVocabulary());
        assertEquals(model.getTags(), expectedModel.getTags());
    }
}
//
//@@author
