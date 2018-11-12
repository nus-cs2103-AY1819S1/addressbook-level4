package seedu.address.logic.parser.friend;

import seedu.address.logic.commands.friend.ListFriendRequestsCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListFriendRequestsCommand object
 */
public class ListFriendRequestsCommandParser implements Parser<ListFriendRequestsCommand> {
    @Override
    public ListFriendRequestsCommand parse(String userInput) throws ParseException {
        return new ListFriendRequestsCommand();
    }
}
