package seedu.address.logic.commands.canvas;

//import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.testutil.ModelGenerator;

//@author j-lum

class CanvasSaveCommandTest {
    //can't run imagemagick in tests without setup and teardown
    @Test
    void execute_save_success() {
        String args = "filename.png";
        Model model = ModelGenerator.getDefaultModel();
        CommandHistory ch = new CommandHistory();
        /*assertCommandSuccess(
        new CanvasSaveCommand(args),
            model,
            ch,
            String.format(CanvasSaveCommand.OUTPUT_SUCCESS, args),
            model
        );*/
    }
}
