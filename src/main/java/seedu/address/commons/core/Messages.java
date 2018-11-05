package seedu.address.commons.core;

import seedu.address.logic.commands.canvas.CanvasAutoResizeCommand;
import seedu.address.logic.commands.canvas.CanvasBgcolorCommand;
import seedu.address.logic.commands.canvas.CanvasSizeCommand;
import seedu.address.logic.commands.google.GoogleDlCommand;
import seedu.address.logic.commands.google.GoogleLsCommand;
import seedu.address.logic.commands.google.GoogleUploadCommand;
import seedu.address.logic.commands.layer.LayerAddCommand;
import seedu.address.logic.commands.layer.LayerDeleteCommand;
import seedu.address.logic.commands.layer.LayerPositionCommand;
import seedu.address.logic.commands.layer.LayerSelectCommand;
import seedu.address.logic.commands.layer.LayerSwapCommand;


/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";

    /* Error message for traversing imageList */
    public static final String MESSAGE_INDEX_END_OF_IMAGE_LIST = "Exceeded number of images in current directory.";
    public static final String MESSAGE_INDEX_EXCEED_MAX_BATCH_SIZE = "The index provided exceeds batch size.";
    public static final String MESSAGE_TOTAL_IMAGES_IN_DIR = "Total number of images in directory: %d\n";
    public static final String MESSAGE_CURRENT_BATCH_IN_IMAGE_LIST = "Currently viewing images from %d to %d\n";
    public static final String MESSAGE_CURRENT_IMAGES_IN_BATCH = "Current number of images in batch: %d\n";
    public static final String MESSAGE_NO_MORE_NEXT_IMAGES = "No more images in the current directory.";
    public static final String MESSAGE_NO_MORE_PREV_IMAGES = "Already at start of list.";

    /* Error messages for Google OAuth */
    public static final String MESSAGE_INVALID_ALBUM_REQUESTED = "The album name provided is invalid";
    public static final String MESSAGE_GOOGLE_INVALID_FORMAT = "Please enter a valid google command format.";

    public static final String MESSAGE_LOGIN_FAILURE = "Login unsuccessful";
    public static final String MESSAGE_CONNECTION_FAILURE = "Error connecting to Google Photos, please try again.";

    public static final String MESSAGE_INVALID_IMAGE_REQUESTED = "The image name provided is invalid.";
    public static final String ENTIRE_GOOGLE_MESSAGE = MESSAGE_GOOGLE_INVALID_FORMAT + "\n---------------------\n\n"
            + GoogleLsCommand.MESSAGE_USAGE + "\n------------------------\n\n"
            + GoogleDlCommand.MESSAGE_USAGE + "\n------------------------\n\n"
            + GoogleUploadCommand.MESSAGE_USAGE;

    /* Error messages ls. */
    public static final String MESSAGE_INVALID_FILE_DIR = "File or directory invalid.";
    public static final String MESSAGE_EMPTY_DIR = "No images or folders to display!";

    /* Error messages for layer and canvas */
    public static final String MESSAGE_LAYER_INVALID_FORMAT = "Please enter a valid layer command.";
    public static final String ENTIRE_LAYER_MESSAGE = MESSAGE_LAYER_INVALID_FORMAT + "\n------------------------\n\n"
            + LayerAddCommand.MESSAGE_USAGE + "\n------------------------\n\n"
            + LayerDeleteCommand.MESSAGE_USAGE + "\n------------------------\n\n"
            + LayerPositionCommand.MESSAGE_USAGE + "\n------------------------\n\n"
            + LayerSelectCommand.MESSAGE_USAGE + "\n------------------------\n\n"
            + LayerSwapCommand.MESSAGE_USAGE + "\n------------------------\n\n";

    public static final String MESSAGE_CANVAS_INVALID_FORMAT = "Please enter a valid canvas command.";
    public static final String ENTIRE_CANVAS_MESSAGE = MESSAGE_CANVAS_INVALID_FORMAT + "\n------------------------\n\n"
            + CanvasAutoResizeCommand.MESSAGE_USAGE + "\n------------------------\n\n"
            + CanvasBgcolorCommand.MESSAGE_USAGE + "\n------------------------\n\n"
            + CanvasSizeCommand.MESSAGE_USAGE;


}
