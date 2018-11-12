package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOPIC;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.test.openendedtest.OpenEndedTest;
import seedu.address.model.topic.Topic;

/**
 * The command which will executing the Open Ended Test of 3VIA.
 */
public class OpenEndedTestCommand extends Command {
    public static final String COMMAND_WORD = "testO";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Starts a open ended test. "
            + "Parameters: "
            + PREFIX_TOPIC + "TAG "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TOPIC + "Physics ";

    public static final String MESSAGE_SUCCESS = "OpenEndedTest started.";

    private final Topic tag;

    public OpenEndedTestCommand(Topic tag) {
        this.tag = tag;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        try {
            OpenEndedTest test = new OpenEndedTest(tag, model.getTriviaBundle());
            model.startTriviaTest(test);
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } catch (IllegalArgumentException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
