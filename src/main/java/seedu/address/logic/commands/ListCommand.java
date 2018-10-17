package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.ListCommandParser.PREFIX_DUE_BEFORE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.function.Predicate;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.task.DueDateIsBeforeTodayPredicate;
import seedu.address.model.task.Task;

/**
 * Lists all tasks in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    private final Predicate<Task> predicate;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists tasks. "
            + "Parameters: "
            + "[" + PREFIX_DUE_BEFORE + "DUE BEFORE " + "]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DUE_BEFORE + "today";

    public enum ListFilter {
            DUE_TODAY;
    }

    public ListCommand() { this.predicate = PREDICATE_SHOW_ALL_TASKS; }

    public ListCommand(ListFilter listFilter) {
        switch (listFilter) {
            case DUE_TODAY:
                this.predicate = new DueDateIsBeforeTodayPredicate();
                break;
            default:
                this.predicate = PREDICATE_SHOW_ALL_TASKS;
                break;
        }
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredTaskList(this.predicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
