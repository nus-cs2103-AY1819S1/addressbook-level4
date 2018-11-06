//@@author benedictcss
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;


/**
 * Changes the current directory.
 */
public class CdCommand extends Command {

    public static final String COMMAND_WORD = "cd";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the current directory.\n"
            + "Parameters: FILEPATH (existing directory)\n"
            + "Example: " + COMMAND_WORD + " Desktop/piconso";

    public static final String MESSAGE_FAILURE = "The system cannot find the path specified.";

    private final Path toDirectories;

    public CdCommand(Path directories) {
        this.toDirectories = directories;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        String currDirectory = model.getCurrDirectory().toString();
        String newDir = currDirectory + "/" + toDirectories.toString();

        Path newCurrDirectory = Paths.get("");
        try {
            if (toDirectories.isAbsolute()) {
                File dir = toDirectories.toFile();
                if (!dir.isDirectory()) {
                    return new CommandResult(MESSAGE_FAILURE);
                }
                newCurrDirectory = toDirectories.toRealPath();
                model.updateCurrDirectory(newCurrDirectory);
            } else {
                File dir = new File(newDir);
                if (!dir.isDirectory()) {
                    return new CommandResult(MESSAGE_FAILURE);
                }
                newCurrDirectory = dir.toPath().toRealPath();
                model.updateCurrDirectory(newCurrDirectory);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return new CommandResult(newCurrDirectory.toString() + "\n"
                + String.format(Messages.MESSAGE_TOTAL_IMAGES_IN_DIR, model.getTotalImagesInDir())
                + String.format(Messages.MESSAGE_CURRENT_IMAGES_IN_BATCH,
                Math.min(model.getDirectoryImageList().size(), SelectCommand.BATCH_SIZE)));
    }

    public Path getPath() {
        return this.toDirectories;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CdCommand // instanceof handles nulls
                && toDirectories.equals(((CdCommand) other).toDirectories)); // state check
    }
}
