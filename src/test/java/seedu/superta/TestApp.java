package seedu.superta;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.superta.commons.core.Config;
import seedu.superta.commons.core.GuiSettings;
import seedu.superta.commons.exceptions.DataConversionException;
import seedu.superta.commons.util.FileUtil;
import seedu.superta.commons.util.XmlUtil;
import seedu.superta.model.Model;
import seedu.superta.model.ModelManager;
import seedu.superta.model.ReadOnlySuperTaClient;
import seedu.superta.model.SuperTaClient;
import seedu.superta.model.UserPrefs;
import seedu.superta.storage.UserPrefsStorage;
import seedu.superta.storage.XmlSerializableSuperTaClient;
import seedu.superta.testutil.TestUtil;
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
    protected Supplier<ReadOnlySuperTaClient> initialDataSupplier = () -> null;
    protected Path saveFileLocation = SAVE_LOCATION_FOR_TESTING;

    public TestApp() {
    }

    public TestApp(Supplier<ReadOnlySuperTaClient> initialDataSupplier, Path saveFileLocation) {
        super();
        this.initialDataSupplier = initialDataSupplier;
        this.saveFileLocation = saveFileLocation;

        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null) {
            createDataFileWithData(new XmlSerializableSuperTaClient(this.initialDataSupplier.get()),
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
        userPrefs.setAddressBookFilePath(saveFileLocation);
        return userPrefs;
    }

    /**
     * Returns a defensive copy of the address book data stored inside the storage file.
     */
    public SuperTaClient readStorageAddressBook() {
        try {
            return new SuperTaClient(storage.readSuperTaClient().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the SuperTaClient format.", dce);
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.", ioe);
        }
    }

    /**
     * Returns the file path of the storage file.
     */
    public Path getStorageSaveLocation() {
        return storage.getSuperTaClientFilePath();
    }

    /**
     * Returns a defensive copy of the model.
     */
    public Model getModel() {
        Model copy = new ModelManager((model.getSuperTaClient()), new UserPrefs());
        ModelHelper.setFilteredList(copy, model.getFilteredStudentList());
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
