package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ImportContactsCommandTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "ImportContactsTest");
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    private Optional<String> readFile(String contactsFileInTestDataFolder) {
        Path csvFilePath = addToTestDataPathIfNotNull(contactsFileInTestDataFolder);
        return Optional.ofNullable(csvFilePath.toString());
    }

    private Path addToTestDataPathIfNotNull(String contactsFileInTestDataFolder) {
        return contactsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(contactsFileInTestDataFolder)
                : null;
    }

    @Test
    public void execute_importContactsCommand() {
        assertCommandFailure(new ImportContactsCommand(readFile("importContacts.csv").get()),
                model, commandHistory, ImportContactsCommand.MESSAGE_TEST_EXCEPTION);
    }
}
