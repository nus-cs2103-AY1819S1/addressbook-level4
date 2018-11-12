package tutorhelper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import tutorhelper.commons.core.Config;
import tutorhelper.commons.core.EventsCenter;
import tutorhelper.commons.core.LogsCenter;
import tutorhelper.commons.core.Version;
import tutorhelper.commons.events.ui.ExitAppRequestEvent;
import tutorhelper.commons.exceptions.DataConversionException;
import tutorhelper.commons.util.ConfigUtil;
import tutorhelper.commons.util.StringUtil;
import tutorhelper.logic.Logic;
import tutorhelper.logic.LogicManager;
import tutorhelper.model.Model;
import tutorhelper.model.ModelManager;
import tutorhelper.model.ReadOnlyTutorHelper;
import tutorhelper.model.TutorHelper;
import tutorhelper.model.UserPrefs;
import tutorhelper.model.util.SampleDataUtil;
import tutorhelper.storage.JsonUserPrefsStorage;
import tutorhelper.storage.Storage;
import tutorhelper.storage.StorageManager;
import tutorhelper.storage.TutorHelperStorage;
import tutorhelper.storage.UserPrefsStorage;
import tutorhelper.storage.XmlTutorHelperStorage;
import tutorhelper.ui.Ui;
import tutorhelper.ui.UiManager;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 4, 0, false);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected UserPrefs userPrefs;


    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing TutorHelper ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        userPrefs = initPrefs(userPrefsStorage);
        TutorHelperStorage tutorHelperStorage = new XmlTutorHelperStorage(userPrefs.getTutorHelperFilePath());
        storage = new StorageManager(tutorHelperStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model);

        ui = new UiManager(logic, config, userPrefs);

        initEventsCenter();
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s TutorHelper and {@code userPrefs}. <br>
     * The data from the sample TutorHelper will be used instead if {@code storage}'s TutorHelper is not found,
     * or an empty TutorHelper will be used instead if errors occur when reading {@code storage}'s TutorHelper.
     */
    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyTutorHelper> tutorHelperOptional;
        ReadOnlyTutorHelper initialData;
        try {
            tutorHelperOptional = storage.readTutorHelper();
            if (!tutorHelperOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample TutorHelper");
            }
            initialData = tutorHelperOptional.orElseGet(SampleDataUtil::getSampleTutorHelper);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty TutorHelper");
            initialData = new TutorHelper();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty TutorHelper");
            initialData = new TutorHelper();
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
            logger.warning("Problem while reading from the file. Will be starting with an empty TutorHelper");
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
        logger.info("Starting TutorHelper " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Tutor Helper ] =============================");
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
