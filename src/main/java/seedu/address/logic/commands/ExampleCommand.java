package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ImageMagickUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.transformation.Transformation;


//@@author lancelotwillow
/**
 * give an example for the convert commadnd
 */
public class ExampleCommand extends Command {

    public static final String COMMAND_WORD = "example";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": give the example for processing transmission for the image.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_EXAMPLE_SUCCESS = "Example: %1$s";

    private final Index targetIndex;
    private Path imagePath;
    private Transformation transformationDone;

    public ExampleCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        try {
            imagePath = model.getCurrentPreviewImagePath();
            BufferedImage modifiedImage = processImage(targetIndex, imagePath);
            model.updateCurrentPreviewImage(modifiedImage);
            return new CommandResult(String.format(MESSAGE_EXAMPLE_SUCCESS, targetIndex.getOneBased()));
        } catch (IOException | InterruptedException | ParseException e) {
            throw new CommandException(e.toString());
        }
    }


    /**
     * the method to handle the image processing
     * @param targetIndex
     * @return bufferedImage of the modifed file
     * @throws IOException
     * @throws InterruptedException
     */
    private BufferedImage processImage(Index targetIndex, Path imagePath)
            throws IOException, InterruptedException, ParseException {
        //create a processbuilder to blur the image
        ProcessBuilder pb;
        switch (targetIndex.getOneBased()) {
        case 1:
            Transformation transformation = new Transformation("blur", "0x8");
            transformationDone = transformation;
            return ImageMagickUtil.processImage(imagePath, transformation);
        case 2:
            transformation = new Transformation("rotate", "50");
            transformationDone = transformation;
            return ImageMagickUtil.processImage(imagePath, transformation);
        case 3:
            transformation = new Transformation("resize", "50%");
            transformationDone = transformation;
            return ImageMagickUtil.processImage(imagePath, transformation);
        case 4:
            transformation = new Transformation("colorspace", "gray");
            transformationDone = transformation;
            return ImageMagickUtil.processImage(imagePath, transformation);
        case 5:
            transformation = new Transformation("@grayblur");
            transformationDone = transformation;
            return ImageMagickUtil.processImage(imagePath, transformation);
        case 6:
            transformation = new Transformation("@blurR");
            transformationDone = transformation;
            return ImageMagickUtil.processImage(imagePath, transformation);
        default:
            transformation = new Transformation("resize", "100%");
            transformationDone = transformation;
            return ImageMagickUtil.processImage(imagePath, transformation);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ExampleCommand
                && targetIndex.equals(((ExampleCommand) other).targetIndex)); // state check
    }
}
