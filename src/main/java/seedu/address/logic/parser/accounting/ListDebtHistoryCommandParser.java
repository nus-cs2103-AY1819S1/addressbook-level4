package seedu.address.logic.parser.accounting;

import seedu.address.logic.commands.accounting.ListDebtHistoryCommand;
import seedu.address.logic.parser.Parser;

/**
 * Parses input arguments and creates a new ListDebtHistoryCommand object
 */
public class ListDebtHistoryCommandParser implements Parser<ListDebtHistoryCommand> {

    /**
     * Returns a ListCreditorCommand object for execution.
     */
    @Override
    public ListDebtHistoryCommand parse(String userInput) {
        return new ListDebtHistoryCommand();
    }
}
