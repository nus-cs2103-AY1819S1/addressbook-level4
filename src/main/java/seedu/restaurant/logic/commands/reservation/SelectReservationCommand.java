package seedu.restaurant.logic.commands.reservation;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.core.Messages;
import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.commons.events.ui.reservation.DisplayReservationListRequestEvent;
import seedu.restaurant.commons.events.ui.reservation.JumpToReservationListRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.reservation.Reservation;

/**
 * Selects a reservation identified using it's displayed index from the restaurant book.
 */
public class SelectReservationCommand extends Command {

    public static final String COMMAND_WORD = "select-reservation";

    public static final String COMMAND_ALIAS = "sr";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the reservation identified by the index number used in the displayed reservation list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_RESERVATION_SUCCESS = "Selected Reservation: %1$s";

    private final Index targetIndex;

    public SelectReservationCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Reservation> filteredReservationList = model.getFilteredReservationList();

        if (targetIndex.getZeroBased() >= filteredReservationList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
        }
        EventsCenter.getInstance().post(new DisplayReservationListRequestEvent());
        EventsCenter.getInstance().post(new JumpToReservationListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_RESERVATION_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectReservationCommand // instanceof handles nulls
                    && targetIndex.equals(((SelectReservationCommand) other).targetIndex)); // state check
    }
}
