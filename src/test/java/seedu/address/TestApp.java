package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyTriviaBundle;
import seedu.address.model.TriviaBundle;
import seedu.address.model.UserPrefs;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.XmlSerializableAddressBook;
import seedu.address.storage.XmlSerializableTriviaBundle;
import seedu.address.testutil.TestUtil;
import systemtests.ModelHelper;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final Path ADDRESS_BOOK_SAVE_LOCATION = TestUtil.getFilePathInSandboxFolder("sampleData.xml");
    public static final Path TRIVIA_BUNDLE_SAVE_LOCATION = TestUtil.getFilePathInSandboxFolder("sampleTriviaData.xml");
    public static final String APP_TITLE = "Test App";

    protected static final Path DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    protected Supplier<ReadOnlyAddressBook> initialAddressBook = () -> null;
    protected Supplier<ReadOnlyTriviaBundle> initialTriviaBundle = () -> null;
    protected Path addressBookFile = ADDRESS_BOOK_SAVE_LOCATION;
    protected Path triviaBundleFile = TRIVIA_BUNDLE_SAVE_LOCATION;

    public TestApp() {
    }

    public TestApp(Supplier<ReadOnlyAddressBook> initialAddressBook, Supplier<ReadOnlyTriviaBundle> initialTriviaBundle,
                   Path addressBookFile, Path triviaBundleFile) {
        super();
        this.initialAddressBook = initialAddressBook;
        this.addressBookFile = addressBookFile;
        this.initialTriviaBundle = initialTriviaBundle;
        this.triviaBundleFile = triviaBundleFile;

        // If some initial local data has been provided, write those to the file
        if (this.initialAddressBook.get() != null) {
            createDataFileWithData(new XmlSerializableAddressBook(this.initialAddressBook.get()),
                    this.addressBookFile);
        }

        if (this.initialTriviaBundle.get() != null) {
            createDataFileWithData(new XmlSerializableTriviaBundle(this.initialTriviaBundle.get()),
                    this.triviaBundleFile);
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
        userPrefs.updateLastUsedGuiSetting(new GuiSettings(1000.0, 1000.0, (int) x, (int) y));
        userPrefs.setAddressBookFilePath(addressBookFile);
        userPrefs.setTriviaBundleFilePath(triviaBundleFile);
        return userPrefs;
    }

    /**
     * Returns a defensive copy of the address book data stored inside the storage file.
     */
    public AddressBook readStorageAddressBook() {
        try {
            return new AddressBook(storage.readAddressBook().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the AddressBook format.", dce);
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.", ioe);
        }
    }

    /**
     * Returns a defensive copy of the address book data stored inside the storage file.
     */
    public TriviaBundle readStorageTriviaBundle() {
        try {
            return new TriviaBundle(storage.readTriviaBundle().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the trivia bundle format.", dce);
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.", ioe);
        }
    }

    /**
     * Returns the file path of address book's storage file.
     */
    public Path getAddressBookFilePath() {
        return storage.getAddressBookFilePath();
    }

    /**
     * Returns the file path of trivia bundle's storage file.
     */
    public Path getTriviaBundleFilePath() {
        return storage.getTriviaBundleFilePath();
    }

    /**
     * Returns a defensive copy of the model.
     */
    public Model getModel() {
        Model copy = new ModelManager((model.getAddressBook()), model.getTriviaBundle(), new UserPrefs());
        // ModelHelper.setFilteredList(copy, model.getFilteredPersonList());
        ModelHelper.setFilteredList(copy, model.getFilteredCardList(), true);
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
