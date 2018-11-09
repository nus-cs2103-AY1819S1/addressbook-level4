package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.ListCommandParser.DUE_END_OF_MONTH_OPTION;
import static seedu.address.logic.parser.ListCommandParser.DUE_END_OF_WEEK_OPTION;
import static seedu.address.logic.parser.ListCommandParser.DUE_TODAY_OPTION;
import static seedu.address.logic.parser.ListCommandParser.NOT_BLOCKED_OPTION;
import static seedu.address.logic.parser.ListCommandParser.PREFIX_FILTER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.function.Function;
import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.task.DueDateIsBeforeEndOfMonthPredicate;
import seedu.address.model.task.DueDateIsBeforeEndOfWeekPredicate;
import seedu.address.model.task.DueDateIsBeforeTodayPredicate;
import seedu.address.model.task.IsNotBlockedPredicate;
import seedu.address.model.task.Task;



/**
 * Lists all tasks in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists tasks. "
            + "Parameters: "
            + "[" + PREFIX_FILTER + "FILTER OPTION " + "]\n"
            + "Allowed values for \"FILTER OPTION\": \n"
            + DUE_TODAY_OPTION + " - tasks due before the end of the day \n"
            + DUE_END_OF_WEEK_OPTION + " - tasks due before the end of the week \n"
            + DUE_END_OF_MONTH_OPTION + " - tasks due before the end of the month \n"
            + NOT_BLOCKED_OPTION + " - tasks with no uncompleted dependencies \n"
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FILTER + "today";

    private final Function<Model, Predicate<Task>> predicateGenerator;

    /**
     * Denotes the kind of filters List supports.
     */
    public enum ListFilter {
            DUE_TODAY,
            DUE_END_OF_WEEK,
            DUE_END_OF_MONTH,
            NOT_BLOCKED;
    }

    public ListCommand() {
        this.predicateGenerator = (m) -> PREDICATE_SHOW_ALL_TASKS;
    }

    public ListCommand(ListFilter listFilter) {
        switch (listFilter) {
        case DUE_TODAY:
            this.predicateGenerator = (m) -> new DueDateIsBeforeTodayPredicate();
            break;
        case DUE_END_OF_WEEK:
            this.predicateGenerator = (m) -> new DueDateIsBeforeEndOfWeekPredicate();
            break;
        case DUE_END_OF_MONTH:
            this.predicateGenerator = (m) -> new DueDateIsBeforeEndOfMonthPredicate();
            break;
        case NOT_BLOCKED:
            this.predicateGenerator = (m) -> new IsNotBlockedPredicate(m);
            break;
        default:
            this.predicateGenerator = (m) -> PREDICATE_SHOW_ALL_TASKS;
            break;
        }
    }


    @Override
    public CommandResult executePrimitive(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredTaskList(this.predicateGenerator.apply(model));
        return model.getFilteredTaskList().size() == model.getTaskManager().getTaskList().size()
                ? new CommandResult(MESSAGE_SUCCESS)
                : new CommandResult(String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW,
                    model.getFilteredTaskList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                && predicateGenerator.equals(((ListCommand) other).predicateGenerator)); // state check
    }
}
