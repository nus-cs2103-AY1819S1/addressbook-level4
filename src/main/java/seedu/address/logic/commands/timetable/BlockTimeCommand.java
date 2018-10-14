package seedu.address.logic.commands.timetable;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Blocks out a time on the logged in user's timetable.
 */
public class BlockTimeCommand extends Command {
    public static final String COMMAND_WORD = "blockTime";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Blocks out a time on your timetable in 30 minute chunks. "
            + "Parameters: "
            + PREFIX_WEEK + "NUS WEEK "
            + PREFIX_DAY + "DAY "
            + PREFIX_TIME + "<24-HOUR-TIME> \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_WEEK + "recess "
            + PREFIX_DAY + "thu "
            + PREFIX_TIME + "1830 ";

    public static final String MESSAGE_SUCCESS = "Time has been blocked on your schedule.";

    public static final String MESSAGE_DUPLICATE_REVIEW = "This time block already exists in your schedule.";

    private final String toBlock;

    /**
     * Creates a BlockTimeCommand to add the specified {@code String} toBlock,
     * that ranges from week 1 - 6, recess, 7 - 13,
     * reading, 14, 15.
     */
    public BlockTimeCommand(String toBlock) {
        requireNonNull(toBlock);
        this.toBlock = toBlock;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        // TODO
        requireNonNull(model);

        return null;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BlockTimeCommand // instanceof handles nulls
                && toBlock.equals(((BlockTimeCommand) other).toBlock));
    }
}
