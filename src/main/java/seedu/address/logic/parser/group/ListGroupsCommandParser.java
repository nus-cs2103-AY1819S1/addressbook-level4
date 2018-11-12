package seedu.address.logic.parser.group;

import seedu.address.logic.commands.group.ListGroupsCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Creates a new ListGroupsCommand object
 */
public class ListGroupsCommandParser implements Parser<ListGroupsCommand> {
    @Override
    public ListGroupsCommand parse(String userInput) throws ParseException {
        return new ListGroupsCommand();
    }
}
