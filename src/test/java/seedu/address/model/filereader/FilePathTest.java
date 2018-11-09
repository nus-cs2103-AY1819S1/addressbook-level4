package seedu.address.model.filereader;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;
import seedu.address.testutil.FileReaderBuilder;

public class FilePathTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new FilePath(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidFilePath = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new FilePath(invalidFilePath));
    }

    @Test
    public void isValidPath() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> FilePath.isValidPath(null));

        // invalid addresses
        assertFalse(FilePath.isValidPath("")); // empty string
        assertFalse(FilePath.isValidPath(" ")); // spaces only

        // valid addresses
        assertTrue(FilePath.isValidPath(FileReaderBuilder.DEFAULT_CSV_FILE_PATH));
    }
}