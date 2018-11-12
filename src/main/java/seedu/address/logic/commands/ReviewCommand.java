package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ToggleTabEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Navigates to the Review page
 */
public class ReviewCommand extends Command {
    public static final String COMMAND_WORD = "review";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " : navigates to review page\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Successfully navigated to review page";

    private String tabToGo;

    public ReviewCommand(String tabName) {
        tabToGo = tabName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        EventsCenter.getInstance().post(new ToggleTabEvent("review"));
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
