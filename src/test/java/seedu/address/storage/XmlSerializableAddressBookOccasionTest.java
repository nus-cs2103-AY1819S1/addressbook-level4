package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalOccasions;

public class XmlSerializableAddressBookOccasionTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "XmlSerializableAddressBookTest");
    private static final Path TYPICAL_OCCASIONS_FILE = TEST_DATA_FOLDER.resolve("typicalOccasionsAddressBook.xml");
    private static final Path INVALID_OCCASION_FILE = TEST_DATA_FOLDER.resolve("invalidOccasionAddressBook.xml");
    private static final Path DUPLICATE_OCCASION_FILE = TEST_DATA_FOLDER.resolve("duplicateOccasionAddressBook.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalOccasionsFile_success() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_OCCASIONS_FILE,
                XmlSerializableAddressBook.class);
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalOccasionsAddressBook = TypicalOccasions.getTypicalOccasionsAddressBook();
        assertEquals(addressBookFromFile, typicalOccasionsAddressBook);
    }

    @Test
    public void toModelType_invalidOccasionFile_throwsIllegalArgumentException() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(INVALID_OCCASION_FILE,
                XmlSerializableAddressBook.class);
        thrown.expect(IllegalArgumentException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateOccasions_throwsIllegalValueException() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_OCCASION_FILE,
                XmlSerializableAddressBook.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableAddressBook.MESSAGE_DUPLICATE_OCCASION);
        dataFromFile.toModelType();
    }

}
