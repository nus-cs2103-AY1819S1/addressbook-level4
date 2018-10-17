package seedu.inventory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.inventory.commons.core.Config;
import seedu.inventory.commons.core.EventsCenter;
import seedu.inventory.commons.core.LogsCenter;
import seedu.inventory.commons.core.Version;
import seedu.inventory.commons.events.ui.ExitAppRequestEvent;
import seedu.inventory.commons.exceptions.DataConversionException;
import seedu.inventory.commons.util.ConfigUtil;
import seedu.inventory.commons.util.StringUtil;
import seedu.inventory.logic.Logic;
import seedu.inventory.logic.LogicManager;
import seedu.inventory.model.Inventory;
import seedu.inventory.model.Model;
import seedu.inventory.model.ModelManager;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.ReadOnlySaleList;
import seedu.inventory.model.ReadOnlyStaffList;
import seedu.inventory.model.SaleList;
import seedu.inventory.model.StaffList;
import seedu.inventory.model.UserPrefs;
import seedu.inventory.model.util.SampleDataUtil;
import seedu.inventory.storage.InventoryStorage;
import seedu.inventory.storage.JsonUserPrefsStorage;
import seedu.inventory.storage.SaleListStorage;
import seedu.inventory.storage.StaffStorage;
import seedu.inventory.storage.Storage;
import seedu.inventory.storage.StorageManager;
import seedu.inventory.storage.UserPrefsStorage;
import seedu.inventory.storage.XmlInventoryStorage;
import seedu.inventory.storage.XmlSaleListStorage;
import seedu.inventory.storage.XmlStaffListStorage;
import seedu.inventory.ui.Ui;
import seedu.inventory.ui.UiManager;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 6, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected UserPrefs userPrefs;


    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing Inventory ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        userPrefs = initPrefs(userPrefsStorage);
        InventoryStorage inventoryStorage = new XmlInventoryStorage(userPrefs.getInventoryFilePath());
        StaffStorage staffStorage = new XmlStaffListStorage(userPrefs.getStaffListFilePath());
        SaleListStorage saleListStorage = new XmlSaleListStorage();
        storage = new StorageManager(inventoryStorage, userPrefsStorage, saleListStorage, staffStorage);
        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model);

        ui = new UiManager(logic, config, userPrefs);

        initEventsCenter();
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s inventory and {@code userPrefs}. <br>
     * The data from the sample inventory will be used instead if {@code storage}'s inventory is not found,
     * or an empty inventory will be used instead if errors occur when reading {@code storage}'s inventory.
     */
    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyInventory> inventoryOptional;
        ReadOnlyInventory initialData;
        try {
            inventoryOptional = storage.readInventory();

            if (!inventoryOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample Inventory");
            }

            initialData = inventoryOptional.orElseGet(SampleDataUtil::getSampleInventory);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty Inventory");
            initialData = new Inventory();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty Inventory");
            initialData = new Inventory();
        }

        // Staff List
        Optional<ReadOnlyStaffList> staffListOptional;
        ReadOnlyStaffList initialStaffListData;

        try {
            staffListOptional = storage.readStaffList();
            if (!staffListOptional.isPresent()) {
                logger.info("Staff data file not found. Will be starting with a sample Staff List");
            }
            initialStaffListData = staffListOptional.orElseGet(SampleDataUtil::getSampleStaffList);
        } catch (DataConversionException e) {
            logger.warning("Data file for staff not in the correct format."
                    + " Will be starting with an empty staff list");

            initialStaffListData = new StaffList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty staff list");

            initialStaffListData = new StaffList();
        }

        // Sale List
        Optional<ReadOnlySaleList> saleListOptional;
        ReadOnlySaleList readOnlySaleList;

        try {
            saleListOptional = storage.readSaleList(initialData);
            if (!saleListOptional.isPresent()) {
                logger.info("Data file not found for sale list. Will be starting with empty sale list.");
            }

            readOnlySaleList = saleListOptional.orElse(new SaleList());
        } catch (DataConversionException e) {
            logger.warning("Data file for sale list not in the correct format."
                    + " Will be starting with an empty sale list");

            readOnlySaleList = new SaleList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty sale list");

            readOnlySaleList = new SaleList();
        }

        return new ModelManager(initialData, userPrefs, readOnlySaleList, initialStaffListData);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty Inventory");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    private void initEventsCenter() {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting Inventory " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Inventory ] =============================");
        ui.stop();
        try {
            storage.saveUserPrefs(userPrefs);
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
        Platform.exit();
        System.exit(0);
    }

    @Subscribe
    public void handleExitAppRequestEvent(ExitAppRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
