package seedu.address.logic.parser.accounting;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;

import java.util.stream.Stream;

import seedu.address.logic.commands.accounting.AddGroupDebtCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Name;
import seedu.address.model.accounting.Amount;

/**
 * Parses input arguments and creates a new AddGroupDebtCommand object
 */
public class AddGroupDebtCommandParser implements Parser<AddGroupDebtCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddGroupDebtCommand
     * and returns an AddGroupDebtCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddGroupDebtCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GROUP, PREFIX_AMOUNT);
        if (!arePrefixesPresent(argMultimap, PREFIX_GROUP, PREFIX_AMOUNT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGroupDebtCommand.MESSAGE_USAGE));
        }
        Name groupName = ParserUserUtil.parseGroup(argMultimap.getValue(PREFIX_GROUP).get());
        Amount amount = ParserUserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        return new AddGroupDebtCommand(groupName, amount);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
