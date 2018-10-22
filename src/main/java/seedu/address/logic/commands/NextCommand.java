package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists the next batch of photos in the directory.
 */
public class NextCommand extends Command {

    public static final String COMMAND_WORD = "next";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Retrieves the next batch of photos for viewing"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }
}
