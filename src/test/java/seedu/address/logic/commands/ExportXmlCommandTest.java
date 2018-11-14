package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.VersionedAddressBook;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.XmlAddressBookStorage;
import seedu.address.testutil.TypicalPersons;

/**
 * The test for importXmlCommand integrates with model and storage.
 */
public class ExportXmlCommandTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    private String dirPath;
    private Storage storage;
    private Model model;
    private CommandHistory commandHistory;

    @Before
    public void setUp() {
        dirPath = testFolder.getRoot().getPath() + File.separator;;
        AddressBookStorage addressBookStorage =
                new XmlAddressBookStorage(Paths.get(dirPath + "addressbook.xml"));
        UserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(Paths.get(dirPath + "preference.json"));
        storage = new StorageManager(addressBookStorage, userPrefsStorage);
        model = new ModelManager(TypicalPersons.getTypicalPersonsAddressBook(), new UserPrefs());
        commandHistory = new CommandHistory();
    }

    @Test
    public void execute_validFilePath_success() {
        String filePath = dirPath + "validExport.xml";
        ExportXmlCommand exportXmlCommand =
                new ExportXmlCommand(ParserUtil.parseFilePath(filePath), ExportCommand.FileType.XML);
        exportXmlCommand.setStorage(storage);
        String expectedMessage = String.format(ExportCommand.MESSAGE_SUCCESS, filePath);

        assertCommandSuccess(exportXmlCommand, model, commandHistory, expectedMessage,
                Paths.get(filePath));
    }

    @Test
    public void execute_invalidFilePath_throwsCommandException() {
        // filePath contains invalid character "-"
        String filePath = dirPath + "/desktop/fakefolder/invalid-Export.xml";
        ExportXmlCommand exportXmlCommand =
                new ExportXmlCommand(ParserUtil.parseFilePath(filePath), ExportCommand.FileType.XML);
        exportXmlCommand.setStorage(storage);
        String expectedMessage = String.format(ExportCommand.MESSAGE_INVALID_XML_FILE_PATH);

        assertCommandFailure(exportXmlCommand, model, commandHistory, expectedMessage, filePath);
    }


    @Test
    public void execute_readFileFailure_throwsCommandException() {
        // filePath contains invalid character \0
        String filePath = "/desktop/fakefolder/invalidExport.xml\0";
        ExportXmlCommand exportXmlCommand =
                new ExportXmlCommand(ParserUtil.parseFilePath(filePath), ExportCommand.FileType.XML);
        exportXmlCommand.setStorage(storage);
        String expectedMessage = String.format(ExportCommand.MESSAGE_FAIL_READ_FILE);

        assertCommandFailure(exportXmlCommand, model, commandHistory, expectedMessage, filePath);
    }

    /**
     * Executes {@code command} and in addition, <br>
     * 1. Asserts the result message matches {@code expectedMessage} <br>
     * 2. Asserts the {@code actualCommandHistory} remains unchanged. <br>
     * 3. Asserts the user date stored in {@code model} matches the data stores at {@code filePath}
     */
    public void assertCommandSuccess(ExportXmlCommand command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage, Path filePath) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedCommandHistory, actualCommandHistory);
            assertEquals(actualModel.getAddressBook(),
                    new VersionedAddressBook(storage.readAddressBook(filePath).get()));
        } catch (CommandException | DataConversionException | IOException e) {
            throw new AssertionError("Execution of command should not fail.", e);
        }
    }

    /**
     * Executes {@code command} and in addition, <br>
     * 1. Asserts a {@code CommandException} is thrown <br>
     * 2. Asserts the CommandException message matches {@code expectedMessage} <br>
     * 3. Asserts user data is not saved at {@code filePath}
     */
    public void assertCommandFailure(ExportXmlCommand command, Model actualModel, CommandHistory actualCommandHistory,
                                     String expectedMessage, String filePath) {
        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertFalse((new File(filePath)).exists());
        }
    }

    @Test
    public void equals() {
        String filePath1 = dirPath + "testEqual1.txt";
        String filePath2 = dirPath + "testEqual2.txt";
        ExportXmlCommand commandOne = new ExportXmlCommand(filePath1, ExportCommand.FileType.XML);
        ExportXmlCommand commandTwo = new ExportXmlCommand(filePath1, ExportCommand.FileType.XML);
        ExportXmlCommand commandThree = commandOne;
        ExportXmlCommand commandFour = new ExportXmlCommand(filePath2, ExportCommand.FileType.XML);
        ExportTxtCommand commandFive = new ExportTxtCommand(filePath1, ExportCommand.FileType.TXT);

        // exactly the same command
        assertTrue(commandOne.equals(commandThree));

        // same object type with same file path
        assertTrue(commandOne.equals(commandTwo));

        // same object type with different file path
        assertFalse(commandOne.equals(commandFour));

        // different object type with same file path
        assertFalse(commandOne.equals(commandFive));

    }
}
