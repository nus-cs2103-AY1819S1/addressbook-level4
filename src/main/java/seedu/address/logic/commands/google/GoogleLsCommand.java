package seedu.address.logic.commands.google;

//@@author chivent

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_CONNECTION_FAILURE;

import java.util.List;

import com.google.api.gax.rpc.ApiException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Handles listing of files from Google Photos
 */
public class GoogleLsCommand extends GoogleCommand {

    private static final String TYPE = COMMAND_WORD + " ls";
    public static final String MESSAGE_USAGE = "Usage of google list (requires an internet connection): "
            + "\n- " + TYPE + " Lists all photos in Google Photos, "
            + "takes a longer amount of time depending on number of images in Google Photos."
            + "\n- " + TYPE + " /a: " + "Lists all albums in Google Photos"
            + "\n- " + TYPE + " <ALBUM_NAME>: " + "Lists all photos in specified album from Google Photos"
            + "\n\tExample: " + TYPE + " <Vacation>, usage inclusive of <>";
    public static final String FAILURE_MESSAGE = "Failed to list";

    public GoogleLsCommand(String parameter) {
        super(parameter);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history)
            throws CommandException {
        requireNonNull(model);

        List<String> printTarget;
        StringBuilder toPrint = new StringBuilder();
        try {
            if (parameter.isEmpty()) {
                printTarget = model.getPhotoHandler().returnAllImagesList();

                //Retrieve all names and call
            } else if (parameter.equals("/a")) {
                printTarget = model.getPhotoHandler().returnAllAlbumsList();
            } else {
                parameter = parameter.substring(1, parameter.length() - 1);
                printTarget = model.getPhotoHandler().returnAllImagesinAlbum(parameter);
            }

            for (String name : printTarget) {
                toPrint.append(name + " \n");
            }

            if (toPrint.toString().isEmpty()) {
                if (!parameter.equals("/a")) {
                    toPrint.append("Empty! No images to be displayed");
                }
            }
        } catch (Exception ex) {

            String message = FAILURE_MESSAGE;
            if (ex instanceof ApiException) {
                message = MESSAGE_CONNECTION_FAILURE;
            }
            throw new CommandException(message + "\n\n" + MESSAGE_USAGE);
        }

        return new CommandResult(toPrint.toString());
    }
}
