package seedu.restaurant.logic.commands.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.model.Model.PREDICATE_SHOW_ALL_RESERVATIONS;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.events.ui.reservation.DisplayReservationListRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.model.Model;

/**
 * Sorts and lists all reservations in reservations list by date/time in ascending order.
 */
public class SortReservationsCommand extends Command {

    public static final String COMMAND_WORD = "sort-reservations";

    public static final String COMMAND_ALIAS = "sortr";

    public static final String MESSAGE_SORTED = "Reservations have been sorted by Date/Time";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.sortReservations();
        model.updateFilteredReservationList(PREDICATE_SHOW_ALL_RESERVATIONS);
        model.commitRestaurantBook();
        EventsCenter.getInstance().post(new DisplayReservationListRequestEvent());
        return new CommandResult(String.format(MESSAGE_SORTED));
    }

}
