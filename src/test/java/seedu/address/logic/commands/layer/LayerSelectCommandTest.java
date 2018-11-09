package seedu.address.logic.commands.layer;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.testutil.ModelGenerator;

class LayerSelectCommandTest {

    @Test
    void execute_nullArgument_failure() {
        Model model = ModelGenerator.getModelWithPopulatedCanvas();
        CommandHistory ch = new CommandHistory();
        assertCommandFailure(
                new LayerSelectCommand(null),
                model,
                ch,
                LayerSelectCommand.OUTPUT_FAILURE
        );
    }

    @Test
    void execute_layerValidSelect_success() {
        String args = "2";
        Index index = Index.fromOneBased(Integer.parseInt(args));
        Model model = ModelGenerator.getModelWithPopulatedCanvas();
        Model expectedModel = ModelGenerator.getModelWithPopulatedCanvas();
        expectedModel.getCanvas().setCurrentLayer(index);
        CommandHistory ch = new CommandHistory();
        assertCommandSuccess(
                new LayerSelectCommand(args),
                model,
                ch,
                String.format(LayerSelectCommand.OUTPUT_SUCCESS, index.getOneBased()),
                expectedModel);
    }

    @Test
    void execute_layerInvalidSelect_failure() {
        String args = "invalid";
        Model model = ModelGenerator.getDefaultModel();
        CommandHistory ch = new CommandHistory();
        assertCommandFailure(
                new LayerSelectCommand(args),
                model,
                ch,
                LayerSelectCommand.OUTPUT_FAILURE
        );
    }

    @Test
    void execute_layerSelectWithoutCanvas_failure() {
        String args = "2";
        Index index = Index.fromOneBased(Integer.parseInt(args));
        Model model = ModelGenerator.getModelWithoutCanvas();
        CommandHistory ch = new CommandHistory();
        assertCommandFailure(
                new LayerSelectCommand(args),
                model,
                ch,
                LayerSelectCommand.OUTPUT_MISSING_CANVAS
        );
    }
}
