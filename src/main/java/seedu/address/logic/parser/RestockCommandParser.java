package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT_TO_RESTOCK;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RestockCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.medicine.AmountToRestock;

/**
 * Parses input arguments and creates a new RestockCommand object
 */
public class RestockCommandParser implements Parser<RestockCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RestockCommand
     * and returns a RestockCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RestockCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT_TO_RESTOCK);
        Index index;
        AmountToRestock amountToRestock;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            amountToRestock = new AmountToRestock(Integer.parseInt(
                    argMultimap.getValue(PREFIX_AMOUNT_TO_RESTOCK).get()));
            if (!AmountToRestock.isValidAmountToRestock(amountToRestock)) {
                throw new NumberFormatException();
            }
        } catch (ParseException pe) {
            pe.printStackTrace();
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RestockCommand.MESSAGE_USAGE + RestockCommand.MESSAGE_INVALID_QUANTITY), pe);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RestockCommand.MESSAGE_USAGE + RestockCommand.MESSAGE_INVALID_QUANTITY), nfe);
        }

        return new RestockCommand(index, amountToRestock);
    }
}
