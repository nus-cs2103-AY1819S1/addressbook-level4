package seedu.restaurant.logic.parser.account;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.logic.commands.account.SelectAccountCommand;
import seedu.restaurant.logic.parser.Parser;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.logic.parser.util.ParserUtil;

//@@author AZhiKai
/**
 * Parses input arguments and creates a new {@code SelectAccountCommand} object
 */
public class SelectAccountCommandParser implements Parser<SelectAccountCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code SelectAccountCommand} and returns an
     * {@code SelectAccountCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectAccountCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectAccountCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectAccountCommand.MESSAGE_USAGE),
                    pe);
        }
    }
}
