package seedu.address.logic.commands.canvas;

//@author j-lum
import java.util.logging.Logger;

import javafx.embed.swing.SwingFXUtils;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ChangeImageEvent;
import seedu.address.commons.util.ImageMagickUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Handles the toggling of auto-resize.
 */

public class CanvasBgcolourCommand extends CanvasCommand {
    private static final String TYPE = COMMAND_WORD + " bgcolour";
    public static final String MESSAGE_USAGE = "Usage of canvas bgcolour: "
            + "\n- " + TYPE + " [colour]: " + "Changes the background colour to the colour specified."
            + "\n\tExample: " + TYPE + " rgba(0,255,0,0.7) - Changes the background colour to"
            + " lime-green with 70% opacity.";

    private static final String OUTPUT_SUCCESS = "Background colour is now: %s.";
    private static final String OUTPUT_FAILURE = "Invalid colour %s!";

    private static final String HEX_REGEX = "^#(?:[0-9a-f]{3}){1,2}$";
    private static final String VERBOSE_REGEX = "(#([\\da-f]{3}){1,2}|"
            + "(rgb|hsl)a\\((\\d{1,3}%?,\\s?){3}(1|0?\\.\\d+)\\)|(rgb|hsl)\\(\\d{1,3}%?(,\\s?\\d{1,3}%?){2}\\))";
    private static final String NONE_REGEX = "(none)";

    private static final Logger logger = LogsCenter.getLogger(CanvasBgcolourCommand.class);

    public CanvasBgcolourCommand(String args) {
        super(args);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        if (args.matches(HEX_REGEX) || args.matches(VERBOSE_REGEX) || args.matches(NONE_REGEX)) {
            model.getCanvas().setBackgroundColor(args);
            try {
                EventsCenter.getInstance().post(
                        new ChangeImageEvent(
                                SwingFXUtils.toFXImage(
                                        ImageMagickUtil.processCanvas(model.getCanvas()), null), "preview"));
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
            return new CommandResult(String.format(OUTPUT_SUCCESS, args));
        }
        return new CommandResult(String.format(OUTPUT_FAILURE, args));
    }
}
