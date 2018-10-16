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

    public static final String MESSAGE_SUCCESS = "Contacts imported";
    public static final String MESSAGE_EMPTY_FILE_EXCEPTION = "File is empty";
    public static final String MESSAGE_TEST_EXCEPTION = "Exception for testing";

    private final FileReader toImport;
    private int nameIndex = -1;
    private int phoneIndex = -1;

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
            model.readImportContactsFile(toImport);
        } catch (EmptyFileException e) {
            throw new CommandException(MESSAGE_EMPTY_FILE_EXCEPTION);
        }

        throw new CommandException(MESSAGE_TEST_EXCEPTION);
        // return new CommandResult(String.format(MESSAGE_SUCCESS, toImport));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportContactsCommand // instanceof handles nulls
                && toImport.equals(((ImportContactsCommand) other).toImport));
    }
}
