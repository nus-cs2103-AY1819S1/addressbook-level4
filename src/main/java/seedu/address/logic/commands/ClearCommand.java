package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.TaskManager;

/**
 * Clears the task manager.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Task Manager has been cleared!";


    @Override
    public CommandResult executePrimitive(Model model, CommandHistory history) {
        requireNonNull(model);
        TaskManager clearedTaskManager = new TaskManager();
        clearedTaskManager.setAchievements(model.getAchievementRecord());
        model.resetData(clearedTaskManager);
        model.commitTaskManager();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
