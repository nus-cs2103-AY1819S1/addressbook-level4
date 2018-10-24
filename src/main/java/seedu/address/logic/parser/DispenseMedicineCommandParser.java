package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

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
        try {
            String[] splitArgs = args.split(" ");
            if (splitArgs.length != 3) {
                throw new ParseException("");
            }

            Index index = ParserUtil.parseIndex(splitArgs[1]);
            QuantityToDispense quantityToDispense = ParserUtil.parseQuantityToDispense(splitArgs[2]);
            return new DispenseMedicineCommand(index, quantityToDispense);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DispenseMedicineCommand.MESSAGE_USAGE), pe);
        }
    }
}
