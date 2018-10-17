package seedu.inventory.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.commons.util.XmlUtil;
import seedu.inventory.model.StaffList;
import seedu.inventory.testutil.TypicalStaffs;

public class XmlSerializableStaffListTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableStaffListTest");
    private static final Path TYPICAL_STAFFS_FILE = TEST_DATA_FOLDER.resolve("typicalStaffsStaffList.xml");
    private static final Path INVALID_STAFF_FILE = TEST_DATA_FOLDER.resolve("invalidStaffStaffList.xml");
    private static final Path DUPLICATE_STAFF_FILE = TEST_DATA_FOLDER.resolve("duplicateStaffStaffList.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalStaffsFile_success() throws Exception {
        XmlSerializableStaffList dataFromFile = XmlUtil.getDataFromFile(TYPICAL_STAFFS_FILE,
                XmlSerializableStaffList.class);
        StaffList staffListFromFile = dataFromFile.toModelType();
        StaffList typicalStaffsStaffList = TypicalStaffs.getTypicalStaffList();
        assertEquals(staffListFromFile, typicalStaffsStaffList);
    }

    @Test
    public void toModelType_invalidStaffFile_throwsIllegalValueException() throws Exception {
        XmlSerializableStaffList dataFromFile = XmlUtil.getDataFromFile(INVALID_STAFF_FILE,
                XmlSerializableStaffList.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateStaffs_throwsIllegalValueException() throws Exception {
        XmlSerializableStaffList dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_STAFF_FILE,
                XmlSerializableStaffList.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableStaffList.MESSAGE_DUPLICATE_STAFF);
        dataFromFile.toModelType();
    }
}
