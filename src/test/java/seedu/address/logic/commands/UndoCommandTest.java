package seedu.address.logic.commands;

import static seedu.address.logic.commands.UndoRedoCommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.UndoRedoCommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.ModelGenerator.executeATransformation;
import static seedu.address.testutil.ModelGenerator.getDefaultModel;
import static seedu.address.testutil.ModelGenerator.getModelWithOneTransformation;
import static seedu.address.testutil.ModelGenerator.getModelWithThreeTransformations;
import static seedu.address.testutil.ModelGenerator.getModelWithTwoTransformations;

import java.io.File;

import org.junit.Test;

import seedu.address.MainApp;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

public class UndoCommandTest {
    private CommandHistory commandHistory = new CommandHistory();
    private UndoCommand undoCommand = new UndoCommand();

    @Test
    public void execute_defaultStateHasNothingToUndo() {
        Model model = getDefaultModel();
        assertCommandFailure(undoCommand, model, commandHistory, "No more commands to undo!");
        clearCache();
    }

    @Test
    public void execute_singleUndo() {
        Model model = getModelWithOneTransformation();
        assertCommandSuccess(undoCommand, model, commandHistory, "Undo success!", 0, 2);
        clearCache();
    }

    @Test
    public void execute_successiveUndo() {
        Model model = getModelWithTwoTransformations();
        assertCommandSuccess(undoCommand, model, commandHistory, "Undo success!", 1, 3);
        assertCommandSuccess(undoCommand, model, commandHistory, "Undo success!", 0, 3);
        clearCache();
    }

    @Test
    public void execute_successiveUndoWithPurge() {
        Model model = getModelWithThreeTransformations();
        assertCommandSuccess(undoCommand, model, commandHistory, "Undo success!", 2, 4);
        assertCommandSuccess(undoCommand, model, commandHistory, "Undo success!", 1, 4);
        assertCommandSuccess(undoCommand, model, commandHistory, "Undo success!", 0, 4);
        Model purgedModel = executeATransformation(model);
        assertCommandSuccess(undoCommand, purgedModel, commandHistory, "Undo success!", 0, 2);
        clearCache();
    }

    /**
     * Clears cache in storage folder.
     */
    public void clearCache() {
        String cachePath = MainApp.MAIN_PATH + "/src/main/java/seedu/address/storage/cache";
        File cache = new File(cachePath);
        File[] list = cache.listFiles();
        if (list != null) {
            for (File file: list) {
                if (!file.getName().equals("dummy.txt")) {
                    file.delete();
                }
            }
        }
    }
}
