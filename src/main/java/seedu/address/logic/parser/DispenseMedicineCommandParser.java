package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT_TO_DISPENSE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DispenseMedicineCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.medicine.QuantityToDispense;

/**
 * Parses input arguments and creates a new DispenseMedicineCommand object
 */
public class DispenseMedicineCommandParser implements Parser<DispenseMedicineCommand> {

    @Override
    public DispenseMedicineCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT_TO_DISPENSE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            pe.printStackTrace();
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DispenseMedicineCommand.MESSAGE_USAGE), pe);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_AMOUNT_TO_DISPENSE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DispenseMedicineCommand.MESSAGE_USAGE));
        }

        QuantityToDispense quantity = ParserUtil.parseQuantityToDispense(
                argMultimap.getValue(PREFIX_AMOUNT_TO_DISPENSE).get());

        return new DispenseMedicineCommand(index, quantity);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
