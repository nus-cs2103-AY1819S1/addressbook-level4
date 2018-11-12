package seedu.parking.logic.commands;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.parking.logic.commands.QueryCommand.MESSAGE_ERROR_CARPARK;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import guitests.guihandles.MainWindowHandle;
import seedu.parking.TestApp;
import seedu.parking.commons.core.EventsCenter;
import seedu.parking.commons.events.model.DataFetchExceptionEvent;
import seedu.parking.commons.util.GsonUtil;
import seedu.parking.logic.CommandHistory;
import seedu.parking.logic.commands.exceptions.CommandException;
import seedu.parking.model.CarparkFinder;
import seedu.parking.model.Model;
import seedu.parking.model.ModelManager;
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
import seedu.parking.testutil.TypicalCarparks;
import seedu.parking.ui.testutil.GuiTestAssert;
import systemtests.SystemTestSetupHelper;

public class QueryCommandTest {

    private CommandHistory commandHistory = new CommandHistory();


    private MainWindowHandle mainWindowHandle;
    private TestApp testApp;
    private SystemTestSetupHelper setupHelper;

    @BeforeClass
    public static void setupBeforeClass() {
        SystemTestSetupHelper.initialize();
    }

    @Before
    public void setUp() {
        setupHelper = new SystemTestSetupHelper();
        testApp = setupHelper.setupApplication(this::getInitialData, getDataFileLocation());
        mainWindowHandle = setupHelper.setupMainWindowHandle();

        waitUntilBrowserLoaded(mainWindowHandle.getBrowserPanel());
    }

    @After
    public void tearDown() {
        setupHelper.tearDownStage();
        EventsCenter.clearSubscribers();
    }

    /**
     * Returns the data to be loaded into the file in {@link #getDataFileLocation()}.
     */
    protected CarparkFinder getInitialData() {
        return TypicalCarparks.getTypicalCarparkFinder();
    }

    /**
     * Returns the directory of the data file.
     */
    protected Path getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
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

    @Test
    public void execute_fullCarparkFinder_success() throws Exception {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        expectedModel.loadCarpark(readCarpark(GsonUtil.fetchAllCarparkInfo()));
        expectedModel.commitCarparkFinder();

        QueryCommand command = new QueryCommand();
        command.execute(model, commandHistory);
        command.getFuture().get();

        assertEquals(model.getCarparkFinder().getCarparkList().size(),
                expectedModel.getCarparkFinder().getCarparkList().size());
    }

    @Test
    public void postEvent_dataFetchExceptionEvent_exceptionThrown() {
        EventsCenter.getInstance().post(new DataFetchExceptionEvent(
                new CommandException(MESSAGE_ERROR_CARPARK)));
        mainWindowHandle = setupHelper.setupMainWindowHandle();
        GuiTestAssert.assertResultMessage(mainWindowHandle.getResultDisplay(), MESSAGE_ERROR_CARPARK);
    }
}
