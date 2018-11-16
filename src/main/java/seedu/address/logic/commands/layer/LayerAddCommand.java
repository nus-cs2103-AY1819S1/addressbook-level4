package seedu.address.logic.commands.layer;

//@@author j-lum
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ImageMagickUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PreviewImage;


/**
 * Handles the repositioning of Layers.
 * Commands are in the format - layer add [index].
 * Refer to the open command.
 */

public class LayerAddCommand extends LayerCommand {
    public static final String TYPE = COMMAND_WORD + " add";
    public static final String MESSAGE_USAGE = "Usage of layer add: "
            + "\n- " + TYPE + " [INDEX]: "
            + "Adds the image identified by the index number in the current batch to a new layer."
            + "\n\tExample: " + TYPE + " 2, adds the image with index 2 to the current canvas as the top-most layer.";

    public static final String OUTPUT_SUCCESS = "Layer added!";
    public static final String OUTPUT_FAILURE = "Invalid index provided or initial image not selected!";
    private static final int BATCH_SIZE = 10;

    private static final Logger logger = LogsCenter.getLogger(LayerAddCommand.class);


    public LayerAddCommand(String args) {
        super(args);
    }

    @Override

    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        int i;
        Index index;
        List<Path> dirImageList = model.getDirectoryImageList();
        Image img;

        try {
            i = Integer.parseInt(args);
            index = Index.fromOneBased(i);
            if (index.getZeroBased() >= dirImageList.size() || index.getZeroBased() >= BATCH_SIZE) {
                throw new NumberFormatException(Messages.MESSAGE_INDEX_END_OF_IMAGE_LIST);
            }
            index = Index.fromOneBased(i);
        } catch (NumberFormatException e) {
            throw new CommandException(OUTPUT_FAILURE);
        }

        Path selectedImagePath = dirImageList.get(index.getZeroBased());

        try {
            String selectedImage = selectedImagePath.toString();
            FileInputStream fis = new FileInputStream(selectedImage);
            img = new Image(fis);
            model.addLayer(new PreviewImage(SwingFXUtils.fromFXImage(img, null)));
        } catch (FileNotFoundException e) {
            throw new CommandException(OUTPUT_FAILURE);
        }

        ImageMagickUtil.render(model.getCanvas(), logger, "preview");

        return new CommandResult(OUTPUT_SUCCESS);
    }
}
