package seedu.inventory.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.inventory.testutil.TypicalStaffs.ANGZHIKAI;
import static seedu.inventory.testutil.TypicalStaffs.CHUAENGSOON;
import static seedu.inventory.testutil.TypicalStaffs.WANGCHAO;
import static seedu.inventory.testutil.TypicalStaffs.getTypicalStaffList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.inventory.commons.exceptions.DataConversionException;
import seedu.inventory.model.ReadOnlyStaffList;
import seedu.inventory.model.StaffList;

public class XmlStaffListStorageTest {
    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "XmlStaffListStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readStaffList_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readStaffList(null);
    }

    private Optional<ReadOnlyStaffList> readStaffList(String filePath) throws Exception {
        return new XmlStaffListStorage(Paths.get(filePath)).readStaffList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefssFileInTestDataFolder) {
        return prefssFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefssFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readStaffList("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {
        thrown.expect(DataConversionException.class);
        readStaffList("NotXmlFormatStaffList.xml");
    }

    @Test
    public void readStaffList_invalidStaffStaffList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readStaffList("invalidStaffStaffList.xml");
    }

    @Test
    public void readStaffList_invalidAndValidStaffStaffList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readStaffList("invalidAndValidStaffStaffList.xml");
    }

    @Test
    public void readAndSaveStaffList_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempStaffList.xml");
        StaffList original = getTypicalStaffList();
        XmlStaffListStorage xmlStaffListStorage = new XmlStaffListStorage(filePath);

        xmlStaffListStorage.saveStaffList(original, filePath);
        ReadOnlyStaffList readBack = xmlStaffListStorage.readStaffList(filePath).get();
        assertEquals(original, new StaffList(readBack));

        original.addStaff(ANGZHIKAI);
        original.removeStaff(WANGCHAO);
        xmlStaffListStorage.saveStaffList(original, filePath);
        readBack = xmlStaffListStorage.readStaffList(filePath).get();
        assertEquals(original, new StaffList(readBack));

        original.addStaff(CHUAENGSOON);
        xmlStaffListStorage.saveStaffList(original);
        readBack = xmlStaffListStorage.readStaffList().get();
        assertEquals(original, new StaffList(readBack));
    }

    @Test
    public void saveStaffList_nullStaffList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveStaffList(null, "SomeFile.xml");
    }

    /**
     * Saves staff list into the given file path.
     *
     * @param staffList list of staffs to be stored
     * @param filePath file to store the data
     */
    private void saveStaffList(ReadOnlyStaffList staffList, String filePath) {
        try {
            new XmlStaffListStorage(Paths.get(filePath))
                    .saveStaffList(staffList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveInventory_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveStaffList(new StaffList(), null);
    }
}
