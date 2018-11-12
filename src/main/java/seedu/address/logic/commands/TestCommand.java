package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ToggleTabEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Navigates tab and current page to testPage
 */
public class TestCommand extends Command {
    public static final String COMMAND_WORD = "test";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " : navigates to test menu\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Successfully navigated to test";

    private String tabToGo;

    public TestCommand(String tabName) {
        tabToGo = tabName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        EventsCenter.getInstance().post(new ToggleTabEvent("test"));
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
