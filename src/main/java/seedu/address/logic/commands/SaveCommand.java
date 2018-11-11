package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.oracle.tools.packager.UnsupportedPlatformException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.ImageMagickUtil;
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
    public static final String OUTPUT_FAILURE = "Saving file as %s failed!";
    private String fileName;
    private String format;
    private boolean originalFile;

    /**
     * @param fileName is the name of the file saved.
     */
    public SaveCommand(String fileName) {
        this.fileName = fileName;
        String[] parts = fileName.split("\\.");
        format = parts[1];
        originalFile = false;
    }

    public SaveCommand() {
        originalFile = true;
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
            File saveFile;
            if (!originalFile) {
                saveFile = new File(model.getCurrDirectory().toString() + "/" + fileName);
                if (saveFile.exists()) {
                    throw new CommandException(Messages.MESSAGE_DUPLICATED_IMAGE);
                }
            } else {
                saveFile = model.getCurrentOriginalImage().toFile();
                String[] parts = saveFile.getName().split("\\.");
                format = parts[parts.length - 1];
            }
            BufferedImage savedImage = ImageMagickUtil.processCanvas(model.getCanvas());
            fileName = saveFile.getName();
            ImageIO.write(savedImage, format, saveFile);
        } catch (IOException | InterruptedException | UnsupportedPlatformException e) {
            throw new CommandException(String.format(OUTPUT_FAILURE, fileName));
        }
        model.updateEntireImageList();
        return new CommandResult(String.format("%s successfully saved!", fileName));
    }

    @Override
    public boolean equals(Object object) {
        SaveCommand command = (SaveCommand) object;
        return command == this || fileName.equals(command.fileName);
    }

}
