package seedu.restaurant.logic.commands.sales;

import java.util.Map;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.events.ui.sales.DisplaySalesChartEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.sales.Date;

//@@author HyperionNKJ
/**
 * Display revenue-date sales chart
 */
public class ChartSalesCommand extends Command {
    public static final String COMMAND_WORD = "chart-sales";

    public static final String COMMAND_ALIAS = "cs";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Generate and display the revenue-date sales chart.\n"
            + "Example: " + COMMAND_WORD;

    public static final String DISPLAYING_CHART_MESSAGE = "Displayed sales chart.";
    public static final String EMPTY_RECORD_LIST_MESSAGE = "Your record list is empty.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        Map<Date, Double> salesData = model.getChronologicalSalesData();
        if (salesData.isEmpty()) {
            throw new CommandException(String.format(EMPTY_RECORD_LIST_MESSAGE));
        }
        EventsCenter.getInstance().post(new DisplaySalesChartEvent(salesData));
        return new CommandResult(DISPLAYING_CHART_MESSAGE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChartSalesCommand); // instanceof handles nulls
    }
}
