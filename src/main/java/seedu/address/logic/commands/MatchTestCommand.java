package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.test.matchtest.MatchTest;
import seedu.address.model.topic.Topic;

/**
 * The command which will executing the matching test of trivia.
 */
public class MatchTestCommand extends Command {
    public static final String COMMAND_WORD = "testM";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Starts a matching test. "
            + "Parameters: TAG"
            + "Example: " + COMMAND_WORD + " Physics";

    public static final String MESSAGE_SUCCESS = "Matching test started.";

    private final Topic tag;

    public MatchTestCommand(Topic tag) {
        this.tag = tag;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        try {
            MatchTest test = new MatchTest(tag, model.getTriviaBundle());
            model.startTriviaTest(test);
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } catch (IllegalArgumentException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatchTestCommand // instanceof handles nulls
                && tag.equals(((MatchTestCommand) other).tag)); // state check
    }

}
