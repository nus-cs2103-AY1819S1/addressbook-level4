package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.ArrayList;

import seedu.address.commons.util.GsonUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.carpark.Address;
import seedu.address.model.carpark.Carpark;
import seedu.address.model.carpark.CarparkNumber;
import seedu.address.model.carpark.CarparkType;
import seedu.address.model.carpark.Coordinate;
import seedu.address.model.carpark.FreeParking;
import seedu.address.model.carpark.LotsAvailable;
import seedu.address.model.carpark.NightParking;
import seedu.address.model.carpark.ShortTerm;
import seedu.address.model.carpark.TotalLots;
import seedu.address.model.carpark.TypeOfParking;

/**
 * Notifies when to get the car park information from the API.
 */
public class NotifyCommand extends Command {

    public static final String COMMAND_WORD = "notify";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets when to update the car park information.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Car park information updated";
    public static final String MESSAGE_ERROR_CARPARK = "Problem loading car park information from online";

    /**
     * Calls the API and load all the car parks information
     * @return An array of car parks
     * @throws IOException If unable to load from API
     */
    private ArrayList<Carpark> readCarpark() throws IOException {
        ArrayList<Carpark> carparkList = new ArrayList<>();
        for (ArrayList<String> carpark : GsonUtil.fetchCarparkInfo()) {
            Carpark c = new Carpark(new Address(carpark.get(0)), new CarparkNumber(carpark.get(1)),
                    new CarparkType(carpark.get(2)), new Coordinate(carpark.get(3)),
                    new FreeParking(carpark.get(4)), new LotsAvailable(carpark.get(5)),
                    new NightParking(carpark.get(6)), new ShortTerm(carpark.get(7)),
                    new TotalLots(carpark.get(8)), new TypeOfParking(carpark.get(9)), null);
            carparkList.add(c);
        }
        return carparkList;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        try {
            model.loadCarpark(readCarpark());
        } catch (IOException e) {
            throw new CommandException(MESSAGE_ERROR_CARPARK);
        }
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
