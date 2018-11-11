package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.PersonListEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists active Persons in the address book to the user.
 */
public class ActiveCommand extends Command {

    public static final String COMMAND_WORD = "active";

    public static final String MESSAGE_SUCCESS = "Listed all Persons";


    @Override
    public CommandResult runBody(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        EventsCenter.getInstance().post(new PersonListEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
