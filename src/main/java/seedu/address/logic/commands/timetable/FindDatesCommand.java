package seedu.address.logic.commands.timetable;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.NotLoggedInCommandException;
import seedu.address.model.Model;
import seedu.address.model.Name;
import seedu.address.model.timetable.Week;

/**
 * Finds a common time to eat among a list of friends.
 */
public class FindDatesCommand extends Command {
    public static final String COMMAND_WORD = "findDates";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists the common free times among friends for the week for a given group."
            + "Parameters: "
            + PREFIX_WEEK + "NUS WEEK "
            + PREFIX_GROUP + "GROUP "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_WEEK + "5 "
            + PREFIX_GROUP + "MALA";

    public static final String MESSAGE_SUCCESS = "Listed the common free dates for"
            + " week %1$s below for group %2$s.";

    public static final String MESSAGE_NO_SUCH_GROUP = "You are not in that group.";

    public static final String UI_PAGE_HEADER = "These are the common free dates for Group: %1$s on Week: %2$s";

    private final Week weekNumber;
    private final Name groupName;

    /**
     * Creates a FindDatesCommand to find a common eating timeslot among the {@code Integer} friends.
     */
    public FindDatesCommand(Name groupName, Week weekNumber) {
        requireAllNonNull(weekNumber, groupName);
        this.weekNumber = weekNumber;
        this.groupName = groupName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        // throw exception if no user is currently logged in.
        if (!model.isCurrentlyLoggedIn()) {
            throw new NotLoggedInCommandException(COMMAND_WORD);
        }

        // throw exception if finding times for a group that does not exist.
        if (!model.isInGroup(groupName)) {
            throw new CommandException(MESSAGE_NO_SUCH_GROUP);
        }

        model.displayFreeDatesForGroupAndWeek(String.format(UI_PAGE_HEADER, groupName, weekNumber),
                groupName, weekNumber);

        return new CommandResult(String.format(MESSAGE_SUCCESS, weekNumber, groupName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindDatesCommand // instanceof handles nulls
                && (weekNumber.equals(((FindDatesCommand) other).weekNumber)
                    && groupName.equals(((FindDatesCommand) other).groupName)));
    }
}
