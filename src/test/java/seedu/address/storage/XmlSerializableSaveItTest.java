package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.saveit.commons.exceptions.IllegalValueException;
import seedu.saveit.commons.util.XmlUtil;
import seedu.saveit.model.SaveIt;
import seedu.address.testutil.TypicalPersons;
import seedu.saveit.storage.XmlSerializableSaveIt;

public class XmlSerializableSaveItTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableSaveItTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsSaveIt.xml");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonSaveIt.xml");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonSaveIt.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        XmlSerializableSaveIt dataFromFile = XmlUtil.getDataFromFile(TYPICAL_PERSONS_FILE,
                XmlSerializableSaveIt.class);
        SaveIt saveItFromFile = dataFromFile.toModelType();
        SaveIt typicalPersonsSaveIt = TypicalPersons.getTypicalSaveIt();
        assertEquals(saveItFromFile, typicalPersonsSaveIt);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        XmlSerializableSaveIt dataFromFile = XmlUtil.getDataFromFile(INVALID_PERSON_FILE,
                XmlSerializableSaveIt.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        XmlSerializableSaveIt dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_PERSON_FILE,
                XmlSerializableSaveIt.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableSaveIt.MESSAGE_DUPLICATE_PERSON);
        dataFromFile.toModelType();
    }

}
