package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;

import java.io.File;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

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

    private final File toImport;

    /**
     * Creates an ImportContactsCommand to add the specified {@code File}
     */
    public ImportContactsCommand(File filePath) {
        requireNonNull(filePath);
        toImport = filePath;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        //System.out.println("helloworld");
        //model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toImport));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportContactsCommand // instanceof handles nulls
                && toImport.equals(((ImportContactsCommand) other).toImport));
    }
}
