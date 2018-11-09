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
 * Handles the changing of background color.
 * Commands are in the format : canvas bgcolor [`none` | hex color code | rgba/hsla color code].
 */

public class CanvasBgcolorCommand extends CanvasCommand {
    public static final String TYPE = COMMAND_WORD + " bgcolor";
    public static final String MESSAGE_USAGE = "Usage of canvas bgcolor: "
            + "\n- " + TYPE + " [color]: " + "Changes the background color to the color specified."
            + "\n\tExample: " + TYPE + " rgba(0,255,0,0.7) - Changes the background color to"
            + " lime-green with 70% opacity.";

    public static final String OUTPUT_SUCCESS = "Background color is now: %s.";
    public static final String OUTPUT_FAILURE = "Invalid colour %s!";

    private static final String HEX_REGEX = "^#(?:[0-9a-f]{3}){1,2}$";
    private static final String VERBOSE_REGEX = "(#([\\da-f]{3}){1,2}|"
            + "(rgb|hsl)a\\((\\d{1,3}%?,\\s?){3}(1|0?\\.\\d+)\\)|(rgb|hsl)\\(\\d{1,3}%?(,\\s?\\d{1,3}%?){2}\\))";
    private static final String NONE_REGEX = "(none)";

    private static final Logger logger = LogsCenter.getLogger(CanvasBgcolorCommand.class);

    public CanvasBgcolorCommand(String args) {
        super(args);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (args.matches(HEX_REGEX) || args.matches(VERBOSE_REGEX) || args.matches(NONE_REGEX)) {
            model.getCanvas().setBackgroundColor(args);
            ImageMagickUtil.render(model.getCanvas(), logger, "preview");
            return new CommandResult(String.format(OUTPUT_SUCCESS, args));
        }
        throw new CommandException(String.format(OUTPUT_FAILURE, args)
                + "\n\n"
                + MESSAGE_USAGE);
    }
}
