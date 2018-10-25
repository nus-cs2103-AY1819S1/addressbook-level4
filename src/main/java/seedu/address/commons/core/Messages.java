package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_INDEX_END_OF_IMAGE_LIST = "Exceeded number of images in current directory";
    public static final String MESSAGE_INDEX_EXCEED_MAX_BATCH_SIZE = "The index provided exceeds batch size";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";

    /* Error messages for Google OAuth */
    public static final String MESSAGE_INVALID_ALBUM_REQUESTED = "The album name provided is invalid";
    public static final String MESSAGE_GOOGLE_INVALID_FORMAT = "Please enter a valid google command format.";
    public static final String MESSAGE_LOGIN_FAILURE = "Login unsuccessful";
    public static final String MESSAGE_CONNECTION_FAILURE = "Error connecting to Google Photos, please try again.";

    /* Error messages canvas and layers. */
    public static final String MESSAGE_LAYER_SWAP_INVALID_INDEX = "Please specify valid layer indexes!";
}
