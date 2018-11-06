package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_OCCASIONS;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowOccasionRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all occasions in the address book to the user.
 */
public class ListOccasionCommand extends Command {

    public static final String COMMAND_WORD = "listoccasion";

    public static final String MESSAGE_SUCCESS = "Listed all occasions";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredOccasionList(PREDICATE_SHOW_ALL_OCCASIONS);
        EventsCenter.getInstance().post(new ShowOccasionRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
