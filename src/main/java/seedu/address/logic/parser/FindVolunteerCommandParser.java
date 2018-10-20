package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindVolunteerCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.volunteer.VolunteerNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindVolunteerCommand object
 */
public class FindVolunteerCommandParser implements Parser<FindVolunteerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindVolunteerCommand
     * and returns an FindVolunteerCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindVolunteerCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindVolunteerCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindVolunteerCommand(new VolunteerNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
