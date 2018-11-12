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
 * Frees up the time on the user's timetable.
 */
public class FreeDateCommand extends Command {
    public static final String COMMAND_WORD = "freeDate";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Frees up a time on your timetable in 30 minute chunks. "
            + "Parameters: "
            + PREFIX_WEEK + "NUS WEEK "
            + PREFIX_DAY + "DAY "
            + PREFIX_TIME + "<24-HOUR-TIME> \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_WEEK + "recess "
            + PREFIX_DAY + "thu "
            + PREFIX_TIME + "1830 ";

    public static final String MESSAGE_SUCCESS = "Time has been unblocked on your schedule: %1$s";

    public static final String MESSAGE_ALREADY_FREE = "This time block is already free on your schedule.";

    private final Date toFree;

    /**
     * Creates a WriteReview to add the specified {@code Integer} review, that ranges from 1 - 5.
     */
    public FreeDateCommand(Date toFree) {
        requireNonNull(toFree);
        this.toFree = toFree;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        // ensure that the user is logged in.
        if (!model.isCurrentlyLoggedIn()) {
            throw new NotLoggedInCommandException(COMMAND_WORD);
        }

        if (!model.hasDateForCurrentUser(toFree)) {
            throw new CommandException(MESSAGE_ALREADY_FREE);
        }

        model.freeDateForCurrentUser(this.toFree);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toFree));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FreeDateCommand // instanceof handles nulls
                && toFree.equals(((FreeDateCommand) other).toFree));
    }
}
