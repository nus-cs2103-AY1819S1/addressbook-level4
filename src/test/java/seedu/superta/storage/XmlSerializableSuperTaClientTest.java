package seedu.superta.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.superta.commons.exceptions.IllegalValueException;
import seedu.superta.commons.util.XmlUtil;
import seedu.superta.model.SuperTaClient;
import seedu.superta.testutil.TypicalSuperTaClient;

public class XmlSerializableSuperTaClientTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
                                                           "XmlSerializableSuperTaClientTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.xml");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.xml");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        XmlSerializableSuperTaClient dataFromFile = XmlUtil.getDataFromFile(TYPICAL_PERSONS_FILE,
                XmlSerializableSuperTaClient.class);
        SuperTaClient superTaClientFromFile = dataFromFile.toModelType();
        SuperTaClient typicalPersonsSuperTaClient = TypicalSuperTaClient.getTypicalSuperTaClient();
        assertEquals(superTaClientFromFile, typicalPersonsSuperTaClient);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        XmlSerializableSuperTaClient dataFromFile = XmlUtil.getDataFromFile(INVALID_PERSON_FILE,
                XmlSerializableSuperTaClient.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        XmlSerializableSuperTaClient dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_PERSON_FILE,
                XmlSerializableSuperTaClient.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableSuperTaClient.MESSAGE_DUPLICATE_PERSON);
        dataFromFile.toModelType();
    }

}
