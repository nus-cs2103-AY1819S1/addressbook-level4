package seedu.restaurant.logic.commands.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_DATE;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_NAME;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_PAX;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_TAG;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_TIME;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.events.ui.reservation.DisplayReservationListRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.reservation.Reservation;

/**
 * Adds a reservation to the restaurant book.
 */
public class AddReservationCommand extends Command {

    public static final String COMMAND_WORD = "add-reservation";

    public static final String COMMAND_ALIAS = "ar";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a reservation to the restaurant book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PAX + "PAX "
            + PREFIX_DATE + "DATE "
            + PREFIX_TIME + "TIME "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PAX + "4 "
            + PREFIX_DATE + "05-12-2019 "
            + PREFIX_TIME + "10:00 "
            + PREFIX_TAG + "Driving";

    public static final String MESSAGE_SUCCESS = "New reservation added: %1$s";
    public static final String MESSAGE_DUPLICATE_RESERVATION = "This reservation already exists in the restaurant book";

    private final Reservation toAdd;

    /**
     * Creates an AddReservationCommand to add the specified {@code Reservation}
     */
    public AddReservationCommand(Reservation reservation) {
        requireNonNull(reservation);
        toAdd = reservation;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasReservation(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_RESERVATION);
        }

        model.addReservation(toAdd);
        model.commitRestaurantBook();
        EventsCenter.getInstance().post(new DisplayReservationListRequestEvent());
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddReservationCommand // instanceof handles nulls
                    && toAdd.equals(((AddReservationCommand) other).toAdd));
    }
}
