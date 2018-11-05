package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_CONNECTION_FAILURE;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

//@@author chivent

/**
 * Logs in user
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Re-directs user "
            + "to log into Google Photos (requires an internet connection). ";

    protected static final String MESSAGE_LAUNCHED = "You will be re-directed to a login window shortly...";
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
            if (model.getUserLoggedIn() == null) {
                CompletableFuture.supplyAsync(() -> {
                    try {
                        EventsCenter.getInstance().post(new NewResultAvailableEvent(this.initPhotoHandler(model)));
                        return true;
                    } catch (CommandException e) {
                        EventsCenter.getInstance().post(new NewResultAvailableEvent(MESSAGE_CONNECTION_FAILURE));
                        return false;
                    }
                }).completeOnTimeout(true, 3, TimeUnit.MINUTES);
                return new CommandResult(MESSAGE_LAUNCHED);
            } else {
                return new CommandResult(String.format(MESSAGE_LOGGED_IN, model.getUserLoggedIn()));
            }
        } catch (Exception ex) {
            return new CommandResult(String.format(MESSAGE_CONNECTION_FAILURE));
        }
    }

    /**
     * Method to launch creation/checking of photoHandler
     * @param model model to be based upon
     * @return feedback to display in resultsdisplay
     */
    private String initPhotoHandler(Model model) throws CommandException {
        if (model.getPhotoHandler() == null) {
            return MESSAGE_CONNECTION_FAILURE;
        }
        return String.format(MESSAGE_LOGGED_IN, model.getUserLoggedIn());
    }
}
