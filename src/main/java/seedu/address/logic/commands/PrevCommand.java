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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Retrieves the previous batch of photos for viewing.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.getCurrBatchPointer() < SelectCommand.BATCH_SIZE) {
            throw new CommandException(Messages.MESSAGE_NO_MORE_PREV_IMAGES);
        }

        model.updateImageListPrevBatch();
        EventsCenter.getInstance().post(new UpdateFilmReelEvent(model.getDirectoryImageList(), true));

        return new CommandResult((String.format(Messages.MESSAGE_TOTAL_IMAGES_IN_DIR, model.getTotalImagesInDir())
                + (String.format(Messages.MESSAGE_CURRENT_BATCH_IN_IMAGE_LIST, model.getCurrBatchPointer() + 1,
                model.getCurrBatchPointer() + Math.min(model.numOfRemainingImagesInDir(), SelectCommand.BATCH_SIZE))
                + (String.format(Messages.MESSAGE_CURRENT_IMAGES_IN_BATCH, model.getDirectoryImageList().size())))));
    }
}
