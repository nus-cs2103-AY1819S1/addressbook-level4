package seedu.address.logic.parser.friend;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.friend.DeleteFriendCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.user.Username;

/**
 * Parses input arguments and creates a new DeleteFriendCommand object
 */
public class DeleteFriendCommandParser implements Parser<DeleteFriendCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteFriendCommand
     * and returns an DeleteFriendCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteFriendCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteFriendCommand.MESSAGE_USAGE));
        }

        Username friendUsername = ParserUserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME).get());

        return new DeleteFriendCommand(friendUsername);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
