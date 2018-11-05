package seedu.address.logic.commands.canvas;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.testutil.ModelGenerator;

class CanvasSizeCommandTest {

    @Test
    void execute_changeSize_success() {
        int newHeight = 123;
        int newWidth = 456;
        String args = String.format("%dx%d", newWidth, newHeight);
        Model model = ModelGenerator.getDefaultModel();
        Model expectedModel = ModelGenerator.getDefaultModel();
        expectedModel.getCanvas().setHeight(newHeight);
        expectedModel.getCanvas().setWidth(newWidth);
        CommandHistory ch = new CommandHistory();
        assertCommandSuccess(
                new CanvasSizeCommand(args),
                model,
                ch,
                String.format(CanvasSizeCommand.OUTPUT_SUCCESS, newWidth, newHeight),
                expectedModel);
    }

    @Test
    void execute_listSize_success() {
        String args = null;
        Model model = ModelGenerator.getDefaultModel();
        Model expectedModel = ModelGenerator.getDefaultModel();
        expectedModel.getCanvas().setBackgroundColor(args);
        CommandHistory ch = new CommandHistory();
        assertCommandSuccess(
                new CanvasSizeCommand(args),
                model,
                ch,
                String.format(CanvasSizeCommand.OUTPUT_SUCCESS,
                        model.getCanvas().getWidth(), model.getCanvas().getHeight()),
                expectedModel);
    }

    @Test
    void execute_invalidSize_success() {
        String args = "-100x100";
        Model model = ModelGenerator.getDefaultModel();
        Model expectedModel = ModelGenerator.getDefaultModel();
        expectedModel.getCanvas().setBackgroundColor(args);
        CommandHistory ch = new CommandHistory();
        assertCommandSuccess(
                new CanvasBgcolorCommand(args),
                model,
                ch,
                String.format(CanvasBgcolorCommand.OUTPUT_SUCCESS, args),
                expectedModel);
    }

    @Test
    void execute_changeInvalid_success() {
        int newHeight = -123;
        int newWidth = 456;
        String args = String.format("%dx%d", newWidth, newHeight);
        Model model = ModelGenerator.getDefaultModel();
        Model expectedModel = ModelGenerator.getDefaultModel();
        expectedModel.getCanvas().setHeight(newHeight);
        expectedModel.getCanvas().setWidth(newWidth);
        CommandHistory ch = new CommandHistory();
        assertCommandSuccess(
                new CanvasSizeCommand(args),
                model,
                ch,
                String.format(CanvasSizeCommand.OUTPUT_FAILURE, newWidth, newHeight)
                        + "\n\n"
                        + CanvasSizeCommand.MESSAGE_USAGE,
                expectedModel);
    }
}
