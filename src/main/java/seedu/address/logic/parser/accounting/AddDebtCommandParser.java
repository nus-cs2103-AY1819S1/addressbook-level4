package seedu.address.logic.parser.accounting;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.accounting.AddDebtCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.accounting.Amount;
import seedu.address.model.user.Username;

/**
 * Parses input arguments and creates a new AddDebtCommand object
 */
public class AddDebtCommandParser implements Parser<AddDebtCommand> {

    @Override
    public AddDebtCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_AMOUNT);
        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_AMOUNT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDebtCommand.MESSAGE_USAGE));
        }
        Username debtorUsername = ParserUserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME).get());
        Amount amount = ParserUserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        return new AddDebtCommand(debtorUsername, amount);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
