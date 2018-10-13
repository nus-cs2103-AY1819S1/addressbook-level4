package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPTAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class SelectCommandParser implements Parser<SelectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GROUPTAG, PREFIX_PERSON);

        if (!isOneOfThePrefixesPresent(argMultimap, PREFIX_GROUPTAG, PREFIX_PERSON)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        try {
            Optional<String> option = argMultimap.getValue(PREFIX_GROUPTAG);
            String indexString;
            int selectOption;
            if (option.isPresent()) {
                indexString = option.get();
                selectOption = SelectCommand.SELECT_TYPE_GROUP;
            } else {
                indexString = argMultimap.getValue(PREFIX_PERSON).get();
                selectOption = SelectCommand.SELECT_TYPE_PERSON;
            }
            Index index = ParserUtil.parseIndex(indexString);
            return new SelectCommand(index, selectOption);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Returns true if one of the prefixes exist in the given {@code ArgumentMultimap}
     */
    private static boolean isOneOfThePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
