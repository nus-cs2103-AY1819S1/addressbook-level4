package seedu.meeting;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import seedu.meeting.commons.core.Config;
import seedu.meeting.commons.core.EventsCenter;
import seedu.meeting.commons.core.LogsCenter;
import seedu.meeting.commons.core.Version;
import seedu.meeting.commons.events.ui.ExitAppRequestEvent;
import seedu.meeting.commons.exceptions.DataConversionException;
import seedu.meeting.commons.util.ConfigUtil;
import seedu.meeting.commons.util.StringUtil;
import seedu.meeting.logic.Logic;
import seedu.meeting.logic.LogicManager;
import seedu.meeting.model.MeetingBook;
import seedu.meeting.model.Model;
import seedu.meeting.model.ModelManager;
import seedu.meeting.model.ReadOnlyMeetingBook;
import seedu.meeting.model.UserPrefs;
import seedu.meeting.model.util.SampleDataUtil;
import seedu.meeting.storage.JsonUserPrefsStorage;
import seedu.meeting.storage.MeetingBookStorage;
import seedu.meeting.storage.Storage;
import seedu.meeting.storage.StorageManager;
import seedu.meeting.storage.UserPrefsStorage;
import seedu.meeting.storage.XmlMeetingBookStorage;
import seedu.meeting.ui.Ui;
import seedu.meeting.ui.UiManager;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 3, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected UserPrefs userPrefs;


    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing MeetingBook ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        userPrefs = initPrefs(userPrefsStorage);
        MeetingBookStorage meetingBookStorage = new XmlMeetingBookStorage(userPrefs.getMeetingBookFilePath());
        storage = new StorageManager(meetingBookStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model);

        ui = new UiManager(logic, config, userPrefs);

        initEventsCenter();
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s MeetingBook and {@code userPrefs}. <br>
     * The data from the sample MeetingBook will be used instead if {@code storage}'s MeetingBook is not found,
     * or an empty MeetingBook will be used instead if errors occur when reading {@code storage}'s MeetingBook.
     */
    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyMeetingBook> meetingBookOptional;
        ReadOnlyMeetingBook initialData;
        try {
            meetingBookOptional = storage.readMeetingBook();
            if (!meetingBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample MeetingBook");
            }
            initialData = meetingBookOptional.orElseGet(SampleDataUtil::getSampleMeetingBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty MeetingBook");
            initialData = new MeetingBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty MeetingBook");
            initialData = new MeetingBook();
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
            logger.warning("Problem while reading from the file. Will be starting with an empty MeetingBook");
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
        logger.info("Starting MeetingBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping MeetingBook] =============================");
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
