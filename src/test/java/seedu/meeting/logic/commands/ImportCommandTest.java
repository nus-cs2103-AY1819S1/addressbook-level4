package seedu.meeting.logic.commands;

import static seedu.meeting.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.meeting.testutil.TypicalMeetingBook.getTypicalMeetingBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;

import seedu.meeting.commons.exceptions.DataConversionException;
import seedu.meeting.logic.CommandHistory;
import seedu.meeting.model.Model;
import seedu.meeting.model.ModelManager;
import seedu.meeting.model.UserPrefs;
import seedu.meeting.storage.XmlMeetingBookStorage;
import seedu.meeting.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code ExportCommand}.
 */
public class ImportCommandTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlUtilTest");
    private static final Path VALID_FILE = TEST_DATA_FOLDER.resolve("validMeetingBook.xml");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.xml");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.xml");

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalMeetingBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalMeetingBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_import_success() throws IOException, DataConversionException {
        ImportCommand importCommand = new ImportCommand(VALID_FILE);
        XmlMeetingBookStorage expectedStorage = new XmlMeetingBookStorage(VALID_FILE);
        expectedModel.importMeetingBook(expectedStorage.readMeetingBook().get(), false);
        expectedModel.commitMeetingBook();
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
