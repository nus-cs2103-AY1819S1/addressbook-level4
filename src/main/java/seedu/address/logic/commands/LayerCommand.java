// @@author j-lum
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ChangeImageEvent;
import seedu.address.commons.util.ImageMagickUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PreviewImage;

/**
 * Selects the image identified by the index number in the current batch.
 */
public class LayerCommand extends Command {

    public static final String COMMAND_WORD = "layer";
    public static final String ADD_SUBCOMMAND_WORD = "add";
    public static final int BATCH_SIZE = 10;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds the image identified by the index number in the current batch to a new layer.\n"
            + "Parameters: INDEX (1 - 10)\n"
            + "Example: " + COMMAND_WORD + " " + ADD_SUBCOMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_IMAGE_SUCCESS = "Added image: %1$s to a new layer.";

    private final Index targetIndex;

    public LayerCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Path> dirImageList = model.getDirectoryImageList();
        Image img = new Image("https://via.placeholder.com/500x500");

        if (targetIndex.getZeroBased() >= dirImageList.size()) {
            throw new CommandException(Messages.MESSAGE_INDEX_END_OF_IMAGE_LIST);
        } else if (targetIndex.getZeroBased() >= BATCH_SIZE) {
            throw new CommandException(Messages.MESSAGE_INDEX_EXCEED_MAX_BATCH_SIZE);
        }

        Path selectedImagePath = dirImageList.get(targetIndex.getZeroBased());

        try {
            String selectedImage = selectedImagePath.toString();
            FileInputStream fis = new FileInputStream(selectedImage);
            img = new Image(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            model.addLayer(new PreviewImage(SwingFXUtils.fromFXImage(img, null)));
            EventsCenter.getInstance().post(
                    new ChangeImageEvent(
                            SwingFXUtils.toFXImage(ImageMagickUtil.processCanvas(model.getCanvas()), null), "preview"));
        } catch (Exception e) {
            System.out.println(e);
        }


        return new CommandResult(String.format(MESSAGE_SELECT_IMAGE_SUCCESS, targetIndex.getOneBased())
                + " of " + Math.min(SelectCommand.BATCH_SIZE, model.getDirectoryImageList().size()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LayerCommand // instanceof handles nulls
                && targetIndex.equals(((LayerCommand) other).targetIndex)); // state check
    }
}
