package seedu.address.logic.commands.canvas;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.testutil.ModelGenerator;

class CanvasBgcolorCommandTest {

    @Test
    public void executeChangeNoneSuccess() {
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
    public void executeChangeRgbaSuccess() {
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
    public void executeChangeHexSuccess() {
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
    public void executeChangeInvalidFailure() {
        String args = "invalid";
        Model model = ModelGenerator.getDefaultModel();
        CommandHistory ch = new CommandHistory();
        assertCommandFailure(
                new CanvasBgcolorCommand(args),
                model,
                ch,
                String.format(CanvasBgcolorCommand.OUTPUT_FAILURE, args.toLowerCase())
                        + "\n\n"
                        + CanvasBgcolorCommand.MESSAGE_USAGE
        );
    }
}
