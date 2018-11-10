package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULEINDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCASIONINDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSONINDEX;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.TypeUtil;
import seedu.address.logic.commands.RemovePersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The parser for the remove person command. Parses the user's command line input
 * and decides whether or not to remove a person from a module, or occasion bidirectionally.
 */
public class RemovePersonCommandParser implements Parser<RemovePersonCommand> {

    @Override
    public RemovePersonCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultiMap = ArgumentTokenizer
                .tokenize(args, PREFIX_PERSONINDEX, PREFIX_MODULEINDEX, PREFIX_OCCASIONINDEX);
        if (!arePrefixesPresent(argMultiMap, PREFIX_PERSONINDEX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemovePersonCommand.MESSAGE_USAGE));
        }

        Index personIndex = ParserUtil.parseIndex(argMultiMap.getValue(PREFIX_PERSONINDEX).get());

        if (arePrefixesPresent(argMultiMap, PREFIX_MODULEINDEX)) {
            Index moduleIndex = ParserUtil.parseIndex(argMultiMap.getValue(PREFIX_MODULEINDEX).get());
            return new RemovePersonCommand(personIndex, moduleIndex, TypeUtil.MODULE);

        } else if (arePrefixesPresent(argMultiMap, PREFIX_OCCASIONINDEX)) {
            Index occasionIndex = ParserUtil.parseIndex(argMultiMap.getValue(PREFIX_OCCASIONINDEX).get());
            return new RemovePersonCommand(personIndex, occasionIndex, TypeUtil.OCCASION);
        }

        // Missing either a module or a person index.
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemovePersonCommand.MESSAGE_USAGE));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
