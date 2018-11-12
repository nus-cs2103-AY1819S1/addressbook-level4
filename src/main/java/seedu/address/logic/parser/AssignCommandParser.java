package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERYMAN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AssignCommand object
 */
public class AssignCommandParser implements Parser<AssignCommand> {
    @Override
    public AssignCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DELIVERYMAN, PREFIX_ORDER);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_DELIVERYMAN, PREFIX_ORDER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));
        }

        Index deliverymanId = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_DELIVERYMAN).get());
        Set<Index> orderIds = ParserUtil.parseIndexes(argMultimap.getAllValues(PREFIX_ORDER));

        return new AssignCommand(deliverymanId, orderIds);
    }
}
