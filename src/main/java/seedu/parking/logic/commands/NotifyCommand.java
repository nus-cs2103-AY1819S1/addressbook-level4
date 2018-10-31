package seedu.parking.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.parking.commons.core.Messages;
import seedu.parking.commons.core.index.Index;
import seedu.parking.logic.CommandHistory;
import seedu.parking.logic.commands.exceptions.CommandException;
import seedu.parking.model.Model;
import seedu.parking.model.carpark.Address;
import seedu.parking.model.carpark.Carpark;
import seedu.parking.model.carpark.CarparkNumber;
import seedu.parking.model.carpark.CarparkType;
import seedu.parking.model.carpark.Coordinate;
import seedu.parking.model.carpark.FreeParking;
import seedu.parking.model.carpark.LotsAvailable;
import seedu.parking.model.carpark.NightParking;
import seedu.parking.model.carpark.PostalCode;
import seedu.parking.model.carpark.ShortTerm;
import seedu.parking.model.carpark.TotalLots;
import seedu.parking.model.carpark.TypeOfParking;
import seedu.parking.ui.CarparkListPanel;

/**
 * Notifies when to get the car park information from the API.
 */
public class NotifyCommand extends Command {

    public static final String COMMAND_WORD = "notify";
    public static final String COMMAND_ABBREVIATION = "n";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Set when to update the car park information.\n"
            + "Example: " + COMMAND_WORD + " 60";

    public static final String MESSAGE_SUCCESS = "Notification set for car park %1$d\nInterval: every %2$ds";
    public static final String MESSAGE_ERROR = "Cannot notify without selecting a car park first";
    public static final String MESSAGE_ERROR_CARPARK = "Unable to load car park information from database";

    private final int targetTime;

    public NotifyCommand(int targetTime) {
        this.targetTime = targetTime;
    }

    /**
     * Calls the API and load all the car parks information
     * @return An array of car parks
     */
    private List<Carpark> readCarpark(List<List<String>> carparkData) {
        List<Carpark> carparkList = new ArrayList<>();
        for (List<String> carpark : carparkData) {
            Carpark c = new Carpark(new Address(carpark.get(0)), new CarparkNumber(carpark.get(1)),
                    new CarparkType(carpark.get(2)), new Coordinate(carpark.get(3)),
                    new FreeParking(carpark.get(4)), new LotsAvailable(carpark.get(5)),
                    new NightParking(carpark.get(6)), new ShortTerm(carpark.get(7)),
                    new TotalLots(carpark.get(8)), new TypeOfParking(carpark.get(9)), new PostalCode(carpark.get(10)),
                    null);
            carparkList.add(c);
        }
        return carparkList;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        int validity = CarparkListPanel.getSelectedIndex();
        if (validity < 0) {
            throw new CommandException(MESSAGE_ERROR);
        }

        Index notifyIndex = Index.fromZeroBased(validity);
        List<Carpark> filteredCarparkList = model.getFilteredCarparkList();

        if (notifyIndex.getZeroBased() >= filteredCarparkList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CARPARK_DISPLAYED_INDEX);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, notifyIndex.getOneBased(), targetTime));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NotifyCommand // instanceof handles nulls
                && targetTime == ((NotifyCommand) other).targetTime); // state check
    }
}
