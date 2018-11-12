package seedu.address.logic.parser.group;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.List;
import java.util.stream.Stream;

import javafx.util.Pair;
import seedu.address.logic.commands.group.AddMembersCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Name;
import seedu.address.model.user.Username;

/**
 * Parses input arguments and creates a new AddMembersCommand object
 */
public class AddMembersCommandParser implements Parser<AddMembersCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddMembersCommand
     * and returns an AddMembersCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddMembersCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GROUP, PREFIX_USERNAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_GROUP, PREFIX_USERNAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMembersCommand.MESSAGE_USAGE));
        }

        Name groupName = ParserUserUtil.parseGroup(argMultimap.getValue(PREFIX_GROUP).get());
        List<Username> userList = ParserUserUtil.parseUsernames(argMultimap.getAllValues(PREFIX_USERNAME));

        Pair<Name, List<Username>> pair = new Pair(groupName, userList);
        return new AddMembersCommand(pair);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
