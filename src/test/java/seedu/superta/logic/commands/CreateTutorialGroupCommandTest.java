package seedu.superta.logic.commands;

import static seedu.superta.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.superta.testutil.TypicalSuperTaClient.getTypicalSuperTaClient;

import org.junit.Test;

import seedu.superta.logic.CommandHistory;
import seedu.superta.model.Model;
import seedu.superta.model.ModelManager;
import seedu.superta.model.UserPrefs;
import seedu.superta.model.tutorialgroup.TutorialGroup;

public class CreateTutorialGroupCommandTest {
    private static final String TUTORIAL_GROUP_TO_ADD_ID = "01a";
    private static final String TUTORIAL_GROUP_TO_ADD_NAME = "Lab1";

    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager(getTypicalSuperTaClient(), new UserPrefs());

    @Test
    public void execute_uniqueTutorialGroupId_success() {
        TutorialGroup tg = new TutorialGroup(TUTORIAL_GROUP_TO_ADD_ID, TUTORIAL_GROUP_TO_ADD_NAME);

        CreateTutorialGroupCommand createTutorialGroupCommand = new CreateTutorialGroupCommand(tg);

        Model expectedModel = new ModelManager(getTypicalSuperTaClient(), new UserPrefs());
        String expectedMessage = String.format(CreateTutorialGroupCommand.MESSAGE_SUCCESS, tg);
        expectedModel.addTutorialGroup(tg);
        expectedModel.commitSuperTaClient();

        assertCommandSuccess(createTutorialGroupCommand, model, commandHistory, expectedMessage, expectedModel);
    }
}
