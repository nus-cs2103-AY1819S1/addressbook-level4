package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalMeetingBook.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.storage.XmlMeetingBookStorage;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code ExportCommand}.
 */
public class ImportCommandTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlUtilTest");
    private static final Path VALID_FILE = TEST_DATA_FOLDER.resolve("validAddressBook.xml");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.xml");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.xml");

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_import_success() throws IOException, DataConversionException {
        ImportCommand importCommand = new ImportCommand(VALID_FILE);
        XmlMeetingBookStorage expectedStorage = new XmlMeetingBookStorage(VALID_FILE);
        expectedModel.importAddressBook(expectedStorage.readAddressBook().get(), false);
        expectedModel.commitAddressBook();
        assertCommandSuccess(importCommand, model, commandHistory,
                String.format(ImportCommand.MESSAGE_SUCCESS, VALID_FILE.toString()), expectedModel);
    }

    @Test
    public void execute_importDataFormatMismatchException_failure() {
        ImportCommand importCommand = new ImportCommand(EMPTY_FILE);
        assertCommandSuccess(importCommand, model, commandHistory, ImportCommand.MESSAGE_FAIL_DATA, expectedModel);
    }

    @Test
    public void execute_importOptionalEmpty_failure() {
        ImportCommand importCommand = new ImportCommand(MISSING_FILE);
        assertCommandSuccess(importCommand, model, commandHistory, ImportCommand.MESSAGE_FAIL_NOFILE, expectedModel);
    }

}
