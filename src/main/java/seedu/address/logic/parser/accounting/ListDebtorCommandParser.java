package seedu.address.logic.parser.accounting;

import seedu.address.logic.commands.accounting.ListDebtorCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListDebtorCommand object
 */
public class ListDebtorCommandParser implements Parser<ListDebtorCommand> {
    @Override
    public ListDebtorCommand parse(String userInput) throws ParseException {
        return new ListDebtorCommand();
    }
}
