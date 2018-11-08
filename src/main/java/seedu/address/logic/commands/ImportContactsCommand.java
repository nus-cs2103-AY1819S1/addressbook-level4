package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.filereader.FileReader;

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
    public static final String MESSAGE_WRONG_FILE_FORMAT = "File must be csv format and contain:\n"
            + "'" + FileReader.CSV_HEADER_NAME + "' as header for contact name,\n"
            + "'" + FileReader.CSV_HEADER_PHONE + "' as header for contact number,\n"
            + "'" + FileReader.CSV_HEADER_ADDRESS + "' as header for contact address,\n"
            + "'" + FileReader.CSV_HEADER_EMAIL + "' as header for contact email and\n"
            + "'" + FileReader.CSV_HEADER_FACULTY + "' as header for contact faculty.";

    public static final String MESSAGE_SUCCESS = "%s contacts successfully imported";
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
        model.importContacts(toImport);
        model.commitAddressBook();

        return new CommandResult(String.format(MESSAGE_SUCCESS, toImport.getAddContactStatus()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportContactsCommand // instanceof handles nulls
                && toImport.equals(((ImportContactsCommand) other).toImport));
    }
}
