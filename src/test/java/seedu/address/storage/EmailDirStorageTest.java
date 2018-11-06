package seedu.address.storage;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

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

    private Set<String> readEmailFiles(String filePath) {
        return new EmailDirStorage(Paths.get(filePath)).readEmailFiles(Paths.get(filePath));
    }
}
