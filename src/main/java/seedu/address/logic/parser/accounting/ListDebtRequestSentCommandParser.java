package seedu.address.logic.parser.accounting;

import seedu.address.logic.commands.accounting.ListDebtRequestSentCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListDebtRequestSentCommand object
 */
public class ListDebtRequestSentCommandParser implements Parser<ListDebtRequestSentCommand> {
    @Override
    public ListDebtRequestSentCommand parse(String userInput) throws ParseException {
        return new ListDebtRequestSentCommand();
    }
}
