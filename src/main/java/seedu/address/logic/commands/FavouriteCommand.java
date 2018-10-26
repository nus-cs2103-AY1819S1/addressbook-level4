package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

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

    public FavouriteCommand (Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        ModelManager.updateFavourite("new Favourite");

        return new CommandResult(String.format(MESSAGE_FAVOURITE_EVENT_SUCCESS, targetIndex.getOneBased()));
    }

}
