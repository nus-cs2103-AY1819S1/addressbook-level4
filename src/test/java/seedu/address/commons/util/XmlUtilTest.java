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
import seedu.address.model.TriviaBundle;
import seedu.address.storage.XmlAdaptedCard;
import seedu.address.storage.XmlAdaptedPerson;
import seedu.address.storage.XmlAdaptedTopic;
import seedu.address.storage.XmlSerializableAddressBook;
import seedu.address.storage.XmlSerializableTriviaBundle;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.CardBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TriviaBundleBuilder;
import seedu.address.testutil.TypicalCards;

public class XmlUtilTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlUtilTest");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.xml");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.xml");
    private static final Path VALID_FILE = TEST_DATA_FOLDER.resolve("validAddressBook.xml");
    private static final Path VALID_TRIVIA_FILE = TEST_DATA_FOLDER.resolve("validTriviaBundle.xml");
    private static final Path MISSING_PERSON_FIELD_FILE = TEST_DATA_FOLDER.resolve("missingPersonField.xml");
    private static final Path MISSING_CARD_FIELD_FILE = TEST_DATA_FOLDER.resolve("missingCardField.xml");
    private static final Path INVALID_PERSON_FIELD_FILE = TEST_DATA_FOLDER.resolve("invalidPersonField.xml");
    private static final Path INVALID_CARD_FIELD_FILE = TEST_DATA_FOLDER.resolve("invalidCardField.xml");
    private static final Path VALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("validPerson.xml");
    private static final Path VALID_CARD_FILE = TEST_DATA_FOLDER.resolve("validCard.xml");
    private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("tempAddressBook.xml");
    private static final Path TEMP_TRIVIA_FILE = TestUtil.getFilePathInSandboxFolder("tempTriviaBundle.xml");

    private static final String INVALID_PHONE = "9482asf424";
    private static final String INVALID_QUESTION = "";

    private static final String VALID_NAME = "Hans Muster";
    private static final String VALID_PHONE = "9482424";
    private static final String VALID_EMAIL = "hans@example";
    private static final String VALID_ADDRESS = "4th street";
    private static final List<XmlAdaptedTopic> VALID_TAGS = Collections.singletonList(new XmlAdaptedTopic("friends"));

    private static final String VALID_QUESTION = "Why is the earth round?";
    private static final String VALID_ANSWER = "Because of gravity!";
    private static final List<XmlAdaptedTopic> VALID_TOPIC = Collections.singletonList(new XmlAdaptedTopic("Physics"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, AddressBook.class);
    }

    @Test
    public void getDataFromTriviaFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, TriviaBundle.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromTriviaFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_TRIVIA_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, AddressBook.class);
    }

    @Test
    public void getDataFromTriviaFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, TriviaBundle.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, AddressBook.class);
    }

    @Test
    public void getDataFromTriviaFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, TriviaBundle.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        AddressBook dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableAddressBook.class).toModelType();
        assertEquals(9, dataFromFile.getPersonList().size());
    }

    @Test
    public void getDataFromTriviaFile_validFile_validResult() throws Exception {
        TriviaBundle dataFromFile = XmlUtil.getDataFromFile(VALID_TRIVIA_FILE, XmlSerializableTriviaBundle.class)
                .toModelType();
        assertEquals(TypicalCards.getTypicalTriviaBundle(), dataFromFile);
    }

    @Test
    public void xmlAdaptedPersonFromFile_fileWithMissingPersonField_validResult() throws Exception {
        XmlAdaptedPerson actualPerson = XmlUtil.getDataFromFile(
                MISSING_PERSON_FIELD_FILE, XmlAdaptedPersonWithRootElement.class);
        XmlAdaptedPerson expectedPerson = new XmlAdaptedPerson(
                null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        assertEquals(expectedPerson, actualPerson);
    }

    @Test
    public void xmlAdaptedCardFromFile_fileWithMissingCardField_validResult() throws Exception {
        XmlAdaptedCard actualCard = XmlUtil.getDataFromFile(
                MISSING_CARD_FIELD_FILE, XmlAdaptedCardWithRootElement.class);
        XmlAdaptedCard expectedCard = new XmlAdaptedCard(null, VALID_ANSWER, VALID_TOPIC);
        assertEquals(expectedCard, actualCard);
    }

    @Test
    public void xmlAdaptedPersonFromFile_fileWithInvalidPersonField_validResult() throws Exception {
        XmlAdaptedPerson actualPerson = XmlUtil.getDataFromFile(
                INVALID_PERSON_FIELD_FILE, XmlAdaptedPersonWithRootElement.class);
        XmlAdaptedPerson expectedPerson = new XmlAdaptedPerson(
                VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        assertEquals(expectedPerson, actualPerson);
    }

    @Test
    public void xmlAdaptedCardFromFile_fileWithInvalidCardField_validResult() throws Exception {
        XmlAdaptedCard actualCard = XmlUtil.getDataFromFile(
                INVALID_CARD_FIELD_FILE, XmlAdaptedCardWithRootElement.class);
        XmlAdaptedCard expectedCard = new XmlAdaptedCard(INVALID_QUESTION, VALID_ANSWER, VALID_TOPIC);
        assertEquals(expectedCard, actualCard);
    }

    @Test
    public void xmlAdaptedPersonFromFile_fileWithValidPerson_validResult() throws Exception {
        XmlAdaptedPerson actualPerson = XmlUtil.getDataFromFile(
                VALID_PERSON_FILE, XmlAdaptedPersonWithRootElement.class);
        XmlAdaptedPerson expectedPerson = new XmlAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        assertEquals(expectedPerson, actualPerson);
    }

    @Test
    public void xmlAdaptedCardFromFile_fileWithValidCard_validResult() throws Exception {
        XmlAdaptedCard actualCard = XmlUtil.getDataFromFile(
                VALID_CARD_FILE, XmlAdaptedCardWithRootElement.class);
        XmlAdaptedCard expectedCard = new XmlAdaptedCard(VALID_QUESTION, VALID_ANSWER, VALID_TOPIC);
        assertEquals(expectedCard, actualCard);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new AddressBook());
    }

    @Test
    public void saveDataToTriviaFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new TriviaBundle());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToTriviaFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_TRIVIA_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new AddressBook());
    }

    @Test
    public void saveDataToTriviaFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new TriviaBundle());
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
                builder.withPerson(new PersonBuilder().build()).build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableAddressBook.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    @Test
    public void saveDataToTriviaFile_validFile_dataSaved() throws Exception {
        FileUtil.createFile(TEMP_TRIVIA_FILE);
        XmlSerializableTriviaBundle dataToWrite = new XmlSerializableTriviaBundle(new TriviaBundle());
        XmlUtil.saveDataToFile(TEMP_TRIVIA_FILE, dataToWrite);
        XmlSerializableTriviaBundle dataFromFile = XmlUtil.getDataFromFile(TEMP_TRIVIA_FILE,
                XmlSerializableTriviaBundle.class);
        assertEquals(dataToWrite, dataFromFile);

        TriviaBundleBuilder builder = new TriviaBundleBuilder(new TriviaBundle());
        dataToWrite = new XmlSerializableTriviaBundle(
                builder.withCard(new CardBuilder().build()).build());

        XmlUtil.saveDataToFile(TEMP_TRIVIA_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_TRIVIA_FILE, XmlSerializableTriviaBundle.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAdaptedPerson}
     * objects.
     */
    @XmlRootElement(name = "person")
    private static class XmlAdaptedPersonWithRootElement extends XmlAdaptedPerson {}

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAdaptedCard}
     * objects.
     */
    @XmlRootElement(name = "cards")
    private static class XmlAdaptedCardWithRootElement extends XmlAdaptedCard {}
}
