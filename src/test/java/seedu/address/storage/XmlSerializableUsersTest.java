package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.UserData;
import seedu.address.testutil.TypicalUsers;

public class XmlSerializableUsersTest {

    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "XmlSerializableUsersTest");
    private static final Path TYPICAL_USERS_FILE = TEST_DATA_FOLDER.resolve("typicalUsers.xml");
    private static final Path INVALID_USERS_FILE = TEST_DATA_FOLDER.resolve("invalidUsers.xml");
    private static final Path DUPLICATE_USERS_FILE = TEST_DATA_FOLDER
            .resolve("duplicateUsers.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalUsersFile_success() throws Exception {
        XmlSerializableUsers dataFromFile = XmlUtil.getDataFromFile(TYPICAL_USERS_FILE,
                XmlSerializableUsers.class);
        UserData userDataFromFile = dataFromFile.toModelType();
        UserData typicalUsers = TypicalUsers.getTypicalUserData();
        assertEquals(userDataFromFile, typicalUsers);
    }

    @Test
    public void toModelType_invalidUsersFile_throwsIllegalValueException() throws Exception {
        XmlSerializableUsers dataFromFile = XmlUtil.getDataFromFile(INVALID_USERS_FILE,
                XmlSerializableUsers.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateUsers_throwsIllegalValueException() throws Exception {
        XmlSerializableUsers dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_USERS_FILE,
                XmlSerializableUsers.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableUsers.MESSAGE_DUPLICATE_PERSON);
        dataFromFile.toModelType();
    }

}
