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
import seedu.address.model.ModelManagerToDo;
import seedu.address.model.ModelToDo;
import seedu.address.model.ReadOnlyScheduler;
import seedu.address.model.ReadOnlyToDoList;
import seedu.address.model.Scheduler;
import seedu.address.model.ToDoList;
import seedu.address.model.UserPrefs;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.SchedulerStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.ToDoListStorage;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.XmlSchedulerStorage;
import seedu.address.storage.XmlToDoListStorage;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 3, 0, false);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected ModelToDo modelToDo;
    protected Config config;
    protected UserPrefs userPrefs;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing U-Schedule ]=============================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        userPrefs = initPrefs(userPrefsStorage);
        SchedulerStorage schedulerStorage = new XmlSchedulerStorage(userPrefs.getSchedulerFilePath());
        ToDoListStorage toDoListStorage = new XmlToDoListStorage(userPrefs.getToDoListFilePath());
        storage = new StorageManager(schedulerStorage, toDoListStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        modelToDo = initModelManagerToDo(storage, userPrefs);

        logic = new LogicManager(model, modelToDo);

        ui = new UiManager(logic, config, userPrefs);

        initEventsCenter();
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s scheduler and {@code userPrefs}. <br>
     * The data from the sample scheduler will be used instead if {@code storage}'s scheduler is not found,
     * or an empty scheduler will be used instead if errors occur when reading {@code storage}'s scheduler.
     */
    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyScheduler> schedulerOptional;
        ReadOnlyScheduler initialData;
        try {
            schedulerOptional = storage.readScheduler();
            if (!schedulerOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample Scheduler");
            }
            initialData = schedulerOptional.orElseGet(SampleDataUtil::getSampleScheduler);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty Scheduler");
            initialData = new Scheduler();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty Scheduler");
            initialData = new Scheduler();
        }

        return new ModelManager(initialData, userPrefs);
    }

    /**
     * Returns a {@code ModelManagerToDo} with the data from {@code storage}'s todolist and {@code userPrefs}. <br>
     * The data from the sample todolist will be used instead if {@code storage}'s todolist is not found,
     * or an empty todolist will be used instead if errors occur when reading {@code storage}'s todolist.
     */
    private ModelToDo initModelManagerToDo(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyToDoList> toDoListOptional;
        ReadOnlyToDoList initialData;
        try {
            toDoListOptional = storage.readToDoList();
            if (!toDoListOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample ToDoList");
            }
            initialData = toDoListOptional.orElseGet(SampleDataUtil::getSampleToDoList);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty ToDoList");
            initialData = new ToDoList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty ToDoList");
            initialData = new ToDoList();
        }

        return new ModelManagerToDo(initialData, userPrefs);
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
            logger.warning("Problem while reading from the file. Will be starting with an empty Scheduler");
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
        logger.info("Starting Scheduler " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping U-Schedule ] =============================");
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
