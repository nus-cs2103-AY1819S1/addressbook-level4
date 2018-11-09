package seedu.address.logic.commands.canvas;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.testutil.ModelGenerator;

class CanvasAutoResizeCommandTest {

    @Test
    void execute_turnOn_success() {
        String args = "on";
        Model model = ModelGenerator.getDefaultModel();
        Model expectedModel = ModelGenerator.getDefaultModel();
        expectedModel.getCanvas().setCanvasAuto(true);
        CommandHistory ch = new CommandHistory();
        assertCommandSuccess(
                new CanvasAutoResizeCommand(args),
                model,
                ch,
                String.format(CanvasAutoResizeCommand.OUTPUT_SUCCESS, args),
                expectedModel);
    }

    @Test
    void execute_turnOff_success() {
        String args = "off";
        Model model = ModelGenerator.getDefaultModel();
        Model expectedModel = ModelGenerator.getDefaultModel();
        expectedModel.getCanvas().setCanvasAuto(false);
        CommandHistory ch = new CommandHistory();
        assertCommandSuccess(
                new CanvasAutoResizeCommand(args),
                model,
                ch,
                String.format(CanvasAutoResizeCommand.OUTPUT_SUCCESS, args),
                expectedModel);
    }

    @Test
    void execute_invalidCommand_failure() {
        String args = "invalid";
        Model model = ModelGenerator.getDefaultModel();
        CommandHistory ch = new CommandHistory();
        assertCommandFailure(
                new CanvasAutoResizeCommand(args),
                model,
                ch,
                String.format(CanvasAutoResizeCommand.OUTPUT_FAILURE, args)
                        + "\n\n"
                        + CanvasAutoResizeCommand.MESSAGE_USAGE
        );
    }
}
