//@@author benedictcss
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.UpdateFilmReelEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists the next batch of photos in the directory.
 */
public class PrevCommand extends Command {

    public static final String COMMAND_WORD = "prev";

    public static final String MESSAGE_PREV_SUCCESS = "Currently viewing previous %d images.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Retrieves the previous batch of photos for viewing.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.getCurrBatchPointer() < SelectCommand.BATCH_SIZE) {
            throw new CommandException(Messages.MESSAGE_NO_MORE_PREV_IMAGES);
        }

        model.updateImageListPrevBatch();
        EventsCenter.getInstance().post(new UpdateFilmReelEvent(model.returnPreviewImageList(), true));

        return new CommandResult((String.format(MESSAGE_PREV_SUCCESS, model.getDirectoryImageList().size()) + "\n"
                + (String.format(Messages.MESSAGE_REMAINING_IMAGES_IN_DIR, model.numOfRemainingImagesInDir())
                + (String.format(Messages.MESSAGE_CURRENT_IMAGES_IN_BATCH, model.getDirectoryImageList().size())))));
    }
}
