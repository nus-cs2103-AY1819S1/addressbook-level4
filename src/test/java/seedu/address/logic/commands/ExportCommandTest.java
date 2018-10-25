package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;
import java.nio.file.Path;

import org.junit.Test;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.ExportCommandParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ExportCommandTest {
    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_emptyAddressBook_success() {
        Path path = new File("C://Users/Documents").toPath();
        Model modelEmpty = new ModelManager();
        Model expectedModelEmpty = new ModelManager();
        ExportCommand exportCommand = new ExportCommand(path);

        String expectedMessage = String.format(ExportCommand.MESSAGE_SUCCESS, path);

        assertCommandSuccess(exportCommand, modelEmpty, commandHistory, expectedMessage, expectedModelEmpty);
    }

}