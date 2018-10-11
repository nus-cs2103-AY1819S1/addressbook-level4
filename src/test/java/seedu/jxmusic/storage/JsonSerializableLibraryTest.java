package seedu.jxmusic.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.jxmusic.commons.exceptions.IllegalValueException;
import seedu.jxmusic.commons.util.XmlUtil;
import seedu.jxmusic.model.AddressBook;
import seedu.jxmusic.testutil.TypicalPlaylists;

public class JsonSerializableLibraryTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableLibraryTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.xml");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.xml");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableLibrary dataFromFile = XmlUtil.getDataFromFile(TYPICAL_PERSONS_FILE,
                JsonSerializableLibrary.class);
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalPersonsAddressBook = TypicalPlaylists.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalPersonsAddressBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableLibrary dataFromFile = XmlUtil.getDataFromFile(INVALID_PERSON_FILE,
                JsonSerializableLibrary.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableLibrary dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_PERSON_FILE,
                JsonSerializableLibrary.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableLibrary.MESSAGE_DUPLICATE_PERSON);
        dataFromFile.toModelType();
    }

}
