package seedu.address.logic.commands.layer;

//@@author j-lum
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalOperationException;
import seedu.address.commons.util.ImageMagickUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Handles the deleting of layers.
 * Commands are in the format - layer delete [index].
 * Invalid indexes, locked layers are handled.
 */

public class LayerDeleteCommand extends LayerCommand {
    public static final String TYPE = COMMAND_WORD + " delete";
    public static final String MESSAGE_USAGE = "Usage of layer delete: "
            + "\n- " + TYPE + " [INDEX]: " + "Deletes the layer "
            + "\n\tExample: " + TYPE + " 2, deletes the 2nd layer in the canvas.";

    public static final String OUTPUT_SUCCESS = "Layer deleted! Now working on layer index: %d.";
    public static final String OUTPUT_FAILURE = "Invalid layer index provided!";

    private static final Logger logger = LogsCenter.getLogger(LayerDeleteCommand.class);


    public LayerDeleteCommand(String args) {
        super(args);
    }

    @Override

    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (args == null) {
            throw new CommandException(OUTPUT_FAILURE);
        }
        int index;
        Index toRemove;
        Index currentLayer;
        try {
            index = Integer.parseInt(args);
            toRemove = Index.fromOneBased(index);
            currentLayer = model.removeLayer(toRemove);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new CommandException(OUTPUT_FAILURE);
        } catch (IllegalOperationException e) {
            throw new CommandException(e.getMessage());
        }

        ImageMagickUtil.render(model.getCanvas(), logger, "preview");

        return new CommandResult(String.format(OUTPUT_SUCCESS, currentLayer.getOneBased()));
    }
}
