package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.filereader.FileReader;
import seedu.address.model.Model;

/**
 * Import contacts to the address book.
 */
public class ImportContactsCommand extends Command {

    public static final String COMMAND_WORD = "importContacts";

    public static final String CSV_HEADER_NAME = "Name";
    public static final String CSV_HEADER_PHONE = "Phone 1 - Value";

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
    public ImportContactsCommand(FileReader filePath) {
        requireNonNull(filePath);
        toImport = filePath;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        File csvFile = new File(toImport.toString());

        try {
            Scanner sc = new Scanner(csvFile);
            if (!sc.hasNextLine()) {
                throw new CommandException(MESSAGE_EMPTY_FILE_EXCEPTION);
            }
            String header = sc.nextLine();
            String[] parts = header.split(",");
            boolean isValidIndex = setIndex(parts);
        } catch (FileNotFoundException e) {
            // will never happen, toImport is validated by parser
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

    private boolean setIndex(String[] parts) {
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals(CSV_HEADER_NAME)) {
                nameIndex = i;
            }
            if (parts[i].equals(CSV_HEADER_PHONE)) {
                phoneIndex = i;
            }
        }
        // return true if nameIndex and phoneIndex is valid
        return nameIndex != -1 && phoneIndex != -1;
    }
}
