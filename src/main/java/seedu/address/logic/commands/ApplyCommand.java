package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Arrays;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ImageMagickUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.transformation.Transformation;


/**
 * @@author lancelotwillow
 * the class to execute the apply command that do the modification of the image
 */
public class ApplyCommand extends Command {

    public static final String COMMAND_WORD = "apply";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Apply a transformation to the image.\n"
            + "Parameters: operationName argument1 argument2 ...\n"
            + "Example: " + COMMAND_WORD + " blur 1x8";
    public static final String MESSAGE_USAGE_RAW = COMMAND_WORD
            + ": Apply a transformation to the image by passing the values directly to ImageMagick.\n"
            + "Parameters: argument1 argument2 ...\n"
            + "Example: " + COMMAND_WORD + " raw +noise gaussian";
    //the path of the json file containing the arguments of the apply command
    public static final URL SINGLE_COMMAND_TEMPLATE_PATH =
            ImageMagickUtil.class.getResource("/imageMagic/commandTemplates");
    private static final Logger logger = LogsCenter.getLogger(ApplyCommand.class);

    private Transformation transformation;
    private boolean isRaw;


    /**
     * the constructor take the path of the JSON file of the detail of the apply operation
     * @param transformation contains the operation to be processed to the image
     */
    public ApplyCommand(Transformation transformation) {
        this.transformation = transformation;
        this.isRaw = false;
    }

    public ApplyCommand(String[] args) {
        this.transformation = new Transformation(Arrays.toString(args), args);
        this.isRaw = true;
    }

    /**
     * build a new processbuilder and initialize witht the commands need to the apply command
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        try {
            BufferedImage modifiedImage = ImageMagickUtil.processImage(model.getCurrentPreviewImagePath(),
                    transformation, isRaw);
            model.addTransformation(isRaw ? new Transformation(transformation.getOperation()) : transformation);
            model.updateCurrentPreviewImage(modifiedImage);
            ImageMagickUtil.render(model.getCanvas(), logger, "preview");
        } catch (Exception e) {
            throw new CommandException(isRaw ? "Invalid operation!" : e.getMessage());
        }
        return new CommandResult("Transformation applied!");
    }

    @Override
    public boolean equals(Object object) {
        ApplyCommand command = (ApplyCommand) object;
        return command == this || transformation.equals(command.transformation);
    }
}
