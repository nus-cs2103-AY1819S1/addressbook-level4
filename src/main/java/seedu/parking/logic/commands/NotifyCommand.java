package seedu.parking.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.parking.logic.CommandHistory;
import seedu.parking.logic.NotifyTimeTask;
import seedu.parking.model.Model;
import seedu.parking.ui.CarparkListPanel;

/**
 * Notifies when to get the car park information from the API.
 */
public class NotifyCommand extends Command {

    public static final String COMMAND_WORD = "notify";
    public static final String FORMAT = "notify SECONDS";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Set when to update the car park information.\n"
            + "Parameters: SECONDS (0 is turn off, range is 10 to 600 seconds)\n"
            + "Example: " + COMMAND_WORD + " 60";

    public static final String MESSAGE_SUCCESS = "Notification set for car park %1$s\nInterval: every %2$ds";
    public static final String MESSAGE_ERROR = "Cannot notify without selecting a car park first";
    public static final String MESSAGE_ERROR_CARPARK = "Unable to load car park information from database";


    private final int targetTime;

    public NotifyCommand(int targetTime) {
        this.targetTime = targetTime;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        if (targetTime > 0) {
            NotifyTimeTask task = new NotifyTimeTask(model, targetTime);
            CarparkListPanel.setTimer();
            CarparkListPanel.getTimer().scheduleAtFixedRate(task, 0, targetTime * 1000);

            return new CommandResult("Loading...please wait...");
        } else {
            CarparkListPanel.getTimer().cancel();
            return new CommandResult("Notification turned off");
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NotifyCommand // instanceof handles nulls
                && targetTime == ((NotifyCommand) other).targetTime); // state check
    }
}
