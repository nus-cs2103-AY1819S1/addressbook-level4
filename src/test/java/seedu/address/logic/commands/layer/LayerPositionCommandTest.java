package seedu.address.logic.commands.layer;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.testutil.ModelGenerator;

class LayerPositionCommandTest {

    @Test
    public void executeValidPositionSuccess() {
        int newX = 123;
        int newY = 456;
        String args = String.format("%dx%d", newX, newY);
        Model model = ModelGenerator.getDefaultModel();
        Model expectedModel = ModelGenerator.getDefaultModel();
        CommandHistory ch = new CommandHistory();
        assertCommandSuccess(
                new LayerPositionCommand(args),
                model,
                ch,
                String.format(LayerPositionCommand.OUTPUT_SUCCESS, newX, newY),
                expectedModel);
    }

    @Test
    public void executeNullBothPositionFailure() {
        String args = null;
        Model model = ModelGenerator.getDefaultModel();
        CommandHistory ch = new CommandHistory();

        assertCommandFailure(
                new LayerPositionCommand(args),
                model,
                ch,
                LayerPositionCommand.OUTPUT_FAILURE
        );
    }

    @Test
    public void executeNullSinglePositionFailure() {
        String args = "3 ";
        Model model = ModelGenerator.getDefaultModel();
        CommandHistory ch = new CommandHistory();

        assertCommandFailure(
                new LayerPositionCommand(args),
                model,
                ch,
                LayerPositionCommand.OUTPUT_FAILURE
        );
    }
}
