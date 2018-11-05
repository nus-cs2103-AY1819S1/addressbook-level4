package seedu.address.logic.commands.canvas;

//@author j-lum
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Handles the saving of a canvas
 */

public class CanvasSaveCommand extends CanvasCommand {
    public static final String TYPE = COMMAND_WORD + " save";
    public static final String MESSAGE_USAGE = "Usage of canvas save: "
            + "\n- " + TYPE + " [filename]: " + "Saves the current canvas as the filename provided"
            + "\n\tExample: " + TYPE + " output.png - Saves the current canvas to output.png to the folder provided in "
            + "user preference.";

    public static final String OUTPUT_SUCCESS = "Output saved to %s.";
    public static final String OUTPUT_INVALID = "Invalid filename entered!";
    public static final String OUTPUT_FAILURE = "Saving failed. Please try again.";

    private static final String FILE_REGEX = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";

    private static final Logger logger = LogsCenter.getLogger(CanvasSaveCommand.class);

    public CanvasSaveCommand(String args) {
        super(args);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        if (args.matches(FILE_REGEX)) {
            try {
                model.saveCanvas(args);
                return new CommandResult(String.format(OUTPUT_SUCCESS, args));
            } catch (Exception e) {
                logger.severe(e.getMessage());
                return new CommandResult(String.format(OUTPUT_FAILURE));
            }
        }
        return new CommandResult(String.format(OUTPUT_INVALID)
                + "\n\n"
                + MESSAGE_USAGE);
    }
}
