// @@author benedictcss
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;

import javafx.scene.image.Image;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ChangeImageEvent;
import seedu.address.commons.events.ui.FilmReelSelectionChangeEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Selects the image identified by the index number in the current batch.
 */
public class OpenCommand extends Command {

    public static final String COMMAND_WORD = "open";
    public static final int BATCH_SIZE = 10;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Opens the image identified by the index number in the current batch.\n"
            + "Parameters: INDEX (1 - 10)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_OPEN_IMAGE_SUCCESS = "Selected image: %1$s";

    private final Index targetIndex;

    public OpenCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Path> dirImageList = model.getDirectoryImageList();

        if (targetIndex.getZeroBased() >= model.numOfRemainingImagesInDir()) {
            throw new CommandException(Messages.MESSAGE_INDEX_END_OF_IMAGE_LIST);
        } else if (targetIndex.getZeroBased() >= BATCH_SIZE) {
            throw new CommandException(Messages.MESSAGE_INDEX_EXCEED_MAX_BATCH_SIZE);
        }


        Path openedImagePath = dirImageList.get(targetIndex.getZeroBased());

        try {
            String openedImage = openedImagePath.toString();
            FileInputStream fis = new FileInputStream(openedImage);
            Image img = new Image(fis);

            model.updateCurrentOriginalImage(img, openedImagePath);
            EventsCenter.getInstance().post(new FilmReelSelectionChangeEvent(targetIndex.getZeroBased()));
            EventsCenter.getInstance().post(new ChangeImageEvent(img, "preview"));
            EventsCenter.getInstance().post(new ChangeImageEvent(img, "original"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return new CommandResult(String.format(MESSAGE_OPEN_IMAGE_SUCCESS, targetIndex.getOneBased())
                + " of " + Math.min(OpenCommand.BATCH_SIZE, model.getDirectoryImageList().size()) + "\n"
                + "Image opened: " + openedImagePath.getFileName().toString());

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OpenCommand // instanceof handles nulls
                && targetIndex.equals(((OpenCommand) other).targetIndex)); // state check
    }
}
