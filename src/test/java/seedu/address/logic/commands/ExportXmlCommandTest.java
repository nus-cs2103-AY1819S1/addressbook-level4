package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

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
                new XmlAddressBookStorage(ParserUtil.parseFilePath(dirPath + "addressbook.xml"));
        UserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(ParserUtil.parseFilePath(dirPath + "preference.json"));
        storage = new StorageManager(addressBookStorage, userPrefsStorage);
        model = new ModelManager(TypicalPersons.getTypicalPersonsAddressBook(), new UserPrefs());
        commandHistory = new CommandHistory();
    }

    @Test
    public void execute_validFilePath_success() {
        String filePath = dirPath + "validExport.xml";
        ExportXmlCommand exportXmlCommand = new ExportXmlCommand(ParserUtil.parseFilePath(filePath));
        exportXmlCommand.setStorage(storage);
        String expectedMessage = String.format(ExportCommand.MESSAGE_SUCCESS);

        assertCommandSuccess(exportXmlCommand, model, commandHistory, expectedMessage,
                ParserUtil.parseFilePath(filePath));
    }

    @Test
    public void execute_invalidFilePath_throwsCommandException() {
        String filePath = "/desktop/fakefolder/invalidExport.xml\0";
        ExportXmlCommand exportXmlCommand = new ExportXmlCommand(ParserUtil.parseFilePath(filePath));
        exportXmlCommand.setStorage(storage);
        String expectedMessage = String.format(ExportCommand.MESSAGE_FAIL_READ_FILE);

        assertCommandFailure(exportXmlCommand, model, commandHistory, expectedMessage, filePath);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualCommandHistory} remains unchanged. <br>
     * - the user date stored in {@code model} matches the data stores at {@code filePath}
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
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - user data is not saved at {@code filePath}
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

}
