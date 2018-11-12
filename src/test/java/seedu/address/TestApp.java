package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.OrderBook;
import seedu.address.model.ReadOnlyOrderBook;
import seedu.address.model.ReadOnlyUsersList;
import seedu.address.model.UserPrefs;
import seedu.address.model.deliveryman.DeliverymenList;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.XmlFoodZoom;
import seedu.address.storage.user.XmlSerializableUsersList;
import seedu.address.testutil.TestUtil;
import systemtests.ModelHelper;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final Path SAVE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("sampleData.xml");
    public static final Path USERS_SAVE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("sampleUsers.xml");
    public static final String APP_TITLE = "Test App";

    protected static final Path DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    protected Supplier<ReadOnlyOrderBook> initialOrdersDataSupplier = () -> null;
    protected Supplier<DeliverymenList> initialDeliverymenDataSupplier = () -> null;
    protected Supplier<ReadOnlyUsersList> initialUsersListSupplier = () -> null;
    protected Path saveFileLocation = SAVE_LOCATION_FOR_TESTING;
    protected Path usersSaveFileLocation = USERS_SAVE_LOCATION_FOR_TESTING;

    public TestApp() {
    }

    public TestApp(Supplier<ReadOnlyOrderBook> initialOrdersDataSupplier,
                   Supplier<DeliverymenList> initialDeliverymenDataSupplier,
                   Supplier<ReadOnlyUsersList> initialUsersListSupplier,
                   Path saveFileLocation, Path usersSaveFileLocation) {
        super();
        this.initialOrdersDataSupplier = initialOrdersDataSupplier;
        this.initialDeliverymenDataSupplier = initialDeliverymenDataSupplier;
        this.initialUsersListSupplier = initialUsersListSupplier;
        this.saveFileLocation = saveFileLocation;
        this.usersSaveFileLocation = usersSaveFileLocation;

        // If some initial local data for both orders and deliverymen has been provided, write those to the file
        if (initialOrdersDataSupplier.get() != null && initialDeliverymenDataSupplier.get() != null) {
            createDataFileWithData(new XmlFoodZoom(this.initialOrdersDataSupplier.get(),
                    this.initialDeliverymenDataSupplier.get()), this.saveFileLocation);
        }

        if (initialUsersListSupplier.get() != null) {
            createDataFileWithData(new XmlSerializableUsersList(this.initialUsersListSupplier.get()),
                    this.usersSaveFileLocation);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected Config initConfig(Path configFilePath) {
        Config config = super.initConfig(configFilePath);
        config.setAppTitle(APP_TITLE);
        config.setUserPrefsFilePath(DEFAULT_PREF_FILE_LOCATION_FOR_TESTING);
        return config;
    }

    @Override
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        UserPrefs userPrefs = super.initPrefs(storage);
        double x = Screen.getPrimary().getVisualBounds().getMinX();
        double y = Screen.getPrimary().getVisualBounds().getMinY();
        userPrefs.updateLastUsedGuiSetting(new GuiSettings(600.0, 600.0, (int) x, (int) y));
        userPrefs.setFoodZoomFilePath(saveFileLocation);
        userPrefs.setUsersListFilePath(usersSaveFileLocation);
        return userPrefs;
    }

    /**
     * Returns a defensive copy of the order book data stored inside the storage file.
     */
    public OrderBook readStorageOrderBook() {
        try {
            return new OrderBook(storage.readOrderBook().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the OrderBook format.", dce);
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.", ioe);
        }
    }

    /**
     * Returns the file path of the storage file.
     */
    public Path getStorageSaveLocation() {
        return storage.getFoodZoomFilePath();
    }

    /**
     * Returns a defensive copy of the model.
     */
    public Model getModel() {
        Model copy = new ModelManager(model.getOrderBook(), model.getUsersList(),
                model.getDeliverymenList(), new UserPrefs());
        ModelHelper.setFilteredList(copy, model.getFilteredOrderList());
        return copy;
    }

    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }

    /**
     * Creates an XML file at the {@code filePath} with the {@code data}.
     */
    private <T> void createDataFileWithData(T data, Path filePath) {
        try {
            FileUtil.createIfMissing(filePath);
            XmlUtil.saveDataToFile(filePath, data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
