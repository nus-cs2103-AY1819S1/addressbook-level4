package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.AchievementCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AchievementCommand object
 */
public class AchievementCommandParser implements Parser<AchievementCommand> {

    /**
     * Parses the given {@code String} of argument in the context of the AchievementCommand
     * and returns an AchievementCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AchievementCommand parse(String args) throws ParseException {
        String trimmedArg = args.trim();
        if (!isValidAchievementCommandArg(trimmedArg)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AchievementCommand.MESSAGE_USAGE));
        }

        return new AchievementCommand(trimmedArg);
    }
    private boolean isValidAchievementCommandArg(String arg) {
        return !arg.isEmpty()
                && (arg.equals(AchievementCommand.ALL_TIME_OPTION)
                || arg.equals(AchievementCommand.TODAY_OPTION)
                || arg.equals(AchievementCommand.THIS_WEEK_OPTION));
    }
}
