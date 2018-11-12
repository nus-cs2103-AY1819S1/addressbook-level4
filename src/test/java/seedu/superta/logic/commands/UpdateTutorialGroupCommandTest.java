package seedu.superta.logic.commands;

import static seedu.superta.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.superta.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.superta.testutil.TypicalSuperTaClient.getTypicalSuperTaClient;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.superta.logic.CommandHistory;
import seedu.superta.model.Model;
import seedu.superta.model.ModelManager;
import seedu.superta.model.SuperTaClient;
import seedu.superta.model.UserPrefs;
import seedu.superta.model.tutorialgroup.TutorialGroup;

public class UpdateTutorialGroupCommandTest {

    private Model model = new ModelManager(getTypicalSuperTaClient(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    private final String typicalTutorialGroupId = "testing_purposes_only";
    private final String typicalTutorialGroupName = "Test Tutorial Group";

    private final String wrongTutorialGroupId = "wrong_tutorial_group_id";

    private final String updatedName = "Updated Tutorial Group Name";

    @Before
    public void createModelTutorialGroup() {
        assert !model.hasTutorialGroup(typicalTutorialGroupId);
        TutorialGroup existingTutorialGroup = new TutorialGroup(typicalTutorialGroupId, typicalTutorialGroupName);
        model.addTutorialGroup(existingTutorialGroup);
    }

    @After
    public void deleteModelTutorialGroup() {
        assert model.hasTutorialGroup(typicalTutorialGroupId);
        model.deleteTutorialGroup(new TutorialGroup(typicalTutorialGroupId, typicalTutorialGroupName));
    }

    public Model getInitialExpectedModel() {
        Model expectedModel = new ModelManager(new SuperTaClient(getTypicalSuperTaClient()), new UserPrefs());
        TutorialGroup existingTutorialGroup = new TutorialGroup(typicalTutorialGroupId, typicalTutorialGroupName);
        expectedModel.addTutorialGroup(existingTutorialGroup);
        return expectedModel;
    }

    public Model getInitialModel() {
        return new ModelManager(getTypicalSuperTaClient(), new UserPrefs());
    }

    @Test
    public void execute_allFieldsPresent_success() {
        TutorialGroup updatedTutorialGroup = new TutorialGroup(typicalTutorialGroupId, updatedName);
        UpdateTutorialGroupCommand.UpdateTutorialGroupDescriptor descriptor = new UpdateTutorialGroupCommand
            .UpdateTutorialGroupDescriptor();
        descriptor.setName(updatedName);
        UpdateTutorialGroupCommand command = new UpdateTutorialGroupCommand(typicalTutorialGroupId, descriptor);

        String expectedMessage = String.format(UpdateTutorialGroupCommand.MESSAGE_SUCCESS);

        Model expectedModel = getInitialExpectedModel();
        expectedModel.updateTutorialGroup(updatedTutorialGroup);
        expectedModel.commitSuperTaClient();

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allOptionalFieldsBlankEdited_success() {
        UpdateTutorialGroupCommand.UpdateTutorialGroupDescriptor descriptor = new UpdateTutorialGroupCommand
            .UpdateTutorialGroupDescriptor();
        Model expectedModel = getInitialExpectedModel();
        expectedModel.commitSuperTaClient();
        assertCommandSuccess(new UpdateTutorialGroupCommand(typicalTutorialGroupId, descriptor), model,
                             commandHistory, UpdateTutorialGroupCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_noSuchTutorialGroup_failure() {
        UpdateTutorialGroupCommand.UpdateTutorialGroupDescriptor descriptor = new UpdateTutorialGroupCommand
            .UpdateTutorialGroupDescriptor();
        descriptor.setName(updatedName);
        assertCommandFailure(new UpdateTutorialGroupCommand(wrongTutorialGroupId, descriptor), model, commandHistory,
                             String.format(UpdateTutorialGroupCommand.MESSAGE_NO_SUCH_TUTORIAL_GROUP,
                                           wrongTutorialGroupId));
    }

    @Test
    public void executeUndoRedo_validDescriptor_success() throws Exception {
        TutorialGroup updatedTutorialGroup = new TutorialGroup(typicalTutorialGroupId, updatedName);
        UpdateTutorialGroupCommand.UpdateTutorialGroupDescriptor descriptor = new UpdateTutorialGroupCommand
            .UpdateTutorialGroupDescriptor();
        descriptor.setName(updatedName);
        UpdateTutorialGroupCommand command = new UpdateTutorialGroupCommand(typicalTutorialGroupId, descriptor);

        Model expectedModel = getInitialExpectedModel();
        expectedModel.updateTutorialGroup(updatedTutorialGroup);
        expectedModel.commitSuperTaClient();

        // update -> tutorial group updated
        command.execute(model, commandHistory);

        // undo -> reverts client back to previous state
        expectedModel.undoSuperTaClient();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same tutorial group updated again
        expectedModel.redoSuperTaClient();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
