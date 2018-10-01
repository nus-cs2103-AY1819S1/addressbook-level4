package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.calendar.Month;
import seedu.address.model.calendar.Year;

//@@author GilgameshTC
/**
 * Creates a Calendar with a specified month, year and writes it to hard disk.
 */
public class CreateCalendarCommand extends Command {

    public static final String COMMAND_WORD = "create_calendar";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a calendar. "
            + "Parameters: "
            + PREFIX_MONTH + "MMM "
            + PREFIX_YEAR + "YYYY\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MONTH + "OCT "
            + PREFIX_YEAR + "2018 ";

    public static final String MESSAGE_SUCCESS = "Calendar created: %s";
    public static final String MESSAGE_EXISTING_CALENDAR = "This calendar already exists in Hallper";

    private final Month month;
    private final Year year;

    public CreateCalendarCommand(Month month, Year year) {
        this.month = month;
        this.year = year;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.isExistingCalendar(year, month)) {
            throw new CommandException(MESSAGE_EXISTING_CALENDAR);
        }

        model.createCalendar(year, month);
        return new CommandResult(String.format(MESSAGE_SUCCESS, month + "-" + year + ".ics"));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CreateCalendarCommand // instanceof handles nulls
                && month.equals(((CreateCalendarCommand) other).month)
                && year.equals(((CreateCalendarCommand) other).year));
    }
}
