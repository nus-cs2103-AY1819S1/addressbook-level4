package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.filereader.FileReader;
import seedu.address.model.filereader.exceptions.EmptyFileException;

/**
 * Import contacts to the address book.
 */
public class ImportContactsCommand extends Command {

    public static final String COMMAND_WORD = "importContacts";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports all contacts from a contact list to "
            + "the address book. "
            + "Parameters: "
            + PREFIX_FILE + "FILEPATH\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FILE + "~/Downloads/contacts1.csv ";

    public static final String MESSAGE_WRONG_FILE_FORMAT = "File must be csv format and contain "
            + FileReader.CSV_HEADER_NAME
            + ", "
            + FileReader.CSV_HEADER_PHONE
            + ", "
            + FileReader.CSV_HEADER_ADDRESS
            + " and "
            + FileReader.CSV_HEADER_EMAIL
            + " as header for contact name and contact number field";

    public static final String MESSAGE_SUCCESS = "Contacts imported";
    public static final String MESSAGE_EMPTY_FILE_EXCEPTION = "File is empty";

    private final FileReader toImport;

    /**
     * Creates an ImportContactsCommand to add the specified {@code String}
     */
    public ImportContactsCommand(FileReader fileReader) {
        requireNonNull(fileReader);
        toImport = fileReader;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        try {
            model.importContacts(toImport);
        } catch (EmptyFileException e) {
            throw new CommandException(MESSAGE_EMPTY_FILE_EXCEPTION);
        }
        model.commitAddressBook();

        return new CommandResult(String.format(MESSAGE_SUCCESS, toImport));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportContactsCommand // instanceof handles nulls
                && toImport.equals(((ImportContactsCommand) other).toImport));
    }
}
