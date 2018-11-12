package seedu.thanepark.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.thanepark.commons.exceptions.IllegalValueException;
import seedu.thanepark.commons.util.XmlUtil;
import seedu.thanepark.model.ThanePark;
import seedu.thanepark.testutil.TypicalRides;

public class XmlSerializableThaneParkTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableThaneParkTest");
    private static final Path TYPICAL_RIDES_FILE = TEST_DATA_FOLDER.resolve("typicalRideThanePark.xml");
    private static final Path INVALID_RIDE_FILE = TEST_DATA_FOLDER.resolve("invalidRideThanePark.xml");
    private static final Path DUPLICATE_RIDE_FILE = TEST_DATA_FOLDER.resolve("duplicateRideThanePark.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalRidesFile_success() throws Exception {
        XmlSerializableThanePark dataFromFile = XmlUtil.getDataFromFile(TYPICAL_RIDES_FILE,
                XmlSerializableThanePark.class);
        ThanePark thaneParkFromFile = dataFromFile.toModelType();
        ThanePark typicalRidesThanePark = TypicalRides.getTypicalThanePark();
        assertEquals(thaneParkFromFile, typicalRidesThanePark);
    }

    @Test
    public void toModelType_invalidRideFile_throwsIllegalValueException() throws Exception {
        XmlSerializableThanePark dataFromFile = XmlUtil.getDataFromFile(INVALID_RIDE_FILE,
                XmlSerializableThanePark.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateRides_throwsIllegalValueException() throws Exception {
        XmlSerializableThanePark dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_RIDE_FILE,
                XmlSerializableThanePark.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableThanePark.MESSAGE_DUPLICATE_RIDE);
        dataFromFile.toModelType();
    }

}
