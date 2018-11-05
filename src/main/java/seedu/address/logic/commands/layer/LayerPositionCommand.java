package seedu.address.logic.commands.layer;

//@author j-lum
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ImageMagickUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;



/**
 * Handles the repositioning of Layers.
 */

public class LayerPositionCommand extends LayerCommand {
    public static final String TYPE = COMMAND_WORD + " position";
    public static final String MESSAGE_USAGE = "Usage of layer position: "
            + "\n- " + TYPE + " [POSITION]: " + "Repositions the layer to the provided co-ordinates"
            + "\n\tExample: " + TYPE + " 50x100, sets the layer's top right corner "
            + "to be 50px to the right and 100px from the top";

    public static final String OUTPUT_SUCCESS = "Layer position is now %d by %d.";
    public static final String OUTPUT_FAILURE = "Invalid position provided!";

    private static final Logger logger = LogsCenter.getLogger(LayerPositionCommand.class);


    public LayerPositionCommand(String args) {
        super(args);
    }

    @Override

    public CommandResult execute(Model model, CommandHistory history) {
        if (args == null) {
            return new CommandResult(String.format(OUTPUT_SUCCESS,
                    model.getCanvas().getCurrentLayer().getX(), model.getCanvas().getCurrentLayer().getY()));
        }
        String[] argumentArray = args.trim().split("x", 2);

        int newX;
        int newY;
        try {
            newX = Integer.parseInt(argumentArray[0]);
            newY = Integer.parseInt((argumentArray.length > 1) ? argumentArray[1] : null);
        } catch (NumberFormatException e) {
            return new CommandResult(OUTPUT_FAILURE);
        }
        model.getCanvas().getCurrentLayer().setX(newX);
        model.getCanvas().getCurrentLayer().setY(newY);

        ImageMagickUtil.render(model.getCanvas(), logger, "preview");

        return new CommandResult(String.format(OUTPUT_SUCCESS,
                model.getCanvas().getCurrentLayer().getX(),
                model.getCanvas().getCurrentLayer().getY()));
    }
}
