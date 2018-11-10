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
 * Handles the changing of layer order.
 * Commands are in the format - layer swap [index index].
 * Invalid indexes and invalid operations are handled.
 */

public class LayerSwapCommand extends LayerCommand {
    public static final String TYPE = COMMAND_WORD + " swap";
    public static final String MESSAGE_USAGE = "Usage of layer swap: "
            + "\n- " + TYPE + " [TO] [FROM]: " + "Swaps the order of two distinct layers"
            + "\n\tExample: " + TYPE + " 1 3, swaps the order of the 1st and 3rd layer in the canvas.";

    public static final String OUTPUT_SUCCESS = "Layers %d and %d are now swapped.";
    public static final String OUTPUT_FAILURE = "Invalid index(es) provided!";
    public static final String OUTPUT_ILLEGAL = "Unable to swap layers!";

    private static final Logger logger = LogsCenter.getLogger(LayerSwapCommand.class);


    public LayerSwapCommand(String args) {
        super(args);
    }

    @Override

    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        String[] argumentArray = args.trim().split(" ", 2);

        int to;
        int from;
        Index toIndex;
        Index fromIndex;

        try {
            to = Integer.parseInt(argumentArray[0]);
            from = Integer.parseInt((argumentArray.length > 1) ? argumentArray[1] : "");
            toIndex = Index.fromOneBased(to);
            fromIndex = Index.fromOneBased(from);
            model.swapLayer(toIndex, fromIndex);
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            throw new CommandException(OUTPUT_FAILURE);
        } catch (IllegalOperationException e) {
            throw new CommandException(OUTPUT_ILLEGAL);
        }

        ImageMagickUtil.render(model.getCanvas(), logger, "preview");

        return new CommandResult(String.format(OUTPUT_SUCCESS,
                toIndex.getOneBased(), fromIndex.getOneBased()));
    }
}
