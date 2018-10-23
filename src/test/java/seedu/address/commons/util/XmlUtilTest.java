package seedu.address.commons.util;

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

import seedu.address.model.AddressBook;
import seedu.address.storage.XmlAdaptedTag;
import seedu.address.storage.XmlAdaptedVolunteer;
import seedu.address.storage.XmlSerializableAddressBook;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.VolunteerBuilder;

public class XmlUtilTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlUtilTest");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.xml");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.xml");
    private static final Path VALID_FILE = TEST_DATA_FOLDER.resolve("validAddressBook.xml");
    private static final Path MISSING_VOLUNTEER_FIELD_FILE = TEST_DATA_FOLDER.resolve("missingVolunteerField.xml");
    private static final Path INVALID_VOLUNTEER_FIELD_FILE = TEST_DATA_FOLDER.resolve("invalidVolunteerField.xml");
    private static final Path VALID_VOLUNTEER_FILE = TEST_DATA_FOLDER.resolve("validVolunteer.xml");
    private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("tempAddressBook.xml");

    private static final String INVALID_PHONE = "9482asf424";

    private static final String VALID_NAME = "Hans Muster";
    private static final String VALID_GENDER = "m";
    private static final String VALID_BIRTHDAY = "22-05-1987";
    private static final String VALID_PHONE = "9482424";
    private static final String VALID_EMAIL = "hans@example";
    private static final String VALID_ADDRESS = "4th street";
    private static final List<XmlAdaptedTag> VALID_VOLUNTEER_TAGS = Collections
            .singletonList(new XmlAdaptedTag("student"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, AddressBook.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, AddressBook.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, AddressBook.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        AddressBook dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableAddressBook.class).toModelType();
        assertEquals(9, dataFromFile.getVolunteerList().size());
    }

    @Test
    public void xmlAdaptedFromFile_fileWithMissingVolunteerField_validResult() throws Exception {
        XmlAdaptedVolunteer actualVolunteer = XmlUtil.getDataFromFile(
                MISSING_VOLUNTEER_FIELD_FILE, XmlAdaptedVolunteerWithRootElement.class);
        XmlAdaptedVolunteer expectedVolunteer = new XmlAdaptedVolunteer(
                null, VALID_GENDER, VALID_BIRTHDAY, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_VOLUNTEER_TAGS);
        assertEquals(expectedVolunteer, actualVolunteer);
    }

    @Test
    public void xmlAdaptedFromFile_fileWithInvalidVolunteerField_validResult() throws Exception {
        XmlAdaptedVolunteer actualVolunteer = XmlUtil.getDataFromFile(
                INVALID_VOLUNTEER_FIELD_FILE, XmlAdaptedVolunteerWithRootElement.class);
        XmlAdaptedVolunteer expectedVolunteer = new XmlAdaptedVolunteer(VALID_NAME, VALID_GENDER, VALID_BIRTHDAY,
                INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_VOLUNTEER_TAGS);
        assertEquals(expectedVolunteer, actualVolunteer);
    }

    @Test
    public void xmlAdaptedFromFile_fileWithValidVolunteer_validResult() throws Exception {
        XmlAdaptedVolunteer actualVolunteer = XmlUtil.getDataFromFile(
                VALID_VOLUNTEER_FILE, XmlAdaptedVolunteerWithRootElement.class);
        XmlAdaptedVolunteer expectedVolunteer = new XmlAdaptedVolunteer(
                VALID_NAME, VALID_GENDER, VALID_BIRTHDAY, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_VOLUNTEER_TAGS);
        assertEquals(expectedVolunteer, actualVolunteer);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new AddressBook());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new AddressBook());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        FileUtil.createFile(TEMP_FILE);
        XmlSerializableAddressBook dataToWrite = new XmlSerializableAddressBook(new AddressBook());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableAddressBook.class);
        assertEquals(dataToWrite, dataFromFile);

        AddressBookBuilder builder = new AddressBookBuilder(new AddressBook());
        dataToWrite = new XmlSerializableAddressBook(
                builder.withVolunteer(new VolunteerBuilder().build()).build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableAddressBook.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to
     * {@code XmlAdaptedVolunteer} objects.
     */
    @XmlRootElement(name = "volunteer")
    private static class XmlAdaptedVolunteerWithRootElement extends XmlAdaptedVolunteer {}
}
