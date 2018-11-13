package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.SortPanelViewEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


/**
 * Changes the UI view.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Sorts the current view if applicable (only for tables).\n"
        + "Parameters: SORT_ORDER (\"a/A\" for ascending, and \"d/D\" for descending)\n"
        + "[SORT_COLUMN_INDICES] (starting from 1, can sort by multiple columns).\n"
        + "Example: " + COMMAND_WORD + " a 123";

    public static final String ORDER_FLAG_REGEX = "[a|A|d|D]";
    public static final String ASCENDING_ORDER_FLAG = "a";
    public static final String DESCENDING_ORDER_FLAG = "d";

    public static final String MESSAGE_VIEW_SORTED_SUCCESS = "Sort attempted on current view. "
        + "Note that this will have no effect if the view is not sortable.";

    private final SortOrder order;
    private final int[] colIdx;

    public SortCommand(SortOrder order, int... colIdx) {
        requireNonNull(order);
        requireNonNull(colIdx);

        this.order = order;
        this.colIdx = colIdx;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        EventsCenter.getInstance().post(new SortPanelViewEvent(order, colIdx));

        return new CommandResult(MESSAGE_VIEW_SORTED_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SortCommand // instanceof handles nulls
                && Arrays.equals(colIdx, ((SortCommand) other).colIdx)
                && order.equals(((SortCommand) other).order)); // state check
    }

    /**
     * Enumeration class to hold constants
     * relating to sorting order.
     * @author Darien Chong
     *
     */
    public enum SortOrder {
        ASCENDING(SortCommand.ASCENDING_ORDER_FLAG),
        DESCENDING(SortCommand.DESCENDING_ORDER_FLAG);

        private String shortForm;

        SortOrder(String shortForm) {
            this.shortForm = shortForm;
        }

        /**
         * Returns the short form of the sorting order.
         */
        public String getShortForm() {
            return shortForm;
        }

        /**
         * Returns the corresponding {@code SortOrder} object
         * from its short form, or {@code null} if no such corresponding
         * {@code SortOrder} exists.
         */
        public static SortOrder fromShortForm(String shortForm) {
            for (SortOrder order : SortOrder.values()) {
                if (order.getShortForm().equals(shortForm)) {
                    return order;
                }
            }

            return null;
        }
    }
}
