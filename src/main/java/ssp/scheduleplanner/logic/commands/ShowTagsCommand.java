package ssp.scheduleplanner.logic.commands;

import static java.util.Objects.requireNonNull;

import ssp.scheduleplanner.commons.core.EventsCenter;
import ssp.scheduleplanner.commons.events.ui.ShowTagsEvent;
import ssp.scheduleplanner.logic.CommandHistory;
import ssp.scheduleplanner.model.Model;

/**
 * Shows the tags users have used.
 */
public class ShowTagsCommand extends Command {

    public static final String COMMAND_WORD = "tags";
    public static final String MESSAGE_SUCCESS = "The tags are shown on the left below. ";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        EventsCenter.getInstance().post(new ShowTagsEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
