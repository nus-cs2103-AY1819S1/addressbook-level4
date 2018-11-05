package seedu.address.logic.commands.layer;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.testutil.ModelGenerator;

class LayerPositionCommandTest {

    @Test
    void execute_validPosition_success() {
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
}
