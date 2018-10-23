package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT_TO_DISPENSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICINE_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.DispenseMedicineCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.medicine.MedicineName;

/**
 * Parses input arguments and creates a new DispenseMedicineCommand object
 */
public class DispenseMedicineCommandParser implements Parser<DispenseMedicineCommand> {

    @Override
    public DispenseMedicineCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MEDICINE_NAME, PREFIX_AMOUNT_TO_DISPENSE);

        if (!arePrefixesPresent(argMultimap, PREFIX_MEDICINE_NAME, PREFIX_AMOUNT_TO_DISPENSE)) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, DispenseMedicineCommand.MESSAGE_USAGE));
        }

        MedicineName medicineName = ParserUtil.parseMedicineName(argMultimap.getValue(PREFIX_MEDICINE_NAME).get());
        Integer quantityToDispense = ParserUtil.parseAmountToDispense(
                argMultimap.getValue(PREFIX_AMOUNT_TO_DISPENSE).get());

        return new DispenseMedicineCommand(medicineName, quantityToDispense);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
