package seedu.address.logic.commands;

//@author j-lum
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ImageMagickUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.transformation.Transformation;

/**
 * Handles the repositioning of Layers.
 */

public class RawCommand extends Command {
    public static final String COMMAND_WORD = "raw";
    public static final String MESSAGE_USAGE = "Usage of " + COMMAND_WORD + " "
            + "\n- " + COMMAND_WORD + " [COMMAND]: "
            + "An advanced feature that turns off safety features to allow access to powerful commands."
            + "\nHere be dragons!"
            + "\nRefer to the official ImageMagick documentation at "
            + "https://www.imagemagick.org/script/command-line-processing.php#option to find out more."
            + "\n\tExample: " + COMMAND_WORD + " +noise Gaussian"
            + "\n\tExample: " + COMMAND_WORD + " -ordered-dither c7x7w";

    public static final String OUTPUT_SUCCESS = "Operator performed!";
    public static final String OUTPUT_FAILURE = "Invalid operation!";
    public static final String OUTPUT_MISSING = "You must provide an operation to perform!";


    private static final Logger logger = LogsCenter.getLogger(RawCommand.class);

    private String args;
    private Transformation transformation;

    public RawCommand(String args) {
        this.args = args;
        transformation = new Transformation(args);
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        if (args == null || args.isEmpty()) {
            return new CommandResult(OUTPUT_MISSING);
        }
        try {
            BufferedImage modifiedImage = ImageMagickUtil.processRawImage(model.getCurrentPreviewImagePath(), args);
            model.updateCurrentPreviewImage(modifiedImage);
            model.addTransformation(transformation);
            ImageMagickUtil.render(model.getCanvas(), logger, "preview");
        } catch (Exception e) {
            return new CommandResult(OUTPUT_FAILURE);
        }
        return new CommandResult(String.format(OUTPUT_SUCCESS));
    }
}
