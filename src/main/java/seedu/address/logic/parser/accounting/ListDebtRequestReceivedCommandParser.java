package seedu.address.logic.parser.accounting;

import seedu.address.logic.commands.accounting.ListDebtRequestReceivedCommand;
import seedu.address.logic.parser.Parser;

/**
 * Parses input arguments and creates a new ListDebtRequestReceivedCommand object
 */
public class ListDebtRequestReceivedCommandParser implements Parser<ListDebtRequestReceivedCommand> {

    /**
     * Returns a ListDebtRequestReceivedCommand object for execution.
     */
    @Override
    public ListDebtRequestReceivedCommand parse(String userInput) {
        return new ListDebtRequestReceivedCommand();
    }
}
