package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.simplejavamail.email.Email;

import seedu.address.model.EmailModel;
import seedu.address.testutil.DefaultEmailBuilder;

//@@author EatOrBeEaten
public class EmailDirStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "EmailDirStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readEmailFiles_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readEmailFiles(null);
    }

    @Test
    public void readEmailFiles_missingDirectory_returnsEmptySet() {
        assertEquals(readEmailFiles("nonExistentDirectory"), new HashSet<>());
    }

    @Test
    public void readEmailFiles_emptyDirectory_returnsEmptySet() {
        String filePath = testFolder.getRoot().toPath().toString();
        assertEquals(readEmailFiles(filePath), new HashSet<>());
    }

    private Set<String> readEmailFiles(String filePath) {
        return new EmailDirStorage(Paths.get(filePath)).readEmailFiles(Paths.get(filePath));
    }

//    @Test
//    public void loadEmail_allInOrder_success() throws Exception {
//        Email validEmail = new DefaultEmailBuilder().build();
//        EmailModel emailModel = new EmailModel();
//        emailModel.saveComposedEmail(validEmail);
//        Path filePath = testFolder.getRoot().toPath();
//        EmailDirStorage emailDirStorage = new EmailDirStorage(filePath);
//
//        // Save in a new email and read back
//        emailDirStorage.saveEmail(emailModel);
//        Email readBack = emailDirStorage.loadEmail(validEmail.getSubject());
//        assertEquals(validEmail, readBack);
//    }
}
