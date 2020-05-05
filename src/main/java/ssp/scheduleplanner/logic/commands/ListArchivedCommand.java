package ssp.scheduleplanner.logic.commands;

import static java.util.Objects.requireNonNull;

import static ssp.scheduleplanner.model.Model.PREDICATE_SHOW_ALL_ARCHIVED_TASKS;

import ssp.scheduleplanner.commons.core.EventsCenter;
import ssp.scheduleplanner.commons.events.ui.ChangeViewEvent;
import ssp.scheduleplanner.logic.CommandHistory;
import ssp.scheduleplanner.model.Model;

/**
 * Lists all tasks in the Schedule Planner to the user.
 */
public class ListArchivedCommand extends Command {

    public static final String COMMAND_WORD = "listarchived";

    public static final String MESSAGE_SUCCESS = "Listed all archived tasks";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredArchivedTaskList(PREDICATE_SHOW_ALL_ARCHIVED_TASKS);
        EventsCenter.getInstance().post(new ChangeViewEvent(ChangeViewEvent.View.ARCHIVE));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
