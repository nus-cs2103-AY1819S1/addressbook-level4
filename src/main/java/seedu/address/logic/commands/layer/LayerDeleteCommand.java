package seedu.address.logic.commands.layer;

//@author j-lum
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ImageMagickUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;



/**
 * Handles the repositioning of Layers.
 */

public class LayerDeleteCommand extends LayerCommand {
    private static final String TYPE = COMMAND_WORD + " delete";
    public static final String MESSAGE_USAGE = "Usage of layer delete: "
            + "\n- " + TYPE + " [INDEX]: " + "Deletes the layer "
            + "\n\tExample: " + TYPE + " 2, deletes the 2nd layer in the canvas.";

    private static final String OUTPUT_SUCCESS = "Now working on layer %d.";
    private static final String OUTPUT_FAILURE = "Invalid layer index provided!";

    private static final Logger logger = LogsCenter.getLogger(LayerDeleteCommand.class);


    public LayerDeleteCommand(String args) {
        super(args);
    }

    @Override

    public CommandResult execute(Model model, CommandHistory history) {
        if (args == null) {
            return new CommandResult(String.format(OUTPUT_FAILURE));
        }
        int index;
        try {
            index = Integer.parseInt(args);
            if (index < 0 | index > model.getCanvas().getLayers().size()) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            return new CommandResult(OUTPUT_FAILURE);
        }
        model.getCanvas().setCurrentLayer(Index.fromOneBased(index));

        ImageMagickUtil.render(model.getCanvas(), logger, "preview");

        return new CommandResult(String.format(OUTPUT_SUCCESS,
                model.getCanvas().getWidth(), model.getCanvas().getHeight()));
    }
}
