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
    public static final int BATCH_SIZE = 10;

    public static final String MESSAGE_NEXT_SUCCESS = "Currently viewing next 10 images.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Retrieves the next batch of photos for viewing.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        ArrayList<Path> dirImageList = model.getDirectoryImageList();

        for (int i = 0; i < BATCH_SIZE; i++) {
            if (dirImageList.isEmpty()) {
                throw new CommandException(Messages.MESSAGE_NO_MORE_IMAGES);
            }
            dirImageList.remove(0);
        }

        model.updateImageList(dirImageList);

        return new CommandResult(MESSAGE_NEXT_SUCCESS);
    }
}
