package seedu.restaurant;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.restaurant.commons.core.Config;
import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.core.LogsCenter;
import seedu.restaurant.commons.core.Version;
import seedu.restaurant.commons.events.ui.ExitAppRequestEvent;
import seedu.restaurant.commons.exceptions.DataConversionException;
import seedu.restaurant.commons.util.ConfigUtil;
import seedu.restaurant.commons.util.StringUtil;
import seedu.restaurant.logic.Logic;
import seedu.restaurant.logic.LogicManager;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ModelManager;
import seedu.restaurant.model.ReadOnlyRestaurantBook;
import seedu.restaurant.model.RestaurantBook;
import seedu.restaurant.model.UserPrefs;
import seedu.restaurant.model.util.SampleDataUtil;
import seedu.restaurant.storage.JsonUserPrefsStorage;
import seedu.restaurant.storage.RestaurantBookStorage;
import seedu.restaurant.storage.Storage;
import seedu.restaurant.storage.StorageManager;
import seedu.restaurant.storage.UserPrefsStorage;
import seedu.restaurant.storage.XmlRestaurantBookStorage;
import seedu.restaurant.ui.Ui;
import seedu.restaurant.ui.UiManager;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 4, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected UserPrefs userPrefs;
    
    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing MeNUS ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        userPrefs = initPrefs(userPrefsStorage);
        RestaurantBookStorage restaurantBookStorage = new XmlRestaurantBookStorage(
                userPrefs.getRestaurantBookFilePath());
        storage = new StorageManager(restaurantBookStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model);

        ui = new UiManager(logic, config, userPrefs);

        initEventsCenter();
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s restaurant book and {@code userPrefs}. <br>
     * The data from the sample restaurant book will be used instead if {@code storage}'s restaurant book is not found,
     * or an empty restaurant book will be used instead if errors occur when reading {@code storage}'s restaurant book.
     */
    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyRestaurantBook> restaurantBookOptional;
        ReadOnlyRestaurantBook initialRestaurantBookData;

        try {
            restaurantBookOptional = storage.readRestaurantBook();
            if (!restaurantBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample RestaurantBook");
            }
            initialRestaurantBookData = restaurantBookOptional.orElseGet(SampleDataUtil::getSampleRestaurantBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty RestaurantBook");
            initialRestaurantBookData = new RestaurantBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty RestaurantBook");
            initialRestaurantBookData = new RestaurantBook();
        }

        return new ModelManager(initialRestaurantBookData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br> The default file path {@code
     * Config#DEFAULT_CONFIG_FILE} will be used instead if {@code configFilePath} is null.
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
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path, or a new {@code UserPrefs}
     * with default configuration if errors occur when reading from the file.
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
            logger.warning("Problem while reading from the file. Will be starting with an empty RestaurantBook");
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
        logger.info("Starting RestaurantBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping MeNUS ] =============================");
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
