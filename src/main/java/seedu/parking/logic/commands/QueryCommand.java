package seedu.parking.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import seedu.parking.commons.util.GsonUtil;
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
import seedu.parking.model.carpark.ShortTerm;
import seedu.parking.model.carpark.TotalLots;
import seedu.parking.model.carpark.TypeOfParking;

/**
 * Queries when to get the car park information from the API.
 */
public class QueryCommand extends Command {

    public static final String COMMAND_WORD = "query";
    public static final String COMMAND_ALIAS = "q";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets when to update the car park information.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = " Car parks updated";
    public static final String MESSAGE_LOADING = " Car parks updating...";
    private static final String MESSAGE_ERROR_CARPARK = "Problem loading car park information from database";

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
                    new TotalLots(carpark.get(8)), new TypeOfParking(carpark.get(9)), null);
            carparkList.add(c);
        }
        return carparkList;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        int numberChanged;

        System.out.println("Inside : " + Thread.currentThread().getName());

        System.out.println("Creating Runnable...");
        Runnable runnable = () -> {
            System.out.println("Inside : " + Thread.currentThread().getName());

        };

        try {
            List<List<String>> carparkData = new ArrayList<>(GsonUtil.fetchCarparkInfo());
            List<Carpark> allCarparks = new ArrayList<>(readCarpark(carparkData));
            model.loadCarpark(allCarparks);

            model.commitCarparkFinder();
            numberChanged = model.compareCarparkFinder();
        } catch (IOException e) {
            throw new CommandException(MESSAGE_ERROR_CARPARK);
        }

        Thread thread = new Thread(runnable);
        thread.start();

        return new CommandResult(numberChanged + MESSAGE_SUCCESS);
    }
}
