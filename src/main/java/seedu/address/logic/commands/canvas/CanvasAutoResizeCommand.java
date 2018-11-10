package seedu.address.logic.commands.canvas;

//@@author j-lum
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ImageMagickUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;



/**
 * Handles the toggling of canvas auto-resize.
 * Commands are in the format : canvas auto-resize [ON/OFF] (case-insensitive).
 */

public class CanvasAutoResizeCommand extends CanvasCommand {
    public static final String TYPE = COMMAND_WORD + " auto";

    public static final String MESSAGE_USAGE = "Usage of canvas auto-resize: "
            + "\n- " + TYPE + " [ON/OFF]: " + "Turns auto-resize on or off."
            + "\n\tExample: " + TYPE + " off, turns auto-resize off.";

    public static final String OUTPUT_SUCCESS = "Auto-resize has been turned %s.";
    public static final String OUTPUT_FAILURE = "Invalid operation %s!";

    private static final Logger logger = LogsCenter.getLogger(CanvasAutoResizeCommand.class);

    public CanvasAutoResizeCommand(String args) {
        super(args);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (args.equalsIgnoreCase("on")) {
            model.setCanvasAuto(true);
            ImageMagickUtil.render(model.getCanvas(), logger, "preview");
            return new CommandResult(String.format(OUTPUT_SUCCESS, args.toLowerCase()));
        }
        if (args.equalsIgnoreCase("off")) {
            model.setCanvasAuto(false);
            ImageMagickUtil.render(model.getCanvas(), logger, "preview");
            return new CommandResult(String.format(OUTPUT_SUCCESS, args.toLowerCase()));
        }
        throw new CommandException(String.format(OUTPUT_FAILURE, args.toLowerCase())
                + "\n\n"
                + MESSAGE_USAGE
        );
    }
}
