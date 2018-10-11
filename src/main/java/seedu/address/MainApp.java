package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.OrderBook;
import seedu.address.model.ReadOnlyOrderBook;
import seedu.address.model.ReadOnlyUsersList;
import seedu.address.model.UserPrefs;
import seedu.address.model.UsersList;
import seedu.address.model.deliveryman.DeliverymenList;
import seedu.address.model.route.ReadOnlyRouteList;
import seedu.address.model.route.RouteList;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.OrderBookStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.XmlOrderBookStorage;
import seedu.address.storage.deliveryman.DeliverymenListStorage;
import seedu.address.storage.deliveryman.XmlDeliverymenListStorage;
import seedu.address.storage.route.RouteListStorage;
import seedu.address.storage.route.XmlRouteListStorage;
import seedu.address.storage.user.UsersListStorage;
import seedu.address.storage.user.XmlUsersListStorage;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing OrderBook ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        userPrefs = initPrefs(userPrefsStorage);
        OrderBookStorage orderBookStorage = new XmlOrderBookStorage(userPrefs.getAddressBookFilePath());
        DeliverymenListStorage deliverymenListStorage =
            new XmlDeliverymenListStorage(userPrefs.getDeliverymenListFilePath());
        UsersListStorage usersListStorage = new XmlUsersListStorage(userPrefs.getUsersListFilePath());
        RouteListStorage routeListStorage = new XmlRouteListStorage(userPrefs.getRouteListFilePath());
        storage = new StorageManager(orderBookStorage, routeListStorage, usersListStorage,
                deliverymenListStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model);

        ui = new UiManager(logic, config, userPrefs);

        initEventsCenter();
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s order book and {@code userPrefs}. <br>
     * The data from the sample order book will be used instead if {@code storage}'s order book is not found,
     * or an empty order book will be used instead if errors occur when reading {@code storage}'s order book.
     */
    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyOrderBook> orderBookOptional;
        ReadOnlyOrderBook initialData;
        Optional<ReadOnlyUsersList> usersListOptional;
        ReadOnlyUsersList initialUser;
        Optional<ReadOnlyRouteList> routeListOptional;
        ReadOnlyRouteList initialRouteListData;
        Optional<DeliverymenList> deliverymenListOptional;
        DeliverymenList initialDeliverymenData;

        try {
            orderBookOptional = storage.readOrderBook();
            if (!orderBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample OrderBook");
            }
            initialData = orderBookOptional.orElseGet(SampleDataUtil::getSampleOrderBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty OrderBook");
            initialData = new OrderBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty OrderBook");
            initialData = new OrderBook();
        }

        try {
            routeListOptional = storage.readRouteList();
            if (!routeListOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample Route List");
            }
            initialRouteListData = routeListOptional.orElseGet(SampleDataUtil::getSampleRouteList);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty RouteList");
            initialRouteListData = new RouteList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty RouteList");
            initialRouteListData = new RouteList();
        }

        try {
            usersListOptional = storage.readUsersList();
            if (!usersListOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample UsersList");
            }
            initialUser = usersListOptional.orElseGet(SampleDataUtil::getSampleUsersList);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty UsersList");
            initialUser = new UsersList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty UsersList");
            initialUser = new UsersList();
        }

        try {
            deliverymenListOptional = storage.readDeliverymenList();
            if (!deliverymenListOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample DeliverymenList");
            }
            initialDeliverymenData = deliverymenListOptional.orElseGet(SampleDataUtil::getSampleDeliverymenList);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty DeliverymenList");
            initialDeliverymenData = new DeliverymenList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty DeliverymenList");
            initialDeliverymenData = new DeliverymenList();
        }

        return new ModelManager(initialData, initialRouteListData, initialUser, initialDeliverymenData, userPrefs);
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
            logger.warning("Problem while reading from the file. Will be starting with an empty OrderBook");
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
        logger.info("Starting OrderBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Address Book ] =============================");
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
}
