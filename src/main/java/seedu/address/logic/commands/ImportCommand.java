package seedu.address.logic.commands;
import static java.util.Objects.requireNonNull;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ExtraInformationDisplay;
import seedu.address.commons.events.ui.ExtraInformationDisplayChangeEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Import cards from specified location to the trivia bundle.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports cards to the trivia. "
            + "Parameters: "
            + "File path\n"
            + "Example: " + COMMAND_WORD + " "
            + "C:\\Users\\username\\Desktop\\cards.txt";

    public static final String MESSAGE_SUCCESS = "Imported cards from: %1$s";
    public static final String MESSAGE_DUPLICATE_CARD = "Some cards already exists in the trivia bundle";
    public static final String MESSAGE_INVALID_FILE = "Invalid file name.";
    public static final String MESSAGE_INVALID_FILE_TYPE = "Invalid file type. Only .txt files are accepted";
    private final File file;

    /**
     * Creates an ImportCommand to import the cards {@code File}
     */
    public ImportCommand(File file) {
        requireNonNull(file);
        this.file = file;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        isFileValid();
        // parse txt
        // add card to model
        // exception if file empty, file wrong format, file contains duplicated
        // model.addCard(toAdd);
        model.commitTriviaBundle();
        return new CommandResult(String.format(MESSAGE_SUCCESS, file.getName()));
    }

    /**
     * Validates the file to be imported.
     * @throws CommandException the exception caught.
     */
    private void isFileValid() throws CommandException {
        if (!file.isFile()) {
            EventsCenter
                    .getInstance()
                    .post(new ExtraInformationDisplayChangeEvent(ExtraInformationDisplay.IMPORT_HELP_DISPLAY));
            throw new CommandException(MESSAGE_INVALID_FILE);
        } else if (!isValidFileType()) {
            throw new CommandException(MESSAGE_INVALID_FILE_TYPE);
        }
    }

    /**
     * Checks if file is a readable .txt format.
     * @return true if file is a readable text file.
     */
    private boolean isValidFileType() {
        try {
            String fileType = Files.probeContentType(file.toPath());
            if (!fileType.equals("text/plain")) {
                return false;
            }
        } catch (IOException ioe) {
            return false;
        }
        return true;
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && file.equals(((ImportCommand) other).file));
    }
}
