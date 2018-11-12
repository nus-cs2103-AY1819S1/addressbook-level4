package seedu.address.logic.parser.group;

import seedu.address.logic.commands.group.ListGroupRequestsCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Creates a new ListGroupRequestsCommand object
 */
public class ListGroupRequestsCommandParser implements Parser<ListGroupRequestsCommand> {
    @Override
    public ListGroupRequestsCommand parse(String userInput) throws ParseException {
        return new ListGroupRequestsCommand();
    }
}
