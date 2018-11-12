package seedu.address.logic.commands.jio;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * List the available jios.
 */
public class ListJioCommand extends Command {
    public static final String COMMAND_WORD = "listJio";

    public static final String MESSAGE_SUCCESS = "Listed all jios";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        model.listJio(model.getJioList());
        return new CommandResult(MESSAGE_SUCCESS);
    }

}

