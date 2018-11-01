package seedu.scheduler.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.scheduler.logic.CommandHistory;
import seedu.scheduler.model.Model;

import seedu.scheduler.model.tag.Tag;


/**
 * Adds an tag to the scheduler.
 */
public class AddTagCommand extends Command {

    public static final String COMMAND_WORD = "addTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tag to the scheduler. "
            + "Parameters: "
            + PREFIX_TAG + "TAG"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TAG + "exam ";

    public static final String MESSAGE_SUCCESS = "New tag added to scheduler: %1$s";
    public static final String MESSAGE_DUPLICATE_TAG = "This tag already exists in the scheduler";

    private final Tag toAdd;

    /**
     * Creates an AddTagCommand to add the specified {@code Tag}
     */
    public AddTagCommand(Tag tag) {
        requireNonNull(tag);
        toAdd = tag;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }
}
