package seedu.address.logic.commands.timetable;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.NotLoggedInCommandException;
import seedu.address.model.Model;
import seedu.address.model.timetable.Week;

/**
 * Lists the available times to eat for the logged in user during a specified week number.
 */
public class ListScheduleCommand extends Command {
    public static final String COMMAND_WORD = "listScheduleForWeek";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists your schedule for the week."
            + "Parameters: "
            + PREFIX_WEEK + "NUS WEEK \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_WEEK + "recess";

    public static final String MESSAGE_SUCCESS = "Listed your week: %1$s schedule below.";

    public static final String UI_PAGE_HEADER = "These are your free dates for the Week: %1$s";

    private final Week weekNumber;

    /**
     * Creates a ListScheduleCommand view the free time on the logged in user's timetable.
     */
    public ListScheduleCommand(Week weekNumber) {
        requireNonNull(weekNumber);
        this.weekNumber = weekNumber;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.isCurrentlyLoggedIn()) {
            throw new NotLoggedInCommandException(COMMAND_WORD);
        }

        model.displayUserWeekSchedule(String.format(UI_PAGE_HEADER, weekNumber), weekNumber);

        return new CommandResult(String.format(MESSAGE_SUCCESS, weekNumber));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListScheduleCommand // instanceof handles nulls
                && weekNumber.equals(((ListScheduleCommand) other).weekNumber));
    }
}
