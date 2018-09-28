package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.WishBook;
import seedu.address.testutil.TypicalWishes;

public class XmlSerializableWishBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableWishBookTest");
    private static final Path TYPICAL_WISHES_FILE = TEST_DATA_FOLDER.resolve("typicalWishesWishBook.xml");
    private static final Path INVALID_WISHES_FILE = TEST_DATA_FOLDER.resolve("invalidWishWishBook.xml");
    private static final Path DUPLICATE_WISH_FILE = TEST_DATA_FOLDER.resolve("duplicateWishWishBook.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalWishesFile_success() throws Exception {
        XmlSerializableWishBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_WISHES_FILE,
                XmlSerializableWishBook.class);
        WishBook wishBookFromFile = dataFromFile.toModelType();
        WishBook typicalWishesWishBook = TypicalWishes.getTypicalWishBook();
        assertEquals(wishBookFromFile, typicalWishesWishBook);
    }

    @Test
    public void toModelType_invalidWishFile_throwsIllegalValueException() throws Exception {
        XmlSerializableWishBook dataFromFile = XmlUtil.getDataFromFile(INVALID_WISHES_FILE,
                XmlSerializableWishBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateWishes_throwsIllegalValueException() throws Exception {
        XmlSerializableWishBook dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_WISH_FILE,
                XmlSerializableWishBook.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableWishBook.MESSAGE_DUPLICATE_WISH);
        dataFromFile.toModelType();
    }

}
