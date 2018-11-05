package seedu.address.logic.commands.canvas;

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
    void execute_invalidCommand_success() {
        String args = "invalid";
        Model model = ModelGenerator.getDefaultModel();
        Model expectedModel = ModelGenerator.getDefaultModel();
        CommandHistory ch = new CommandHistory();
        assertCommandSuccess(
                new CanvasAutoResizeCommand(args),
                model,
                ch,
                String.format(CanvasAutoResizeCommand.OUTPUT_FAILURE, args)
                        + "\n\n"
                        + CanvasAutoResizeCommand.MESSAGE_USAGE,
                expectedModel
        );
    }
}
