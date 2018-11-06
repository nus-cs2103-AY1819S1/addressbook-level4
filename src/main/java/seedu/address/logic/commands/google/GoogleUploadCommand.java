package seedu.address.logic.commands.google;

//@@author chivent

import static java.util.Objects.requireNonNull;

import com.google.api.gax.rpc.ApiException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Handles download and upload (to be added) of files to Google Photos
 */
public class GoogleUploadCommand extends GoogleCommand {
    public static final String TYPE = "ul";
    public static final String FULL_CMD = COMMAND_WORD + " " + TYPE;
    public static final String MESSAGE_USAGE = "Usage of google upload (requires an internet connection): "
            + "\n- " + FULL_CMD + " <IMAGE_NAME>: Uploads specified image to Google Photos"
            + "\n\tExample: " + FULL_CMD + " <mountain.png>, usage inclusive of <> "
            + "\n- " + FULL_CMD + " all: Uploads all images in current directory to Google Photos, "
            + "takes a longer amount of time depending on number of images to upload. \n\n"
            + "!!NOTE: All photos uploaded from Piconso can be found in album 'Piconso Uploads'";

    public static final String MESSAGE_ALL_DUPLICATE = "Failure to upload. %s already exist(s) in Google Photos.";
    private static final String ADVICE = "\n\nYou'll need to use `g refresh` before you can see it by ls!";
    public static final String MESSAGE_SUCCESS = "Successfully uploaded to Google Photos: \n%s" + ADVICE;
    public static final String MESSAGE_DUPLICATE = "Upload success. Some of the images in the selected folder are "
            + "duplicates, only the following were uploaded: \n%s" + ADVICE;
    public static final String MESSAGE_FAILURE = "%s failed to upload." + "\n\n" + MESSAGE_USAGE;

    public GoogleUploadCommand(String parameter) {
        super(parameter);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        String org = parameter;
        String message;
        try {
            if (parameter.startsWith("all")) {
                message = model.getPhotoHandler().uploadAll(model.getCurrDirectory().toString());
                return returnUploadMessage(message);
            } else {
                parameter = parameter.substring(1, parameter.length() - 1);
                message = model.getPhotoHandler().uploadImage(parameter, model.getCurrDirectory().toString());
                return returnUploadMessage(message);
            }
        } catch (Exception ex) {

            if (ex instanceof ApiException) {
                throw new CommandException(ex.getMessage());
            }

            if (parameter.isEmpty()) {
                parameter = org;
            }
            throw new CommandException(String.format(MESSAGE_FAILURE, parameter) + "\n\n" + MESSAGE_USAGE);
        }
    }

    /**
     * Parses and prepares a message to return to result display for uploading
     * @param uploaded duplicate list
     * @return result to return to display
     */
    public CommandResult returnUploadMessage(String uploaded) {
        String allImages = "All images in directory";
        if (uploaded.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_ALL_DUPLICATE, allImages));
        } else if (uploaded.substring(0, 4).equals(".all")) {
            uploaded = uploaded.substring(4);
            return new CommandResult(String.format(MESSAGE_SUCCESS, uploaded));
        } else {
            return new CommandResult(String.format(MESSAGE_DUPLICATE, uploaded));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GoogleUploadCommand)) { //this handles null as well.
            return false;
        }
        return ((GoogleUploadCommand) other).parameter.equals(this.parameter);
    }
}
