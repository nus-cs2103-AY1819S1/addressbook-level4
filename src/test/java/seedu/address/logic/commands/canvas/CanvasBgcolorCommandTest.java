package seedu.address.logic.commands.canvas;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.testutil.ModelGenerator;

class CanvasBgcolorCommandTest {

    @Test
    void execute_changeNone_success() {
        String args = "none";
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
    void execute_changeRgba_success() {
        String args = "rgba(0,0,0,0.0)";
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
    void execute_changeHex_success() {
        String args = "#00ff00";
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
        String args = "invalid";
        Model model = ModelGenerator.getDefaultModel();
        Model expectedModel = ModelGenerator.getDefaultModel();
        expectedModel.getCanvas().setBackgroundColor(args);
        CommandHistory ch = new CommandHistory();
        assertCommandSuccess(
                new CanvasBgcolorCommand(args),
                model,
                ch,
                String.format(CanvasBgcolorCommand.OUTPUT_FAILURE, args.toLowerCase())
                        + "\n\n"
                        + CanvasBgcolorCommand.MESSAGE_USAGE,
                expectedModel);
    }
}
