package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.AchievementsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AchievementsCommand object
 */
public class AchievementsCommandParser implements Parser<AchievementsCommand> {

    /**
     * Parses the given {@code String} of argument in the context of the AchievementsCommand
     * and returns an AchievementsCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AchievementsCommand parse(String args) throws ParseException {
        String trimmedArg = args.trim();
        if (!isValidAchievementCommandArg(trimmedArg)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AchievementsCommand.MESSAGE_USAGE));
        }

        return new AchievementsCommand(trimmedArg);
    }

    /**
     * Checks if the {@code arg} matches any of the three valid argument strings: all-time, today or this week.
     */
    private boolean isValidAchievementCommandArg(String arg) {
        return !arg.isEmpty()
                && (arg.equals(AchievementsCommand.ALL_TIME_OPTION)
                || arg.equals(AchievementsCommand.TODAY_OPTION)
                || arg.equals(AchievementsCommand.THIS_WEEK_OPTION));
    }
}
