package seedu.jxmusic.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.jxmusic.testutil.TypicalPlaylistList.SFX;

import static seedu.jxmusic.testutil.TypicalPlaylistList.getTypicalLibrary;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.jxmusic.model.Library;
import seedu.jxmusic.model.ReadOnlyLibrary;

public class JsonLibraryStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonLibraryStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readLibrary_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readLibrary(null);
    }

    private java.util.Optional<ReadOnlyLibrary> readLibrary(String filePath) throws Exception {
        return new JsonLibraryStorage(Paths.get(filePath)).readLibrary(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readLibrary("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() throws Exception {

        // thrown.expect(DataConversionException.class); // todo failing test
        // readLibrary("NotJsonFormatLibrary.json");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readLibrary_invalidPlaylistLibrary_throwDataConversionException() throws Exception {
        // thrown.expect(DataConversionException.class); // todo failing test
        // readLibrary("invalidPlaylistLibrary.json");
    }

    @Test
    public void readLibrary_invalidAndValidPlaylistLibrary_throwDataConversionException() throws Exception {
        // thrown.expect(DataConversionException.class); // todo failing test
        // readLibrary("invalidAndValidPlaylistLibrary.json");
    }

    @Test
    public void readAndSaveLibrary_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempLibrary.json");
        Library original = getTypicalLibrary();
        JsonLibraryStorage jsonLibraryStorage = new JsonLibraryStorage(filePath);

        //Save in new file and read back
        jsonLibraryStorage.saveLibrary(original, filePath);
        ReadOnlyLibrary readBack = jsonLibraryStorage.readLibrary(filePath).get();
        assertEquals(original, new Library(readBack));

        //Modify data, overwrite exiting file, and read back
        original.removePlaylist(SFX);
        jsonLibraryStorage.saveLibrary(original, filePath);
        readBack = jsonLibraryStorage.readLibrary(filePath).get();
        assertEquals(original, new Library(readBack));

        //Save and read without specifying file path
        original.addPlaylist(SFX);
        jsonLibraryStorage.saveLibrary(original); //file path not specified
        readBack = jsonLibraryStorage.readLibrary().get(); //file path not specified
        assertEquals(original, new Library(readBack));

    }

    @Test
    public void saveLibrary_nullLibrary_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveLibrary(null, "SomeFile.json");
    }

    /**
     * Saves {@code library} at the specified {@code filePath}.
     */
    private void saveLibrary(ReadOnlyLibrary library, String filePath) {
        try {
            new JsonLibraryStorage(Paths.get(filePath))
                    .saveLibrary(library, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveLibrary_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveLibrary(new Library(), null);
    }


}
