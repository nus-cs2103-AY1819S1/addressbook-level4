package seedu.address.commons.core;

import seedu.address.logic.commands.google.GoogleDlCommand;
import seedu.address.logic.commands.google.GoogleLsCommand;
import seedu.address.logic.commands.google.GoogleUploadCommand;

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

    /* Error messages canvas and layers. */
    public static final String MESSAGE_LAYER_SWAP_INVALID_INDEX = "Please specify valid layer indexes!";
    public static final String MESSAGE_INVALID_IMAGE_REQUESTED = "The image name provided is invalid.";
    public static final String ENTIRE_GOOGLE_MESSAGE = MESSAGE_GOOGLE_INVALID_FORMAT + "\n------------------------\n\n"
            + GoogleLsCommand.MESSAGE_USAGE + "\n------------------------\n\n"
            + GoogleDlCommand.MESSAGE_USAGE + "\n------------------------\n\n"
            + GoogleUploadCommand.MESSAGE_USAGE;


}
