package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToRestaurantListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.restaurant.Restaurant;

/**
 * Selects a restaurant identified using it's displayed index from the address book.
 */
public class SelectRestaurantCommand extends Command {

    public static final String COMMAND_WORD = "selectRestaurant";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the restaurant identified by the index number used in the displayed restaurant list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_RESTAURANT_SUCCESS = "Selected Restaurant: %1$s";

    private final Index targetIndex;

    public SelectRestaurantCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Restaurant> filteredRestaurantList = model.getFilteredRestaurantList();

        if (targetIndex.getZeroBased() >= filteredRestaurantList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToRestaurantListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_RESTAURANT_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectRestaurantCommand // instanceof handles nulls
                && targetIndex.equals(((SelectRestaurantCommand) other).targetIndex)); // state check
    }
}
