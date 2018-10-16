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
import seedu.address.model.filereader.FileReader;
import seedu.address.testutil.FileReaderBuilder;

public class ImportContactsCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_importContactsCommand() {
        FileReader fileReader = new FileReaderBuilder().build();
        assertCommandFailure(new ImportContactsCommand(fileReader),
                model, commandHistory, ImportContactsCommand.MESSAGE_TEST_EXCEPTION);
    }

    @Test
    public void execute_emptyCsvFile_throwsCommandException() {
        FileReader emptyFileReader = new FileReaderBuilder().empty().build();
        assertCommandFailure(new ImportContactsCommand(emptyFileReader),
                model, commandHistory, ImportContactsCommand.MESSAGE_EMPTY_FILE_EXCEPTION);
    }
}
