package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * the command to save the current preview
 *
 * @author lancelotwillow
 */
public class SaveCommand extends Command {
    public static final String COMMAND_WORD = "save";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": save the current preview image with the name specified.\n"
            + "Parameters: fileName\n"
            + "Example: " + COMMAND_WORD + " modified.png";
    private String fileName;
    private ArrayList<String> formats = new ArrayList<>(
            Arrays.asList("jpg", "JPG", "jpeg", "JPEG", "tiff", "TIFF", "gif", "GIF", "png", "PNG"));

    /**
     * @param fileName is the name of the file saved.
     */
    public SaveCommand(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Build a new processbuilder and initialize with the commands need to the convert command
     *
     * @param model   {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        try {
            Path currentDir = model.getCurrDirectory();
            File saveFile = new File(currentDir.toString() + "/" + fileName);
            if (saveFile.exists()) {
                return new CommandResult("An image with the same name already exists in this directory! \n\n"
                        + MESSAGE_USAGE);
            }
            BufferedImage savedImage = model.getCurrentPreviewImage().getImage();
            String[] parts = fileName.split("\\.");
            if (parts.length != 2 || !isFormatValid(parts[1])) {
                return new CommandResult("Image name/format provided invalid. \n\n" + MESSAGE_USAGE);
            }
            ImageIO.write(savedImage, parts[1], saveFile);
        } catch (IllegalArgumentException | IOException e) {
            throw new CommandException(e.toString());
        }
        model.updateEntireImageList();
        return new CommandResult(String.format("%s successfully saved!", fileName));
    }

    private boolean isFormatValid(String format) {
        return formats.contains(format);
    }
}
