package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.LogoutStatusEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.google.PhotosLibraryClientFactory;

//@@author chivent

/**
 * Logs in user
 */
public class LogoutCommand extends Command {

    public static final String COMMAND_WORD = "logout";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Log out from Google Photos";

    protected static final String MESSAGE_LOGGED_OUT = "Successfully logged out.";
    protected static final String MESSAGE_NONE = "There is no account logged into.";
    private static final String MESSAGE_ERROR = "Error while attempting to logout.";

    /**
     * Creates a LogoutCommand to allow user to login to Google Account
     */
    public LogoutCommand() {

    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        model.setPhotoHandler(null);

        try {
            if (PhotosLibraryClientFactory.logoutUserIfPossible()) {
                EventsCenter.getInstance().post(new LogoutStatusEvent());
                return new CommandResult(MESSAGE_LOGGED_OUT);
            } else {
                return new CommandResult(MESSAGE_NONE);
            }
        } catch (Exception ex) {
            return new CommandResult(MESSAGE_ERROR);
        }
    }
}
