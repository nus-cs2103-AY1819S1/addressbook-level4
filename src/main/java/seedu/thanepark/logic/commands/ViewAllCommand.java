package seedu.thanepark.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.thanepark.model.Model.PREDICATE_SHOW_ALL_RIDES;

import java.util.List;

import seedu.thanepark.logic.CommandHistory;
import seedu.thanepark.model.Model;
import seedu.thanepark.model.ride.Ride;
import seedu.thanepark.model.ride.Status;

/**
 * Lists all persons in the thanepark book to the user.
 */
public class ViewAllCommand extends Command {

    public static final String COMMAND_WORD = "viewall";

    public static final String MESSAGE_SUCCESS = "%1$d rides listed! (OPEN: %2$d, SHUTDOWN: %3$d, MAINTENANCE: %4$d)";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredRideList(PREDICATE_SHOW_ALL_RIDES);
        List<Ride> filteredRideList = model.getFilteredRideList();
        int[] statuses = getRidesOfDifferentStatus(filteredRideList);
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, model.getFilteredRideList().size(),
                        statuses[0], statuses[1], statuses[2]));
    }

    public int[] getRidesOfDifferentStatus(List<Ride> rides) {
        int[] statusArray = new int[3];
        statusArray[0] = (int) rides.stream()
                .filter(x -> x.getStatus() == Status.OPEN)
                .count();
        statusArray[1] = (int) rides.stream()
                .filter(x -> x.getStatus() == Status.SHUTDOWN)
                .count();
        statusArray[2] = (int) rides.stream()
                .filter(x -> x.getStatus() == Status.MAINTENANCE)
                .count();
        return statusArray;
    }
}
