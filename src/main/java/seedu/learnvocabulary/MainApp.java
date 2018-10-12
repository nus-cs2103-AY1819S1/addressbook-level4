package seedu.learnvocabulary;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.learnvocabulary.commons.core.Config;
import seedu.learnvocabulary.commons.core.EventsCenter;
import seedu.learnvocabulary.commons.core.LogsCenter;
import seedu.learnvocabulary.commons.core.Version;
import seedu.learnvocabulary.commons.events.ui.ExitAppRequestEvent;
import seedu.learnvocabulary.commons.exceptions.DataConversionException;
import seedu.learnvocabulary.commons.util.ConfigUtil;
import seedu.learnvocabulary.commons.util.StringUtil;
import seedu.learnvocabulary.logic.Logic;
import seedu.learnvocabulary.logic.LogicManager;
import seedu.learnvocabulary.model.LearnVocabulary;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.ModelManager;
import seedu.learnvocabulary.model.ReadOnlyLearnVocabulary;
import seedu.learnvocabulary.model.UserPrefs;
import seedu.learnvocabulary.model.util.SampleDataUtil;
import seedu.learnvocabulary.storage.JsonUserPrefsStorage;

import seedu.learnvocabulary.storage.LearnVocabularyStorage;
import seedu.learnvocabulary.storage.Storage;
import seedu.learnvocabulary.storage.StorageManager;
import seedu.learnvocabulary.storage.UserPrefsStorage;
import seedu.learnvocabulary.storage.XmlLearnVocabularyStorage;
import seedu.learnvocabulary.ui.Ui;
import seedu.learnvocabulary.ui.UiManager;

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
        logger.info("=============================[ Initializing LearnVocabulary ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        userPrefs = initPrefs(userPrefsStorage);
        LearnVocabularyStorage learnVocabularyStorage =
                new XmlLearnVocabularyStorage(userPrefs.getLearnVocabularyFilePath());
        storage = new StorageManager(learnVocabularyStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model);

        ui = new UiManager(logic, config, userPrefs);

        initEventsCenter();
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s learnvocabulary and {@code userPrefs}. <br>
     * The data from the sample learnvocabulary will be used instead if {@code storage}'s learnvocabulary not found,
     * or an empty learnvocabulary will be used instead if errors occur when reading {@code storage}'s learnvocabulary.
     */
    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyLearnVocabulary> learnVocabularyOptional;
        ReadOnlyLearnVocabulary initialData;
        try {
            learnVocabularyOptional = storage.readLearnVocabulary();
            if (!learnVocabularyOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample LearnVocabulary");
            }
            initialData = learnVocabularyOptional.orElseGet(SampleDataUtil::getSampleLearnVocabulary);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty LearnVocabulary");
            initialData = new LearnVocabulary();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty LearnVocabulary");
            initialData = new LearnVocabulary();
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
            logger.warning("Problem while reading from the file. Will be starting with an empty LearnVocabulary");
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
        logger.info("Starting LearnVocabulary " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping LearnVocabulary ] =============================");
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
