package seedu.thanepark;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.thanepark.commons.core.Config;
import seedu.thanepark.commons.core.GuiSettings;
import seedu.thanepark.commons.exceptions.DataConversionException;
import seedu.thanepark.commons.util.FileUtil;
import seedu.thanepark.commons.util.XmlUtil;
import seedu.thanepark.model.Model;
import seedu.thanepark.model.ModelManager;
import seedu.thanepark.model.ReadOnlyThanePark;
import seedu.thanepark.model.ThanePark;
import seedu.thanepark.model.UserPrefs;
import seedu.thanepark.storage.UserPrefsStorage;
import seedu.thanepark.storage.XmlSerializableThanePark;
import seedu.thanepark.testutil.TestUtil;
import systemtests.ModelHelper;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final Path SAVE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("sampleData.xml");
    public static final String APP_TITLE = "Test App";

    protected static final Path DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    protected Supplier<ReadOnlyThanePark> initialDataSupplier = () -> null;
    protected Path saveFileLocation = SAVE_LOCATION_FOR_TESTING;

    public TestApp() {
    }

    public TestApp(Supplier<ReadOnlyThanePark> initialDataSupplier, Path saveFileLocation) {
        super();
        this.initialDataSupplier = initialDataSupplier;
        this.saveFileLocation = saveFileLocation;

        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null) {
            createDataFileWithData(new XmlSerializableThanePark(this.initialDataSupplier.get()),
                    this.saveFileLocation);
        }
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
        userPrefs.setThaneParkFilePath(saveFileLocation);
        return userPrefs;
    }

    /**
     * Returns a defensive copy of the thanepark book data stored inside the storage file.
     */
    public ThanePark readStorageThanePark() {
        try {
            return new ThanePark(storage.readThanePark().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the ThanePark format.", dce);
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.", ioe);
        }
    }

    /**
     * Returns the file path of the storage file.
     */
    public Path getStorageSaveLocation() {
        return storage.getThaneParkFilePath();
    }

    /**
     * Returns a defensive copy of the model.
     */
    public Model getModel() {
        Model copy = new ModelManager((model.getThanePark()), new UserPrefs());
        ModelHelper.setFilteredList(copy, model.getFilteredRideList());
        return copy;
    }

    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
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
