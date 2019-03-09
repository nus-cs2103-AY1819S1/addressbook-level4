package seedu.jxmusic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.jxmusic.commons.core.Config;
import seedu.jxmusic.commons.core.GuiSettings;
import seedu.jxmusic.commons.exceptions.DataConversionException;
import seedu.jxmusic.commons.util.FileUtil;
import seedu.jxmusic.model.Library;
import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.ModelManager;
import seedu.jxmusic.model.ReadOnlyLibrary;
import seedu.jxmusic.model.UserPrefs;
import seedu.jxmusic.storage.JsonFileStorage;
import seedu.jxmusic.storage.UserPrefsStorage;
import seedu.jxmusic.testutil.TestUtil;
import systemtests.ModelHelper;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final Path SAVE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("sampleData.json");
    public static final String APP_TITLE = "Test App";

    protected static final Path DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    protected Supplier<ReadOnlyLibrary> initialDataSupplier = () -> null;
    protected Path saveFileLocation = SAVE_LOCATION_FOR_TESTING;

    public TestApp() {
    }

    public TestApp(Supplier<ReadOnlyLibrary> initialDataSupplier, Path saveFileLocation) {
        super();
        this.initialDataSupplier = initialDataSupplier;
        this.saveFileLocation = saveFileLocation;

        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null) {
            createDataFileWithData(new Library(this.initialDataSupplier.get()),
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
        userPrefs.setLibraryFilePath(saveFileLocation);
        return userPrefs;
    }

    /**
     * Returns a defensive copy of the jxmusic player data stored inside the storage file.
     */
    public Library readStorageLibrary() {
        try {
            return new Library(storage.readLibrary());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the Library format.", dce);
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.", ioe);
        }
    }

    /**
     * Returns the file path of the storage file.
     */
    public Path getStorageSaveLocation() {
        return storage.getLibraryFilePath();
    }

    /**
     * Returns a defensive copy of the model.
     */
    public Model getModel() {
        Model copy = new ModelManager((model.getLibrary()), new UserPrefs());
        ModelHelper.setFilteredList(copy, model.getFilteredPlaylistList());
        ModelHelper.setFilteredTrackList(copy, model.getFilteredTrackList());
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
     * Creates a Json file at the {@code filePath} with the {@code data}.
     */
    private <T> void createDataFileWithData(Library data, Path filePath) {
        try {
            FileUtil.createIfMissing(filePath);
            JsonFileStorage.saveDataToFile(filePath, data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
