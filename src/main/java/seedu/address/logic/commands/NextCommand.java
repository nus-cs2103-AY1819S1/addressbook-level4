//@@author benedictcss
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.ArrayList;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists the next batch of photos in the directory.
 */
public class NextCommand extends Command {

    public static final String COMMAND_WORD = "next";

    public static final String MESSAGE_NEXT_SUCCESS = "Currently viewing next %d images.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Retrieves the next batch of photos for viewing.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        ArrayList<Path> dirImageList = model.getDirectoryImageList();

        if (dirImageList.size() <= SelectCommand.BATCH_SIZE) {
            throw new CommandException(Messages.MESSAGE_NO_MORE_IMAGES);
        }

        for (int i = 0; i < SelectCommand.BATCH_SIZE; i++) {
            dirImageList.remove(0);
        }

        model.updateImageList(dirImageList);

        return new CommandResult((String.format(MESSAGE_NEXT_SUCCESS,
                Math.min(model.getDirectoryImageList().size(), SelectCommand.BATCH_SIZE)) + "\n"
                + (String.format(Messages.MESSAGE_REMAINING_IMAGES_IN_DIR, model.getDirectoryImageList().size())
                + (String.format(Messages.MESSAGE_CURRENT_IMAGES_IN_BATCH,
                Math.min(model.getDirectoryImageList().size(), SelectCommand.BATCH_SIZE))))));
    }
}
