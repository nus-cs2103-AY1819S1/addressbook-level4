package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOPIC;

import java.util.stream.Stream;

import seedu.address.logic.commands.OpenEndedTestCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.topic.Topic;

/**
 * Parses input arguments and creates a new OpenEndedTestCommand object
 */
public class OpenEndedTestCommandParser implements Parser<OpenEndedTestCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the OpenEndedTestCommand
     * and returns an estCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public OpenEndedTestCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TOPIC);

        if (!arePrefixesPresent(argMultimap, PREFIX_TOPIC)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenEndedTestCommand.MESSAGE_USAGE));
        }

        Topic tag = ParserUtil.parseTopic(argMultimap.getValue(PREFIX_TOPIC).get());

        return new OpenEndedTestCommand(tag);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
