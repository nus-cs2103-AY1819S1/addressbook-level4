package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;

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

/**
 * Import MeetingBook XML Files into MeetingBook
 */
public class ImportCommand extends Command {
    public static final String COMMAND_WORD = "import";
    public static final String OPTION_OVERWRITE = "--force";
    public static final String MESSAGE_SUCCESS = "%s have been imported into MeetingBook.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Import XML File to MeetingBook. Default behaviour "
            + "is to ignore any conflicting Person/Group entries unless "
            + OPTION_OVERWRITE + " is provided.\n"
            + "Parameters: [Options] "
            + PREFIX_PATH + "FilePath\n"
            + "Options: "
            + OPTION_OVERWRITE + " overwrite any conflicting Person/Group \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PATH + "backup\n"
            + COMMAND_WORD + " " + OPTION_OVERWRITE + PREFIX_PATH + "backup";
    public static final String MESSAGE_FAIL_DATA = "Data file not in the correct format. ";
    public static final String MESSAGE_FAIL_NOFILE = "File does not exists.";

    private static final Logger logger = LogsCenter.getLogger(XmlAddressBookStorage.class);

    private boolean overwrite;

    private Path importPath;

    public ImportCommand(Path filepath) {
        importPath = filepath;
        this.overwrite = false;
    }

    public ImportCommand(Path filepath, boolean overwrite) {
        importPath = filepath;
        this.overwrite = overwrite;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        XmlAddressBookStorage importedXmlAddressStorage = new XmlAddressBookStorage(importPath);
        Optional<ReadOnlyAddressBook> importedAddressBook;
        try {
            importedAddressBook = importedXmlAddressStorage.readAddressBook();
            if (!importedAddressBook.isPresent()) {
                return new CommandResult(MESSAGE_FAIL_NOFILE);
            }
        } catch (DataConversionException e) {
            logger.warning(MESSAGE_FAIL_DATA);
            return new CommandResult(MESSAGE_FAIL_DATA);
        } catch (IOException e) {
            logger.warning(MESSAGE_FAIL_NOFILE);
            return new CommandResult(MESSAGE_FAIL_NOFILE);
        }

        model.importAddressBook(importedAddressBook.get(), overwrite);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, importPath.toString()));
    }

}
