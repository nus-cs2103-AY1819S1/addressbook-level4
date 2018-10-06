package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

//@@author kengwoon
/**
 * Imports a .txt file to update the address book.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports a .txt file to update the address book. "
            + "Parameters: "
            + "import "
            + "f/FILENAME.txt";

    public static final String MESSAGE_SUCCESS = "%1$s file read and database updated.";
    public static final String MESSAGE_FILE_NOT_FOUND = "File not found.";
    public static final String MESSAGE_FILE_EMPTY = "Unable to read. File is empty.";

    private final File file;


    /**
     * Creates an ImportCommand to add the specified file.
     */
    public ImportCommand(File file) {
        requireNonNull(file);
        this.file = file;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        try {
            FileReader fr = new FileReader(file.getName());
            BufferedReader br = new BufferedReader(fr);
            String input;
            while ((input = br.readLine()) != null) {
            }
            br.close();

            model.commitAddressBook();
            return new CommandResult(String.format(MESSAGE_SUCCESS, file.getName()));
        } catch (FileNotFoundException e) {
            throw new CommandException(MESSAGE_FILE_NOT_FOUND);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FILE_EMPTY);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && file.equals(((ImportCommand) other).file));
    }
}
