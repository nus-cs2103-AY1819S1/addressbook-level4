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

import seedu.address.model.SaveIt;
import seedu.address.storage.XmlAdaptedIssue;
import seedu.address.storage.XmlAdaptedSolution;
import seedu.address.storage.XmlAdaptedTag;
import seedu.address.storage.XmlSerializableSaveIt;
import seedu.address.testutil.IssueBuilder;
import seedu.address.testutil.SaveItBuilder;
import seedu.address.testutil.TestUtil;

public class XmlUtilTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlUtilTest");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.xml");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.xml");
    private static final Path VALID_FILE = TEST_DATA_FOLDER.resolve("validSaveIt.xml");
    private static final Path MISSING_ISSUE_FIELD_FILE = TEST_DATA_FOLDER.resolve("missingIssueField.xml");
    private static final Path INVALID_ISSUE_FIELD_FILE = TEST_DATA_FOLDER.resolve("invalidIssueField.xml");
    private static final Path VALID_ISSUE_FILE = TEST_DATA_FOLDER.resolve("validIssue.xml");
    private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("tempSaveIt.xml");

    private static final String INVALID_DESCRIPTION = " ";

    private static final String VALID_STATEMENT = "Hans Muster";
    private static final String VALID_DESCRIPTION = "9482424";
    private static final List<XmlAdaptedSolution> VALID_SOLUTIONS = Collections
            .singletonList(new XmlAdaptedSolution("webSite remark"));
    private static final List<XmlAdaptedTag> VALID_TAGS = Collections
            .singletonList(new XmlAdaptedTag("friends"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, SaveIt.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, SaveIt.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, SaveIt.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        SaveIt dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableSaveIt.class).toModelType();
        assertEquals(9, dataFromFile.getIssueList().size());
    }

    @Test
    public void xmlAdaptedIssueFromFile_fileWithMissingIssueField_validResult() throws Exception {
        XmlAdaptedIssue actualIssue = XmlUtil.getDataFromFile(
            MISSING_ISSUE_FIELD_FILE, XmlAdaptedIssueWithRootElement.class);
        XmlAdaptedIssue expectedIssue = new XmlAdaptedIssue(
                null, VALID_DESCRIPTION, VALID_SOLUTIONS, VALID_TAGS);
        assertEquals(expectedIssue, actualIssue);
    }

    @Test
    public void xmlAdaptedIssueFromFile_fileWithInvalidIssueField_validResult() throws Exception {
        XmlAdaptedIssue actualIssue = XmlUtil.getDataFromFile(
            INVALID_ISSUE_FIELD_FILE, XmlAdaptedIssueWithRootElement.class);
        XmlAdaptedIssue expectedIssue = new XmlAdaptedIssue(
                VALID_STATEMENT, INVALID_DESCRIPTION, VALID_SOLUTIONS, VALID_TAGS);
        assertEquals(expectedIssue, actualIssue);
    }

    @Test
    public void xmlAdaptedIssueFromFile_fileWithValidIssue_validResult() throws Exception {
        XmlAdaptedIssue actualIssue = XmlUtil.getDataFromFile(
            VALID_ISSUE_FILE, XmlAdaptedIssueWithRootElement.class);
        XmlAdaptedIssue expectedIssue = new XmlAdaptedIssue(
                VALID_STATEMENT, VALID_DESCRIPTION, VALID_SOLUTIONS, VALID_TAGS);
        assertEquals(expectedIssue, actualIssue);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new SaveIt());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new SaveIt());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        FileUtil.createFile(TEMP_FILE);
        XmlSerializableSaveIt dataToWrite = new XmlSerializableSaveIt(new SaveIt());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableSaveIt dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableSaveIt.class);
        assertEquals(dataToWrite, dataFromFile);

        SaveItBuilder builder = new SaveItBuilder(new SaveIt());
        dataToWrite = new XmlSerializableSaveIt(
                builder.withIssue(new IssueBuilder().build()).build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableSaveIt.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code
     * XmlAdaptedIssue} objects.
     */
    @XmlRootElement(name = "issue")
    private static class XmlAdaptedIssueWithRootElement extends XmlAdaptedIssue {

    }
}
