package seedu.address.logic.parser.accounting;

import seedu.address.logic.commands.accounting.ListCreditorCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListCreditorCommand object
 */
public class ListCreditorCommandParser implements Parser<ListCreditorCommand> {
    @Override
    public ListCreditorCommand parse(String userInput) throws ParseException {
        return new ListCreditorCommand();
    }
}
