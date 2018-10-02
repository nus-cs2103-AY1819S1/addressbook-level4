package seedu.address.logic.Anakin_commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Anakin_Model;


/**
 * Lists all persons in the address book to the user.
 */
public class AnakinListCommand extends Anakin_Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all decks";


    @Override
    public CommandResult execute(Anakin_Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredDeckList(unusedVariable -> true);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
