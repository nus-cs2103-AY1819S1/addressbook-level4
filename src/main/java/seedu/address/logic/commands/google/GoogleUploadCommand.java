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

    public static final String MESSAGE_SUCCESS = "%s uploaded to Google Photos";
    public static final String MESSAGE_FAILURE = "%s failed to upload.";

    private static final String TYPE = COMMAND_WORD + " ul";
    public static final String MESSAGE_USAGE = "Usage of google upload (requires an internet connection): "
            + "\n- " + TYPE + " <IMAGE_NAME>: Uploads specified image to Google Photos"
            + "\n\tExample: " + TYPE + " <mountain.png>, usage inclusive of <> "
            + "\n- " + TYPE + " all: Uploads all images in current directory to Google Photos, "
            + "takes a longer amount of time depending on number of images to upload. \n\n"
            + "!!NOTE: All photos uploaded from Piconso can be found in album 'Piconso Uploads'";

    public GoogleUploadCommand(String parameter) {
        super(parameter);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        String org = parameter;
        try {
            if (parameter.startsWith("all")) {
                model.getPhotoHandler().uploadAll(model.getCurrDirectory().toString());
            } else {
                parameter = parameter.substring(1, parameter.length() - 1);
                model.getPhotoHandler().uploadImage(parameter, model.getCurrDirectory().toString());
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
        return new CommandResult(String.format(MESSAGE_SUCCESS, parameter));
    }
}
