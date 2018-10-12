package seedu.learnvocabulary.commons.util;

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

import seedu.learnvocabulary.model.LearnVocabulary;
import seedu.learnvocabulary.storage.XmlAdaptedTag;

import seedu.learnvocabulary.storage.XmlAdaptedWord;
import seedu.learnvocabulary.storage.XmlSerializableLearnVocabulary;
import seedu.learnvocabulary.testutil.LearnVocabularyBuilder;
import seedu.learnvocabulary.testutil.TestUtil;

import seedu.learnvocabulary.testutil.WordBuilder;

public class XmlUtilTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlUtilTest");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.xml");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.xml");
    private static final Path VALID_FILE = TEST_DATA_FOLDER.resolve("validLearnVocabulary.xml");
    private static final Path MISSING_WORD_FIELD_FILE = TEST_DATA_FOLDER.resolve("missingWordField.xml");
    private static final Path INVALID_WORD_FIELD_FILE = TEST_DATA_FOLDER.resolve("invalidWordField.xml");
    private static final Path VALID_WORD_FILE = TEST_DATA_FOLDER.resolve("validWord.xml");
    private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("tempLearnVocabulary.xml");

    private static final String INVALID_PHONE = "9482asf424";

    private static final String VALID_NAME = "Hans Muster";
    private static final String VALID_MEANING = "Test";
    private static final String VALID_PHONE = "9482424";
    private static final String VALID_ADDRESS = "4th street";
    private static final List<XmlAdaptedTag> VALID_TAGS = Collections.singletonList(new XmlAdaptedTag("friends"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, LearnVocabulary.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, LearnVocabulary.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, LearnVocabulary.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        LearnVocabulary dataFromFile = XmlUtil.getDataFromFile
                (VALID_FILE, XmlSerializableLearnVocabulary.class).toModelType();
        assertEquals(9, dataFromFile.getWordList().size());
    }

    @Test
    public void xmlAdaptedWordFromFile_fileWithMissingWordField_validResult() throws Exception {
        XmlAdaptedWord actualWord = XmlUtil.getDataFromFile(
                MISSING_WORD_FIELD_FILE, XmlAdaptedWordWithRootElement.class);
        XmlAdaptedWord expectedWord = new XmlAdaptedWord(
                null, VALID_MEANING, VALID_PHONE, VALID_ADDRESS, VALID_TAGS);
        assertEquals(expectedWord, actualWord);
    }

    @Test
    public void xmlAdaptedWordFromFile_fileWithInvalidWordField_validResult() throws Exception {
        XmlAdaptedWord actualWord = XmlUtil.getDataFromFile(
                INVALID_WORD_FIELD_FILE, XmlAdaptedWordWithRootElement.class);
        XmlAdaptedWord expectedWord = new XmlAdaptedWord(
                VALID_NAME, VALID_MEANING, INVALID_PHONE, VALID_ADDRESS, VALID_TAGS);
        assertEquals(expectedWord, actualWord);
    }

    @Test
    public void xmlAdaptedWordFromFile_fileWithValidWord_validResult() throws Exception {
        XmlAdaptedWord actualWord = XmlUtil.getDataFromFile(
                VALID_WORD_FILE, XmlAdaptedWordWithRootElement.class);
        XmlAdaptedWord expectedWord = new XmlAdaptedWord(
                VALID_NAME, VALID_MEANING, VALID_PHONE, VALID_ADDRESS, VALID_TAGS);
        assertEquals(expectedWord, actualWord);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new LearnVocabulary());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new LearnVocabulary());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        FileUtil.createFile(TEMP_FILE);
        XmlSerializableLearnVocabulary dataToWrite = new XmlSerializableLearnVocabulary(new LearnVocabulary());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableLearnVocabulary dataFromFile =
                XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableLearnVocabulary.class);
        assertEquals(dataToWrite, dataFromFile);

        LearnVocabularyBuilder builder = new LearnVocabularyBuilder(new LearnVocabulary());
        dataToWrite = new XmlSerializableLearnVocabulary(
                builder.withWord(new WordBuilder().build()).build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableLearnVocabulary.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAdaptedWord}
     * objects.
     */
    @XmlRootElement(name = "word")
    private static class XmlAdaptedWordWithRootElement extends XmlAdaptedWord {}
}
