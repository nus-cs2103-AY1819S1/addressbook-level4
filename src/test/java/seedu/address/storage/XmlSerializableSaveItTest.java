package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.SaveIt;
import seedu.address.testutil.TypicalPersons;

public class XmlSerializableSaveItTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableSaveItTest");
    private static final Path TYPICAL_ISSUES_FILE = TEST_DATA_FOLDER.resolve("typicalIssuesSaveIt.xml");
    private static final Path DUPLICATE_ISSUE_FILE = TEST_DATA_FOLDER.resolve("duplicateIssuesSaveIt.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        XmlSerializableSaveIt dataFromFile = XmlUtil.getDataFromFile(TYPICAL_ISSUES_FILE,
                XmlSerializableSaveIt.class);
        SaveIt saveItFromFile = dataFromFile.toModelType();
        SaveIt typicalIssuesSaveIt = TypicalPersons.getTypicalSaveIt();
        assertEquals(saveItFromFile, typicalIssuesSaveIt);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        XmlSerializableSaveIt dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_ISSUE_FILE,
                XmlSerializableSaveIt.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableSaveIt.MESSAGE_DUPLICATE_PERSON);
        dataFromFile.toModelType();
    }

}
