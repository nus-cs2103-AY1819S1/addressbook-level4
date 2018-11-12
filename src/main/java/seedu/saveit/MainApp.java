package seedu.saveit;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.saveit.commons.core.Config;
import seedu.saveit.commons.core.EventsCenter;
import seedu.saveit.commons.core.LogsCenter;
import seedu.saveit.commons.core.Version;
import seedu.saveit.commons.events.ui.ExitAppRequestEvent;
import seedu.saveit.commons.exceptions.DataConversionException;
import seedu.saveit.commons.util.ConfigUtil;
import seedu.saveit.commons.util.StringUtil;
import seedu.saveit.logic.Logic;
import seedu.saveit.logic.LogicManager;
import seedu.saveit.logic.SuggestionLogic;
import seedu.saveit.logic.SuggestionLogicManager;
import seedu.saveit.model.Model;
import seedu.saveit.model.ModelManager;
import seedu.saveit.model.ReadOnlySaveIt;
import seedu.saveit.model.SaveIt;
import seedu.saveit.model.UserPrefs;
import seedu.saveit.model.util.SampleDataUtil;
import seedu.saveit.storage.JsonUserPrefsStorage;
import seedu.saveit.storage.SaveItStorage;
import seedu.saveit.storage.Storage;
import seedu.saveit.storage.StorageManager;
import seedu.saveit.storage.UserPrefsStorage;
import seedu.saveit.storage.XmlSaveItStorage;
import seedu.saveit.ui.Ui;
import seedu.saveit.ui.UiManager;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 4, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected SuggestionLogic suggestionLogic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected UserPrefs userPrefs;


    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing SaveIt ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        userPrefs = initPrefs(userPrefsStorage);
        SaveItStorage saveItStorage = new XmlSaveItStorage(userPrefs.getSaveItFilePath());
        storage = new StorageManager(saveItStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model);

        suggestionLogic = new SuggestionLogicManager(model);

        ui = new UiManager(logic, suggestionLogic, config, userPrefs);

        initEventsCenter();
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s saveIt and {@code userPrefs}. <br>
     * The data from the sample saveIt will be used instead if {@code storage}'s saveIt is not found,
     * or an empty saveIt will be used instead if errors occur when reading {@code storage}'s saveIt.
     */
    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlySaveIt> saveItOptional;
        ReadOnlySaveIt initialData;
        try {
            saveItOptional = storage.readSaveIt();
            if (!saveItOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample SaveIt");
            }
            initialData = saveItOptional.orElseGet(SampleDataUtil::getSampleSaveIt);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty SaveIt");
            initialData = new SaveIt();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty SaveIt");
            initialData = new SaveIt();
        }

        return new ModelManager(initialData, userPrefs);
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
            logger.warning("Problem while reading from the file. Will be starting with an empty SaveIt");
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
        logger.info("Starting SaveIt " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping SaveIt ] =============================");
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
