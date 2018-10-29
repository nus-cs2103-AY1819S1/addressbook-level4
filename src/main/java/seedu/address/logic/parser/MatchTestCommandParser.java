package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.MatchTestCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.topic.Topic;

/**
 * Parses input arguments and creates a new MatchTestCommand object
 */
public class MatchTestCommandParser implements Parser<MatchTestCommand> {
    /**
     * Parses the given {@code String} of argument in the context of the MatchTestCommand
     * and returns an MatchTestCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public MatchTestCommand parse(String args) throws ParseException {
        if (args.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchTestCommand.MESSAGE_USAGE));
        }

        Topic tag = ParserUtil.parseTopic(args);

        return new MatchTestCommand(tag);
    }
}
