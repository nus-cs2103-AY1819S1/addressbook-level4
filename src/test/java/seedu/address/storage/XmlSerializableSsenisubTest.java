package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.Ssenisub;
import seedu.address.testutil.TypicalPersons;

public class XmlSerializableSsenisubTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableSsenisubTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsSsenisub.xml");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonSsenisub.xml");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonSsenisub.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        XmlSerializableSsenisub dataFromFile = XmlUtil.getDataFromFile(TYPICAL_PERSONS_FILE,
                XmlSerializableSsenisub.class);
        Ssenisub ssenisubFromFile = dataFromFile.toModelType();
        Ssenisub typicalPersonsSsenisub = TypicalPersons.getTypicalSsenisub();
        assertEquals(ssenisubFromFile, typicalPersonsSsenisub);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        XmlSerializableSsenisub dataFromFile = XmlUtil.getDataFromFile(INVALID_PERSON_FILE,
                XmlSerializableSsenisub.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        XmlSerializableSsenisub dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_PERSON_FILE,
                XmlSerializableSsenisub.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableSsenisub.MESSAGE_DUPLICATE_PERSON);
        dataFromFile.toModelType();
    }

}
