package seedu.address.logic.commands.google;

//@@author chivent
// TODO: Add help message_usage
// TODO: Add test cases

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_CONNECTION_FAILURE;

import java.util.ArrayList;
import java.util.List;

import com.google.api.gax.rpc.ApiException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Handles listing of files in Google Photos
 */
public class GoogleLsCommand extends GoogleCommand {

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
            } else if (parameter.equals("-albums")) {
                printTarget = model.getPhotoHandler().returnAllAlbumsList();
            } else {
                printTarget = model.getPhotoHandler().returnAllImagesinAlbum(parameter);
            }

            for (String name : printTarget) {
                toPrint.append(name + " \t");
            }
        } catch (Exception ex) {

            String message = ex.getMessage();
            if (ex instanceof ApiException) {
                message = MESSAGE_CONNECTION_FAILURE;
            }

            throw new CommandException(message);
        }
        //Save all entries into temporary space and only refresh on new ls/retrieval - for PhotoHandler
        return new CommandResult(toPrint.toString());
    }
}
