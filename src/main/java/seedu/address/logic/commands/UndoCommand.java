package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CARDS;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.HideCardInfoPanelEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Reverts the {@code model}'s address book to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoTriviaBundle()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoTriviaBundle();
        model.updateFilteredCardList(PREDICATE_SHOW_ALL_CARDS);

        EventsCenter.getInstance().post(new HideCardInfoPanelEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
