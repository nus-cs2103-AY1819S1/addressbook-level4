package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindDepartmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.DepartmentContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindDepartmentCommand object
 */
public class FindDepartmentCommandParser implements Parser<FindDepartmentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindDepartmentCommand
     * and returns an FindDepartmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindDepartmentCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDepartmentCommand.MESSAGE_USAGE));
        }

        String[] departmentKeywords = trimmedArgs.split("\\s+");

        return new FindDepartmentCommand(new DepartmentContainsKeywordsPredicate(Arrays.asList(departmentKeywords)));
    }
}
