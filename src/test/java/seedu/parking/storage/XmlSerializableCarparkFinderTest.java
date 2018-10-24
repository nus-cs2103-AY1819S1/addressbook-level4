package seedu.parking.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.parking.commons.exceptions.IllegalValueException;
import seedu.parking.commons.util.XmlUtil;
import seedu.parking.model.CarparkFinder;
import seedu.parking.testutil.TypicalCarparks;

public class XmlSerializableCarparkFinderTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableCarparkFinderTest");
    private static final Path TYPICAL_CARPARKS_FILE = TEST_DATA_FOLDER.resolve("typicalCarparksCarparkFinder.xml");
    private static final Path INVALID_CARPARK_FILE = TEST_DATA_FOLDER.resolve("invalidCarparkCarparkFinder.xml");
    private static final Path DUPLICATE_CARPARK_FILE = TEST_DATA_FOLDER.resolve("duplicateCarparkCarparkFinder.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalCarparksFile_success() throws Exception {
        XmlSerializableCarparkFinder dataFromFile = XmlUtil.getDataFromFile(TYPICAL_CARPARKS_FILE,
                XmlSerializableCarparkFinder.class);
        CarparkFinder carparkFinderFromFile = dataFromFile.toModelType();
        CarparkFinder typicalCarparksCarparkFinder = TypicalCarparks.getTypicalCarparkFinder();
        assertEquals(carparkFinderFromFile, typicalCarparksCarparkFinder);
    }

    @Test
    public void toModelType_invalidCarparkFile_throwsIllegalValueException() throws Exception {
        XmlSerializableCarparkFinder dataFromFile = XmlUtil.getDataFromFile(INVALID_CARPARK_FILE,
                XmlSerializableCarparkFinder.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateCarparks_throwsIllegalValueException() throws Exception {
        XmlSerializableCarparkFinder dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_CARPARK_FILE,
                XmlSerializableCarparkFinder.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableCarparkFinder.MESSAGE_DUPLICATE_CARPARK);
        dataFromFile.toModelType();
    }

}
