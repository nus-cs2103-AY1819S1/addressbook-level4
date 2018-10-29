package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.DisplayGoogleSearchEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.card.Card;

/**
 * A command to display the google search results on the InfoPanel.
 */
public class GoogleCommand extends Command {

    public static final String COMMAND_WORD = "google";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Google the card by providing the identified index number used in the displayed card list. \n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_CARD_SUCCESS = "Googled Card: %1$s";

    private final Index targetIndex;

    public GoogleCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Card> filteredCardList = model.getFilteredCardList();

        if (targetIndex.getZeroBased() >= filteredCardList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
        }

        Card cardToGoogle = filteredCardList.get(targetIndex.getZeroBased());
        EventsCenter.getInstance().post(new DisplayGoogleSearchEvent(cardToGoogle));
        return new CommandResult(String.format(MESSAGE_SELECT_CARD_SUCCESS, targetIndex.getOneBased()));

    }
}
