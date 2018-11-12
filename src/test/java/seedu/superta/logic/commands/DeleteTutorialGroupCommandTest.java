package seedu.superta.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.superta.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.superta.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.superta.logic.commands.DeleteTutorialGroupCommand.MESSAGE_FAILURE;
import static seedu.superta.testutil.TypicalSuperTaClient.getTypicalSuperTaClient;

import org.junit.Test;

import seedu.superta.logic.CommandHistory;
import seedu.superta.model.Model;
import seedu.superta.model.ModelManager;
import seedu.superta.model.UserPrefs;
import seedu.superta.model.tutorialgroup.TutorialGroup;

public class DeleteTutorialGroupCommandTest {

    private static final String TG_ID_TO_DELETE = "T01";
    private static final String TG_NAME_TO_DELETE = "TutorialGroup01";

    private Model model = new ModelManager(getTypicalSuperTaClient(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_tutorialGroupFound_success() {
        DeleteTutorialGroupCommand deleteTutorialGroupCommand = new DeleteTutorialGroupCommand(TG_ID_TO_DELETE);
        TutorialGroup tutorialGroup = new TutorialGroup(TG_ID_TO_DELETE, TG_NAME_TO_DELETE);
        model.addTutorialGroup(tutorialGroup);

        Model expectedModel = new ModelManager(getTypicalSuperTaClient(), new UserPrefs());
        expectedModel.addTutorialGroup(tutorialGroup);
        expectedModel.deleteTutorialGroup(new TutorialGroup(TG_ID_TO_DELETE, "toDelete"));
        expectedModel.commitSuperTaClient();
        String expectedMessage = String.format(DeleteTutorialGroupCommand.MESSAGE_SUCCESS, TG_ID_TO_DELETE);

        assertCommandSuccess(deleteTutorialGroupCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_tutorialGroupNotFound_throwsCommandException() {
        model.addTutorialGroup(new TutorialGroup("Z01", "TestZ01"));
        DeleteTutorialGroupCommand deleteTutorialGroupCommand = new DeleteTutorialGroupCommand(TG_ID_TO_DELETE);

        assertCommandFailure(deleteTutorialGroupCommand, model, commandHistory, MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        DeleteTutorialGroupCommand firstCommand = new DeleteTutorialGroupCommand(TG_ID_TO_DELETE);
        DeleteTutorialGroupCommand secondCommand = new DeleteTutorialGroupCommand("Z01");

        //same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        //same String object -> returns true
        DeleteTutorialGroupCommand firstCommandCopy = new DeleteTutorialGroupCommand(TG_ID_TO_DELETE);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different id -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }
}
