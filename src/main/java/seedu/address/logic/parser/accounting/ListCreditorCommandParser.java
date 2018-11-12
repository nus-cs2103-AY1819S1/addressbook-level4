package seedu.address.logic.parser.accounting;

import seedu.address.logic.commands.accounting.ListCreditorCommand;
import seedu.address.logic.parser.Parser;

/**
 * Parses input arguments and creates a new ListCreditorCommand object
 */
public class ListCreditorCommandParser implements Parser<ListCreditorCommand> {

    /**
     * Returns a ListCreditorCommand object for execution.
     */
    @Override
    public ListCreditorCommand parse(String userInput) {
        return new ListCreditorCommand();
    }
}
