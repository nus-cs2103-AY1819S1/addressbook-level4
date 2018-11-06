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
 * Refreshes retrieved albums and images from google
 */
public class GoogleRefreshCommand extends GoogleCommand {
    public static final String FAILURE_MESSAGE = "Failed to refresh";
    private static final String TYPE = COMMAND_WORD + " refresh";
    public static final String MESSAGE_USAGE = "Usage of google refresh (requires an internet connection): "
            + "\n- " + TYPE + " Refreshes image and album list gotten from google ";

    public GoogleRefreshCommand() {
        super();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history)
            throws CommandException {
        try {
            requireNonNull(model);

            model.getPhotoHandler().refreshLists();
        } catch (Exception ex) {
            String message = FAILURE_MESSAGE;
            if (ex instanceof ApiException) {
                message = MESSAGE_CONNECTION_FAILURE;
            }
            throw new CommandException(message);
        }
        return new CommandResult("Images and albums refreshed!");
    }
}
