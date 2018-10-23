package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import javafx.collections.ObservableList;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.event.Event;

/**
 * Favourite an event for it to show up as a notification on startup
 * To do : documentation
 */

public class FavouriteCommand extends Command {

    public static final String COMMAND_WORD = "favourite";
    public static final String COMMAND_WORD_ALIAS = "fv";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "(alias: " + COMMAND_WORD_ALIAS + ")"
            + ": set notification to display an event"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_FAVOURITE_EVENT_SUCCESS = "Favorite Event: %1$s";

    private final Index targetIndex;

    public FavouriteCommand (Index targetIndex) { this.targetIndex = targetIndex; }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        /*ObservableList<Event> eventsList = AddressBook.getEventList();

        if (targetIndex.getZeroBased() >= eventsList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        } */

        ModelManager.updateFavourite("new Favourite");

        //EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_FAVOURITE_EVENT_SUCCESS, targetIndex.getOneBased()));
    }

}
