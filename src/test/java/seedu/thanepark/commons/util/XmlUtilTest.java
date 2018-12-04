package seedu.thanepark.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.thanepark.model.ThanePark;
import seedu.thanepark.storage.XmlAdaptedRide;
import seedu.thanepark.storage.XmlAdaptedTag;
import seedu.thanepark.storage.XmlSerializableThanePark;
import seedu.thanepark.testutil.RideBuilder;
import seedu.thanepark.testutil.TestUtil;
import seedu.thanepark.testutil.ThaneParkBuilder;

public class XmlUtilTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlUtilTest");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.xml");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.xml");
    private static final Path VALID_FILE = TEST_DATA_FOLDER.resolve("validThanePark.xml");
    private static final Path MISSING_RIDE_FIELD_FILE = TEST_DATA_FOLDER.resolve("missingRideField.xml");
    private static final Path INVALID_RIDE_FIELD_FILE = TEST_DATA_FOLDER.resolve("invalidRideField.xml");
    private static final Path VALID_RIDE_FILE = TEST_DATA_FOLDER.resolve("validRide.xml");
    private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("tempThanePark.xml");

    private static final String INVALID_MAINTENANCE = "9482asf424";

    private static final String VALID_NAME = "Haunted Mansion";
    private static final String VALID_MAINTENANCE = "9482424";
    private static final String VALID_WAIT_TIME = "13";
    private static final String VALID_ADDRESS = "4th street";
    private static final List<XmlAdaptedTag> VALID_TAGS = Collections.singletonList(new XmlAdaptedTag("friends"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, ThanePark.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, ThanePark.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, ThanePark.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        ThanePark dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableThanePark.class).toModelType();
        assertEquals(9, dataFromFile.getRideList().size());
    }

    @Test
    public void xmlAdaptedRideFromFile_fileWithMissingRideField_validResult() throws Exception {
        XmlAdaptedRide actualRide = XmlUtil.getDataFromFile(
                MISSING_RIDE_FIELD_FILE, XmlAdaptedRideWithRootElement.class);
        XmlAdaptedRide expectedRide = new XmlAdaptedRide(
                null, VALID_MAINTENANCE, VALID_WAIT_TIME, VALID_ADDRESS, VALID_TAGS);
        assertEquals(expectedRide, actualRide);
    }

    @Test
    public void xmlAdaptedRideFromFile_fileWithInvalidRideField_validResult() throws Exception {
        XmlAdaptedRide actualRide = XmlUtil.getDataFromFile(
                INVALID_RIDE_FIELD_FILE, XmlAdaptedRideWithRootElement.class);
        XmlAdaptedRide expectedRide = new XmlAdaptedRide(
                VALID_NAME, INVALID_MAINTENANCE, VALID_WAIT_TIME, VALID_ADDRESS, VALID_TAGS);
        assertEquals(expectedRide, actualRide);
    }

    @Test
    public void xmlAdaptedRideFromFile_fileWithValidRide_validResult() throws Exception {
        XmlAdaptedRide actualRide = XmlUtil.getDataFromFile(
                VALID_RIDE_FILE, XmlAdaptedRideWithRootElement.class);
        XmlAdaptedRide expectedRide = new XmlAdaptedRide(
                VALID_NAME, VALID_MAINTENANCE, VALID_WAIT_TIME, VALID_ADDRESS, VALID_TAGS);
        assertEquals(expectedRide, actualRide);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new ThanePark());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new ThanePark());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        FileUtil.createFile(TEMP_FILE);
        XmlSerializableThanePark dataToWrite = new XmlSerializableThanePark(new ThanePark());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableThanePark dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableThanePark.class);
        assertEquals(dataToWrite, dataFromFile);

        ThaneParkBuilder builder = new ThaneParkBuilder(new ThanePark());
        dataToWrite = new XmlSerializableThanePark(
                builder.withRide(new RideBuilder().build()).build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableThanePark.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAdaptedRide}
     * objects.
     */
    @XmlRootElement(name = "ride")
    private static class XmlAdaptedRideWithRootElement extends XmlAdaptedRide {}
}
