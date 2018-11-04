package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.OverviewPanelChangedEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Opens the overview panel.
 */
public class OverviewCommand extends Command {
    public static final String COMMAND_WORD = "overview";

    public static final String MESSAGE_OVERVIEW_EVENT_SUCCESS = "Showing overview panel.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        EventsCenter.getInstance().post(new OverviewPanelChangedEvent());
        return new CommandResult(String.format(MESSAGE_OVERVIEW_EVENT_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof OverviewCommand; // instanceof handles nulls
    }
}
