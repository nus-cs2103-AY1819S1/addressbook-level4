package seedu.address.logic.commands.google;

//@@author chivent

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_CONNECTION_FAILURE;

import com.google.api.gax.rpc.ApiException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Handles download and upload (to be added) of files to Google Photos
 */
public class GoogleDlCommand extends GoogleCommand {

    public static final String MESSAGE_SUCCESS = "%s downloaded into %s";
    public static final String MESSAGE_FAILURE = "%s failed to download.";

    private static final String TYPE = COMMAND_WORD + " dl";
    public static final String MESSAGE_USAGE = "Usage of google download (requires an internet connection): "
            + "\n- " + TYPE + " /i<IMAGE_NAME>: " + "Downloads specified image from Google Photos"
            + "\n\tExample: " + TYPE + " /i<mountain.png>, usage inclusive of <> "
            + "\n- " + TYPE + " /a<ALBUM_NAME> /i<IMAGE_NAME>: " + "Downloads specified image"
            + "from specified album in Google sPhotos"
            + "\n\tExample: " + TYPE + " /a<Vacation> /i<mountain.png>, usage inclusive of <>"
            + "\n- " + TYPE + " /a<ALBUM_NAME>: " + "Downloads all images from specified album, "
            + "takes a longer time depending on number of images"
            + "\n\tExample: " + TYPE + " /a<Vacation>, usage inclusive of <> \n\n"
            + "!!WARNING: Any files with duplicate naming existing in the folder WILL be replaced";

    /**
     * Index to start from when parsing a image or album name
     */
    private static final int START_INDEX = 3;

    public GoogleDlCommand(String parameter) {
        super(parameter);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        String org = parameter;
        try {
            String currDir = model.getCurrDirectory().toString();

            if (parameter.startsWith("/a")) {
                String[] params = parameter.trim().split(" /i<", 2);
                if (params.length > 1) {
                    String albumName = params[0].substring(START_INDEX, params[0].length() - 1);

                    // get image name
                    parameter = params[1].substring(0, params[1].length() - 1);
                    model.getPhotoHandler().downloadAlbumImage(albumName, parameter, currDir);
                } else {
                    parameter = parameter.substring(START_INDEX, parameter.length() - 1);
                    model.getPhotoHandler().downloadWholeAlbum(parameter, currDir);
                }

            } else if (parameter.startsWith("/i")) {
                parameter = parameter.substring(START_INDEX, parameter.length() - 1);
                model.getPhotoHandler().downloadImage(parameter, currDir);
            } else {
                throw new Exception(parameter);
            }

            model.updateEntireImageList();

        } catch (Exception ex) {

            if (ex instanceof ApiException) {
                throw new CommandException(MESSAGE_CONNECTION_FAILURE);
            }

            if (parameter.isEmpty()) {
                parameter = org;
            }
            throw new CommandException(String.format(MESSAGE_FAILURE, parameter) + "\n\n" + MESSAGE_USAGE);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, parameter, model.getCurrDirectory().toString()));
    }
}
