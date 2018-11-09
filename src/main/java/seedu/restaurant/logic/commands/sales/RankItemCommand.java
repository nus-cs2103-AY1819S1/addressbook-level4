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
import seedu.restaurant.model.sales.ItemName;

//@@author HyperionNKJ
/**
 * Ranks the existing records' items according to revenue accumulated in past sales record in descending order
 */
public class RankItemCommand extends Command {
    public static final String COMMAND_WORD = "rank-item";

    public static final String COMMAND_ALIAS = "rai";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Generate and display the ranking of items according "
            + "to total revenue accumulated in past sales records.\n"
            + "Example: " + COMMAND_WORD;

    public static final String DISPLAYING_RANK_ITEM_MESSAGE = "Displayed ranking of items by revenue.";
    public static final String EMPTY_RECORD_LIST_MESSAGE = "Your record list is empty.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        Map<ItemName, Double> itemRanking = model.rankItemBasedOnRevenue();
        if (itemRanking.isEmpty()) {
            throw new CommandException(String.format(EMPTY_RECORD_LIST_MESSAGE));
        }
        EventsCenter.getInstance().post(new DisplayRankingEvent(getStringRepresentation(itemRanking)));
        return new CommandResult(String.format(DISPLAYING_RANK_ITEM_MESSAGE));
    }

    /**
     * Converts every ItemName key and Double value in the given {@code itemRanking} map to String. Uses a LinkedHashMap
     * to preserve insertion order.
     */
    private Map<String, String> getStringRepresentation(Map<ItemName, Double> itemRanking) {
        Map<String, String> itemRankingInString = new LinkedHashMap<>();
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        for (Map.Entry<ItemName, Double> entry : itemRanking.entrySet()) {
            String itemName = entry.getKey().toString();
            String revenue = currencyFormatter.format(entry.getValue());
            itemRankingInString.put(itemName, revenue);
        }
        return itemRankingInString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RankItemCommand); // instanceof handles nulls
    }
}
