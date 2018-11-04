package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_LOGIN_FAILURE;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

//@@author chivent

/**
 * Logs in user
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Re-directs user "
            + "to log into Google Photos (requires an internet connection). ";

    private static final String MESSAGE_LOGGED_IN = "Logged in as %s.";

    /**
     * Creates a LoginCommand to allow user to login to Google Account
     */
    public LoginCommand() {

    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        try {
            if (model.getPhotoHandler() == null) {
                throw new Exception();
            }
            return new CommandResult(String.format
                    (MESSAGE_LOGGED_IN, model.getUserLoggedIn()));

        } catch (Exception ex) {
            return new CommandResult(String.format(MESSAGE_LOGIN_FAILURE));
        }
    }
}
