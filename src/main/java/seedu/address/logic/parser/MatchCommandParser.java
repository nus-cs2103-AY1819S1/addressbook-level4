package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MatchCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MatchCommand object
 */
public class MatchCommandParser implements Parser<MatchCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code MatchTestCommand}
     * and returns an MatchTest object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MatchCommand parse(String args) throws ParseException {
        args = args.trim().replaceAll("\\s+", " ");
        String[] values = args.split(" ");

        if (isInvalidValues(values)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE));
        }

        if (values.length == 1) {
            return createMatchCommandWithOneIndex(values);
        } else {
            return createMatchCommandWithTwoIndexes(values);
        }
    }

    private boolean isInvalidValues(String[] values) {
        return values.length > 2 || (values.length == 1 && values[0].isEmpty());
    }

    private MatchCommand createMatchCommandWithOneIndex(String[] values) throws ParseException {
        Index answerIndex = ParserUtil.parseIndex(values[0]);
        return new MatchCommand(null, answerIndex);
    }

    private MatchCommand createMatchCommandWithTwoIndexes(String[] values) throws ParseException {
        Index questionIndex = ParserUtil.parseIndex(values[0]);
        Index answerIndex = ParserUtil.parseIndex(values[1]);
        return new MatchCommand(questionIndex, answerIndex);
    }
}
