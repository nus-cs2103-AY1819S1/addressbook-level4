package seedu.address.logic.commands;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.storage.XmlAddressBookStorage;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;

/**
 * Import MeetingBook XML Files into MeetingBook
 */
public class ImportCommand extends Command{
    private static final Logger logger = LogsCenter.getLogger(XmlAddressBookStorage.class);
    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_SUCCESS = "%s have been imported into MeetingBook.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Import XML File to Meetingbook."
            + "Parameters: "
            + PREFIX_PATH + "FilePath\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PATH + "backup";
    public static final String MESSAGE_FAIL_DATA = "Data file not in the correct format. "
            + "Will be starting with an empty AddressBook.";
    public  static final String MESSAGE_FAIL_IO = "Problem while reading from the file. "
            + "Will be starting with an empty AddressBook.";
    public  static final String MESSAGE_FAIL_NOFILE = "File does not exists.";

    private Path importPath;

    public ImportCommand(Path filepath) {
        importPath = filepath;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        // TODO: Support Meeting
        XmlAddressBookStorage importedXmlAddressStorage = new XmlAddressBookStorage(importPath);
        Optional<ReadOnlyAddressBook> importedAddressBook;
        try {
            importedAddressBook = importedXmlAddressStorage.readAddressBook();
            if (!importedAddressBook.isPresent()) {
                return new CommandResult(MESSAGE_FAIL_NOFILE);
            }
        }
        catch (DataConversionException e) {
            logger.warning(MESSAGE_FAIL_DATA);
            return new CommandResult(MESSAGE_FAIL_DATA);
        } catch (IOException e) {
            logger.warning(MESSAGE_FAIL_IO);
            return new CommandResult(MESSAGE_FAIL_IO);
        }

        model.importAddressBook(importedAddressBook.get());
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, importPath.toString()));
    }

}
