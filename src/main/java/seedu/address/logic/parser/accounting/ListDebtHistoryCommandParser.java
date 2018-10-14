package seedu.address.logic.parser.accounting;

import seedu.address.logic.commands.accounting.ListDebtHistoryCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListDebtHistoryCommand object
 */
public class ListDebtHistoryCommandParser implements Parser<ListDebtHistoryCommand> {
    @Override
    public ListDebtHistoryCommand parse(String userInput) throws ParseException {
        return new ListDebtHistoryCommand();
    }
}
