package seedu.restaurant.logic.commands.reservation;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.restaurant.commons.core.Messages;
import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.reservation.Reservation;

/**
 * Deletes a reservation identified using it's displayed index from the restaurant book.
 */
public class DeleteReservationCommand extends Command {

    public static final String COMMAND_WORD = "delete-reservation";

    public static final String COMMAND_ALIAS = "dr";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the reservation identified by the index number used in the displayed reservation list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_RESERVATION_SUCCESS = "Deleted Reservation: %1$s";

    private final Index targetIndex;

    public DeleteReservationCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Reservation> lastShownList = model.getFilteredReservationList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
        }

        Reservation reservationToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteReservation(reservationToDelete);
        model.commitRestaurantBook();
        return new CommandResult(String.format(MESSAGE_DELETE_RESERVATION_SUCCESS, reservationToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteReservationCommand // instanceof handles nulls
                    && targetIndex.equals(((DeleteReservationCommand) other).targetIndex)); // state check
    }
}
