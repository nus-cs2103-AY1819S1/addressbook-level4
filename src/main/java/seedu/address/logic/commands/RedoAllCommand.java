package seedu.address.logic.commands;

//@@author ihwk1996
import static java.util.Objects.requireNonNull;

import javafx.embed.swing.SwingFXUtils;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeImageEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Reverts the {@code model}'s previewImageManager to its previously undone state.
 */
public class RedoAllCommand extends Command {

    public static final String COMMAND_WORD = "redo-all";
    public static final String MESSAGE_SUCCESS = "All transformations successfully redone";
    public static final String MESSAGE_FAILURE = "No more transformations to redo";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoPreviewImage()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoAllPreviewImage();
        EventsCenter.getInstance().post(
                new ChangeImageEvent(SwingFXUtils.toFXImage(
                        model.getCurrentPreviewImage().getImage(), null), "preview"));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
