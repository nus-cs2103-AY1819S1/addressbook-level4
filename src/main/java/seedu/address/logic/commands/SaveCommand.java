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
     * build a new processbuilder and initialize witht the commands need to the convert command
     * @param model {@code Model} which the command should operate on.
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
                throw new IllegalArgumentException("the file already exists");
            }
            BufferedImage savedImage = model.getCurrentPreviewImage().getImage();
            String[] parts = fileName.split("\\.");
            if (parts.length != 2 || !isFormatValid(parts[1])) {
                throw new IllegalArgumentException("the file name is not valid");
            }
            ImageIO.write(savedImage, parts[1], saveFile);
        } catch (IllegalArgumentException | IOException e) {
            throw new CommandException(e.toString());
        }
        return new CommandResult("the image is successfully saved");
    }

    private boolean isFormatValid(String format) {
        return formats.contains(format);
    }
}
