package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.BackToHomeEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Logout the user from FoodZoom.
 */
public class HomeCommand extends Command {

    public static final String COMMAND_WORD = "/home";

    public static final String MESSAGE_SUCCESS = "Back to Home Page! View your dashboard below.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Returns back to home screen with dashboard. ";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        EventsCenter.getInstance().post(new BackToHomeEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
