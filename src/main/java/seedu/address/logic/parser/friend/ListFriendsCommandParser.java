package seedu.address.logic.parser.friend;

import seedu.address.logic.commands.friend.ListFriendsCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Creates a new ListFriendsCommand object
 */
public class ListFriendsCommandParser implements Parser<ListFriendsCommand> {
    @Override
    public ListFriendsCommand parse(String userInput) throws ParseException {
        return new ListFriendsCommand();
    }
}
