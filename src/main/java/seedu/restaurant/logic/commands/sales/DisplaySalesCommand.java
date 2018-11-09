package seedu.restaurant.logic.commands.sales;

import static java.util.Objects.requireNonNull;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.events.ui.sales.DisplaySalesReportEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.sales.Date;
import seedu.restaurant.model.sales.SalesReport;

//@@author HyperionNKJ
/**
 * Display sales report of a specific date
 */
public class DisplaySalesCommand extends Command {
    public static final String COMMAND_WORD = "display-sales";

    public static final String COMMAND_ALIAS = "dis";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Generate and display the sales report of a specified "
            + "date.\n"
            + "Parameters: DATE (must be in DD-MM-YYYY format) "
            + "Example: " + COMMAND_WORD + " 25-09-2018";

    public static final String DISPLAYING_REPORT_MESSAGE = "Displayed sales report dated: %1$s";
    public static final String NO_SUCH_DATE_MESSAGE = "No record with date %1$s was found.";

    private final Date date;

    /**
     * @param date of sales report to be displayed
     */
    public DisplaySalesCommand(Date date) {
        requireNonNull(date);
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        SalesReport salesReport = model.getSalesReport(date);
        if (salesReport.getRecords().isEmpty()) {
            throw new CommandException(String.format(NO_SUCH_DATE_MESSAGE, date.toString()));
        }
        EventsCenter.getInstance().post(new DisplaySalesReportEvent(salesReport));
        return new CommandResult(String.format(DISPLAYING_REPORT_MESSAGE, date.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DisplaySalesCommand // instanceof handles nulls
                    && date.equals(((DisplaySalesCommand) other).date));
    }
}
