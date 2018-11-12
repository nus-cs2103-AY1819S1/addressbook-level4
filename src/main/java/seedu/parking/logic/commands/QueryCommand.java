package seedu.parking.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import seedu.parking.commons.core.EventsCenter;
import seedu.parking.commons.core.LogsCenter;
import seedu.parking.commons.events.model.DataFetchExceptionEvent;
import seedu.parking.commons.events.ui.ListCarparkRequestEvent;
import seedu.parking.commons.events.ui.NewResultAvailableEvent;
import seedu.parking.commons.events.ui.NoSelectionRequestEvent;
import seedu.parking.commons.events.ui.TimeIntervalChangeEvent;
import seedu.parking.commons.events.ui.ToggleTextFieldRequestEvent;
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
import seedu.parking.model.carpark.PostalCode;
import seedu.parking.model.carpark.ShortTerm;
import seedu.parking.model.carpark.TotalLots;
import seedu.parking.model.carpark.TypeOfParking;

/**
 * Queries when to get the car park information from the API.
 */
public class QueryCommand extends Command {

    public static final String COMMAND_WORD = "query";

    public static final String MESSAGE_SUCCESS = "%1$d car parks updated";
    private static final String MESSAGE_LOADING = "Loading... please wait...";
    private static final String MESSAGE_ERROR_CARPARK = "Unable to retrieve car park information from data.gov.sg\n"
            + "Please check your internet connection and try again";

    private final Logger logger = LogsCenter.getLogger(QueryCommand.class);

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
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        ExecutorService threadExecutor = Executors.newSingleThreadExecutor();

        Callable<Boolean> task = () -> {
            try {
                EventsCenter.getInstance().post(new TimeIntervalChangeEvent(0));
                EventsCenter.getInstance().post(new NoSelectionRequestEvent());
                EventsCenter.getInstance().post(new ToggleTextFieldRequestEvent());
                model.updateFilteredCarparkList(unused -> false);
                List<List<String>> carparkData = new ArrayList<>(GsonUtil.fetchAllCarparkInfo());
                List<Carpark> allCarparks = new ArrayList<>(readCarpark(carparkData));
                model.loadCarpark(allCarparks);
                model.commitCarparkFinder();
                int updated = model.compareCarparkFinder();
                EventsCenter.getInstance().post(new NewResultAvailableEvent(String.format(MESSAGE_SUCCESS, updated)));
                EventsCenter.getInstance().post(new ToggleTextFieldRequestEvent());
            } catch (Exception e) {
                model.updateFilteredCarparkList(unused -> true);
                EventsCenter.getInstance().post(new DataFetchExceptionEvent(
                        new CommandException(MESSAGE_ERROR_CARPARK)));
            }
            return true;
        };

        threadExecutor.submit(task);

        EventsCenter.getInstance().post(new ListCarparkRequestEvent());
        return new CommandResult(MESSAGE_LOADING);
    }
}
