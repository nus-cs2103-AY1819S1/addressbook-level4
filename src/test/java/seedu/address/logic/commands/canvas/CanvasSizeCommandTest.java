package seedu.address.logic.commands.canvas;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.testutil.ModelGenerator;

class CanvasSizeCommandTest {

    @Test
    public void executeChangeSizeSuccess() {
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
    public void executeListSizeSuccess() {
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
    public void executeInvalidSizeFailure() {
        String args = "-100x100";
        Model model = ModelGenerator.getDefaultModel();
        CommandHistory ch = new CommandHistory();
        assertCommandFailure(
                new CanvasSizeCommand(args),
                model,
                ch,
                CanvasSizeCommand.OUTPUT_FAILURE
                        + "\n\n"
                        + CanvasSizeCommand.MESSAGE_USAGE
        );
    }

    @Test
    public void executeChangeInvalidFailure() {
        int newHeight = -123;
        int newWidth = 456;
        String args = String.format("%dx%d", newWidth, newHeight);
        Model model = ModelGenerator.getDefaultModel();
        CommandHistory ch = new CommandHistory();
        assertCommandFailure(
                new CanvasSizeCommand(args),
                model,
                ch,
                CanvasSizeCommand.OUTPUT_FAILURE
                        + "\n\n"
                        + CanvasSizeCommand.MESSAGE_USAGE
        );
    }
}
