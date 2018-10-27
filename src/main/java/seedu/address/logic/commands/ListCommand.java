package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_VOLUNTEERS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all volunteers in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all volunteers.";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredVolunteerList(PREDICATE_SHOW_ALL_VOLUNTEERS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
