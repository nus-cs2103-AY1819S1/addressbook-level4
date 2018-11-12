package seedu.address.logic.commands.timetable;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.NotLoggedInCommandException;
import seedu.address.model.Model;
import seedu.address.model.timetable.Date;

/**
 * Blocks out a date on the logged in user's timetable.
 */
public class BlockDateCommand extends Command {
    public static final String COMMAND_WORD = "blockDate";

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

    public static final String MESSAGE_SUCCESS = "New date added on your schedule: %1$s.";

    public static final String MESSAGE_DUPLICATE_DATE = "This busy date already exists in your schedule.";

    private final Date toBlock;

    /**
     * Creates a BlockDateCommand to add the specified {@code String} toBlock,
     * that ranges from week 1 - 6, recess, 7 - 13,
     * reading, 14, 15.
     */
    public BlockDateCommand(Date toBlock) {
        requireNonNull(toBlock);
        this.toBlock = toBlock;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        // ensure that the user is logged in.
        if (!model.isCurrentlyLoggedIn()) {
            throw new NotLoggedInCommandException(COMMAND_WORD);
        }

        if (model.hasDateForCurrentUser(toBlock)) {
            throw new CommandException(MESSAGE_DUPLICATE_DATE);
        }

        model.blockDateForCurrentUser(this.toBlock);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toBlock));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BlockDateCommand // instanceof handles nulls
                && toBlock.equals(((BlockDateCommand) other).toBlock));
    }
}
