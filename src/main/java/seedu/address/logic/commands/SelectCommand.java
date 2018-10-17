package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javafx.scene.image.Image;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ChangeImageEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Selects a person identified using it's displayed index from the address book.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";
    public static final int BATCH_SIZE = 10;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the image identified by the index number in the current batch.\n"
            + "Parameters: INDEX (1 - 10)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_IMAGE_SUCCESS = "Selected image: %1$s";

    private final Index targetIndex;

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<String> dirImageList = model.getDirectoryImageList();
        Image img = new Image("https://via.placeholder.com/500x500");

        if (targetIndex.getZeroBased() >= dirImageList.size()) {
            throw new CommandException(Messages.MESSAGE_INDEX_END_OF_IMAGE_LIST);
        } else if (targetIndex.getZeroBased() >= BATCH_SIZE) {
            throw new CommandException(Messages.MESSAGE_INDEX_EXCEED_MAX_BATCH_SIZE);
        }

        // TODO: Ivan - model.resetPreviewImageManager(initialImage);
        try {
            FileInputStream fis = new FileInputStream(dirImageList.get(targetIndex.getZeroBased()));
            img = new Image(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        EventsCenter.getInstance().post(new ChangeImageEvent(img, "preview"));
        EventsCenter.getInstance().post(new ChangeImageEvent(img, "original"));

        model.updateCurrDisplayedImage(img);

        //EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        //EventsCenter.getInstance().post(new ChangeImageEvent(
        //        new Image("https://api.thecatapi.com/v1/images/search?format=src&size=full"), "preview"));
        //EventsCenter.getInstance().post(new ChangeImageEvent(
        //        new Image("https://api.thecatapi.com/v1/images/search?format=src&size=full"), "original"));
        return new CommandResult(String.format(MESSAGE_SELECT_IMAGE_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}
