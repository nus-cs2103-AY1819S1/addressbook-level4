package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.personcommands.FindByPhoneCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindByPhoneCommandParser implements Parser<FindByPhoneCommand> {

    /**
     * Parses the given {@code Phone number} in the context of the FindByPhoneCommand
     * and returns an FindByPhoneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindByPhoneCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindByPhoneCommand.MESSAGE_USAGE));
        }

        String[] phoneKeywords = trimmedArgs.split("\\s+");

        return new FindByPhoneCommand(new PhoneContainsKeywordsPredicate(Arrays.asList(phoneKeywords)));
    }

}
