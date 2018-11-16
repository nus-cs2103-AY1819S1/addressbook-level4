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
    public static final String TYPE = "refresh";
    public static final String FULL_CMD = COMMAND_WORD + " " + TYPE;
    public static final String MESSAGE_USAGE = "Usage of google refresh (requires an internet connection): "
            + "\n- " + FULL_CMD + ": Refreshes image and album list gotten from google ";

    @Override
    public CommandResult execute(Model model, CommandHistory history)
            throws CommandException {
        try {
            requireNonNull(model);

            model.getPhotoHandler(true).refreshLists();
        } catch (ApiException api) {
            throw new CommandException(MESSAGE_CONNECTION_FAILURE + "\n\n" + MESSAGE_USAGE);
        } catch (CommandException coEx) {
            throw coEx;
        } catch (Exception ex) {
            throw new CommandException(FAILURE_MESSAGE + "\n\n" + MESSAGE_USAGE);
        }
        return new CommandResult("Images and albums refreshed!");
    }
}
