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
 * Handles the changing and echoing of Canvas size.
 * Commands are in the format - canvas size (height x width).
 * If the optional (height x width) is omitted, the current height and width is displayed instead.
 */

public class CanvasSizeCommand extends CanvasCommand {
    public static final String TYPE = COMMAND_WORD + " size";
    public static final String MESSAGE_USAGE = "Usage of canvas size: "
            + "\n- " + TYPE + " (SIZE): " + "Resizes the canvas if size is provided. Prints the current size otherwise."
            + "\n\tExample: " + TYPE + " 800x600, sets the canvas size to 800px width and 600px height.";

    public static final String OUTPUT_SUCCESS = "Canvas size is now %d by %d.";
    public static final String OUTPUT_FAILURE = "Invalid size provided!";

    private static final Logger logger = LogsCenter.getLogger(CanvasSizeCommand.class);


    public CanvasSizeCommand(String args) {
        super(args);
    }

    @Override

    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (args == null) {
            return new CommandResult(String.format(OUTPUT_SUCCESS,
                    model.getCanvasWidth(), model.getCanvasHeight()));
        }
        String[] argumentArray = args.trim().split("x", 2);

        int newWidth;
        int newHeight;
        try {
            newWidth = Integer.parseInt(argumentArray[0]);
            newHeight = Integer.parseInt((argumentArray.length > 1) ? argumentArray[1] : "");
            if (newWidth <= 0 | newHeight <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            throw new CommandException(OUTPUT_FAILURE
                    + "\n\n"
                    + MESSAGE_USAGE);
        }
        model.setCanvasSize(newHeight, newWidth);

        ImageMagickUtil.render(model.getCanvas(), logger, "preview");

        return new CommandResult(String.format(OUTPUT_SUCCESS,
                model.getCanvasWidth(), model.getCanvasHeight()));
    }
}
