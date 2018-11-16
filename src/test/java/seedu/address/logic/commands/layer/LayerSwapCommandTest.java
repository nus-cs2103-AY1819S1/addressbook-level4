package seedu.address.logic.commands.layer;

import static org.junit.jupiter.api.Assertions.assertNull;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.testutil.ModelGenerator;

class LayerSwapCommandTest {

    @Test
    public void executeValidSwapSuccess() {
        Index to = Index.fromOneBased(2);
        Index from = Index.fromOneBased(3);
        String args = String.format("%d %d", to.getOneBased(), from.getOneBased());
        Model model = ModelGenerator.getModelWithPopulatedCanvas();
        Model expectedModel = ModelGenerator.getModelWithPopulatedCanvas();
        try {
            expectedModel.getCanvas().swapLayer(to, from);
        } catch (Exception e) {
            assertNull(e);

        }
        CommandHistory ch = new CommandHistory();
        assertCommandSuccess(
                new LayerSwapCommand(args),
                model,
                ch,
                String.format(LayerSwapCommand.OUTPUT_SUCCESS, to.getOneBased(), from.getOneBased()),
                expectedModel);
    }

    @Test
    public void executeInvalidSwapFailure() {
        Index to = Index.fromOneBased(1);
        Index from = Index.fromOneBased(5);
        String args = String.format("%d %d", to.getOneBased(), from.getOneBased());
        Model model = ModelGenerator.getModelWithPopulatedCanvas();

        CommandHistory ch = new CommandHistory();
        assertCommandFailure(
                new LayerSwapCommand(args),
                model,
                ch,
                LayerSwapCommand.OUTPUT_FAILURE
        );
    }

    @Test
    public void executeInvalidSameIndexFailure() {
        Index index = Index.fromOneBased(2);
        String args = String.format("%d %d", index.getOneBased(), index.getOneBased());
        Model model = ModelGenerator.getModelWithPopulatedCanvas();

        CommandHistory ch = new CommandHistory();
        assertCommandFailure(
                new LayerSwapCommand(args),
                model,
                ch,
                LayerSwapCommand.OUTPUT_ILLEGAL
        );
    }
}
