package seedu.address.logic.commands.layer;

import static org.junit.jupiter.api.Assertions.assertNull;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.testutil.ModelGenerator;

class LayerSwapCommandTest {

    @Test
    void execute_validSwap_success() {
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
    void execute_invalidSwap_success() {
        Index to = Index.fromOneBased(2);
        Index from = Index.fromOneBased(2);
        String args = String.format("%d %d", to.getOneBased(), from.getOneBased());
        Model model = ModelGenerator.getModelWithPopulatedCanvas();

        CommandHistory ch = new CommandHistory();
        assertCommandSuccess(
                new LayerSwapCommand(args),
                model,
                ch,
                String.format(LayerSwapCommand.OUTPUT_FAILURE),
                model);
    }
}
