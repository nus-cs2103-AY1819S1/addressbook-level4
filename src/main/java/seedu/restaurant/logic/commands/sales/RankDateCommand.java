package seedu.restaurant.logic.commands.sales;

import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.events.ui.sales.DisplayRankingEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.sales.Date;

//@@author HyperionNKJ
/**
 * Ranks the existing records' dates according to revenue in descending order
 */
public class RankDateCommand extends Command {
    public static final String COMMAND_WORD = "rank-date";

    public static final String COMMAND_ALIAS = "rad";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Generate and display the ranking of dates according "
            + "to total revenue.\n"
            + "Example: " + COMMAND_WORD;

    public static final String DISPLAYING_RANK_DATE_MESSAGE = "Displayed ranking of dates by revenue.";
    public static final String EMPTY_RECORD_LIST_MESSAGE = "Your record list is empty.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        Map<Date, Double> dateRanking = model.rankDateBasedOnRevenue();
        if (dateRanking.isEmpty()) {
            throw new CommandException(String.format(EMPTY_RECORD_LIST_MESSAGE));
        }
        EventsCenter.getInstance().post(new DisplayRankingEvent(getStringRepresentation(dateRanking)));
        return new CommandResult(String.format(DISPLAYING_RANK_DATE_MESSAGE));
    }

    /**
     * Converts every Date key and Double value in the given {@code dateRanking} map to String. Uses a LinkedHashMap
     * to preserve insertion order.
     */
    private Map<String, String> getStringRepresentation(Map<Date, Double> dateRanking) {
        Map<String, String> dateRankingInString = new LinkedHashMap<>();
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        for (Map.Entry<Date, Double> entry : dateRanking.entrySet()) {
            String date = entry.getKey().toString() + " (" + entry.getKey().getDayOfWeek() + ")";
            String revenue = currencyFormatter.format(entry.getValue());
            dateRankingInString.put(date, revenue);
        }
        return dateRankingInString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RankDateCommand); // instanceof handles nulls
    }
}
