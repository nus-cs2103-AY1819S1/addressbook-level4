package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectOtherCommand;
import seedu.address.logic.commands.SelectRestaurantCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.accounting.AcceptDebtRequestCommand;
import seedu.address.logic.commands.accounting.AddDebtCommand;
import seedu.address.logic.commands.accounting.AddGroupDebtCommand;
import seedu.address.logic.commands.accounting.ClearDebtCommand;
import seedu.address.logic.commands.accounting.DeleteDebtRequestCommand;
import seedu.address.logic.commands.accounting.ListCreditorCommand;
import seedu.address.logic.commands.accounting.ListDebtHistoryCommand;
import seedu.address.logic.commands.accounting.ListDebtRequestReceivedCommand;
import seedu.address.logic.commands.accounting.ListDebtRequestSentCommand;
import seedu.address.logic.commands.accounting.ListDebtorCommand;
import seedu.address.logic.commands.friend.AcceptFriendCommand;
import seedu.address.logic.commands.friend.AddFriendCommand;
import seedu.address.logic.commands.friend.DeleteFriendCommand;
import seedu.address.logic.commands.friend.DeleteFriendRequestCommand;
import seedu.address.logic.commands.friend.ListFriendRequestsCommand;
import seedu.address.logic.commands.friend.ListFriendsCommand;
import seedu.address.logic.commands.group.AcceptGroupCommand;
import seedu.address.logic.commands.group.AddGroupCommand;
import seedu.address.logic.commands.group.AddMembersCommand;
import seedu.address.logic.commands.group.DeleteGroupCommand;
import seedu.address.logic.commands.group.DeleteGroupRequestCommand;
import seedu.address.logic.commands.group.ListGroupRequestsCommand;
import seedu.address.logic.commands.group.ListGroupsCommand;
import seedu.address.logic.commands.jio.CreateJioCommand;
import seedu.address.logic.commands.jio.DeleteJioCommand;
import seedu.address.logic.commands.jio.JoinJioCommand;
import seedu.address.logic.commands.jio.ListJioCommand;
import seedu.address.logic.commands.timetable.BlockDateCommand;
import seedu.address.logic.commands.timetable.FindDatesCommand;
import seedu.address.logic.commands.timetable.FreeDateCommand;
import seedu.address.logic.commands.timetable.ListScheduleCommand;
import seedu.address.logic.commands.user.DisplayProfileCommand;
import seedu.address.logic.commands.user.LoginCommand;
import seedu.address.logic.commands.user.LogoutCommand;
import seedu.address.logic.commands.user.SignUpCommand;
import seedu.address.logic.commands.user.WriteReviewCommand;
import seedu.address.logic.parser.accounting.AcceptDebtRequestCommandParser;
import seedu.address.logic.parser.accounting.AddDebtCommandParser;
import seedu.address.logic.parser.accounting.AddGroupDebtCommandParser;
import seedu.address.logic.parser.accounting.ClearDebtCommandParser;
import seedu.address.logic.parser.accounting.DeleteDebtRequestCommandParser;
import seedu.address.logic.parser.accounting.ListCreditorCommandParser;
import seedu.address.logic.parser.accounting.ListDebtHistoryCommandParser;
import seedu.address.logic.parser.accounting.ListDebtRequestReceivedCommandParser;
import seedu.address.logic.parser.accounting.ListDebtRequestSentCommandParser;
import seedu.address.logic.parser.accounting.ListDebtorCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.friend.AcceptFriendCommandParser;
import seedu.address.logic.parser.friend.AddFriendCommandParser;
import seedu.address.logic.parser.friend.DeleteFriendCommandParser;
import seedu.address.logic.parser.friend.DeleteFriendRequestCommandParser;
import seedu.address.logic.parser.friend.ListFriendRequestsCommandParser;
import seedu.address.logic.parser.friend.ListFriendsCommandParser;
import seedu.address.logic.parser.group.AcceptGroupCommandParser;
import seedu.address.logic.parser.group.AddGroupCommandParser;
import seedu.address.logic.parser.group.AddMembersCommandParser;
import seedu.address.logic.parser.group.DeleteGroupCommandParser;
import seedu.address.logic.parser.group.DeleteGroupRequestCommandParser;
import seedu.address.logic.parser.group.ListGroupRequestsCommandParser;
import seedu.address.logic.parser.group.ListGroupsCommandParser;
import seedu.address.logic.parser.jio.CreateJioCommandParser;
import seedu.address.logic.parser.jio.DeleteJioCommandParser;
import seedu.address.logic.parser.jio.JoinJioCommandParser;
import seedu.address.logic.parser.timetable.BlockDateCommandParser;
import seedu.address.logic.parser.timetable.FindDatesCommandParser;
import seedu.address.logic.parser.timetable.FreeDateCommandParser;
import seedu.address.logic.parser.timetable.ListScheduleCommandParser;


/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectRestaurantCommand.COMMAND_WORD:
            return new SelectRestaurantCommandParser().parse(arguments);

        case SelectOtherCommand.COMMAND_WORD:
            return new SelectOtherCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case SignUpCommand.COMMAND_WORD:
            return new SignUpCommandParser().parse(arguments);

        case LoginCommand.COMMAND_WORD:
            return new LoginCommandParser().parse(arguments);

        case LogoutCommand.COMMAND_WORD:
            return new LogoutCommand();

        case WriteReviewCommand.COMMAND_WORD:
            return new WriteReviewCommandParser().parse(arguments);

        case DisplayProfileCommand.COMMAND_WORD:
            return new DisplayProfileCommand();

            // Restaurant Commands

        // Friend Commands
        case AddFriendCommand.COMMAND_WORD:
            return new AddFriendCommandParser().parse(arguments);

        case AcceptFriendCommand.COMMAND_WORD:
            return new AcceptFriendCommandParser().parse(arguments);

        case DeleteFriendCommand.COMMAND_WORD:
            return new DeleteFriendCommandParser().parse(arguments);

        case DeleteFriendRequestCommand.COMMAND_WORD:
            return new DeleteFriendRequestCommandParser().parse(arguments);

        case ListFriendRequestsCommand.COMMAND_WORD:
            return new ListFriendRequestsCommandParser().parse(arguments);

        case ListFriendsCommand.COMMAND_WORD:
            return new ListFriendsCommandParser().parse(arguments);

        // Group Commands
        case AddGroupCommand.COMMAND_WORD:
            return new AddGroupCommandParser().parse(arguments);

        case AcceptGroupCommand.COMMAND_WORD:
            return new AcceptGroupCommandParser().parse(arguments);

        case AddMembersCommand.COMMAND_WORD:
            return new AddMembersCommandParser().parse(arguments);

        case DeleteGroupCommand.COMMAND_WORD:
            return new DeleteGroupCommandParser().parse(arguments);

        case DeleteGroupRequestCommand.COMMAND_WORD:
            return new DeleteGroupRequestCommandParser().parse(arguments);

        case ListGroupRequestsCommand.COMMAND_WORD:
            return new ListGroupRequestsCommandParser().parse(arguments);

        case ListGroupsCommand.COMMAND_WORD:
            return new ListGroupsCommandParser().parse(arguments);

        // Jio Commands
        case CreateJioCommand.COMMAND_WORD:
            return new CreateJioCommandParser().parse(arguments);

        case DeleteJioCommand.COMMAND_WORD:
            return new DeleteJioCommandParser().parse(arguments);

        case JoinJioCommand.COMMAND_WORD:
            return new JoinJioCommandParser().parse(arguments);

        case ListJioCommand.COMMAND_WORD:
            return new ListJioCommand();

        // Timetable Commands
        case BlockDateCommand.COMMAND_WORD:
            return new BlockDateCommandParser().parse(arguments);

        case FreeDateCommand.COMMAND_WORD:
            return new FreeDateCommandParser().parse(arguments);

        case ListScheduleCommand.COMMAND_WORD:
            return new ListScheduleCommandParser().parse(arguments);

        case FindDatesCommand.COMMAND_WORD:
            return new FindDatesCommandParser().parse(arguments);

        // Accounting Commands
        case AddDebtCommand.COMMAND_WORD:
            return new AddDebtCommandParser().parse(arguments);

        case AddGroupDebtCommand.COMMAND_WORD:
            return new AddGroupDebtCommandParser().parse(arguments);

        case ClearDebtCommand.COMMAND_WORD:
            return new ClearDebtCommandParser().parse(arguments);

        case AcceptDebtRequestCommand.COMMAND_WORD:
            return new AcceptDebtRequestCommandParser().parse(arguments);

        case DeleteDebtRequestCommand.COMMAND_WORD:
            return new DeleteDebtRequestCommandParser().parse(arguments);

        case ListCreditorCommand.COMMAND_WORD:
            return new ListCreditorCommandParser().parse(arguments);

        case ListDebtorCommand.COMMAND_WORD:
            return new ListDebtorCommandParser().parse(arguments);

        case ListDebtHistoryCommand.COMMAND_WORD:
            return new ListDebtHistoryCommandParser().parse(arguments);

        case ListDebtRequestSentCommand.COMMAND_WORD:
            return new ListDebtRequestSentCommandParser().parse(arguments);

        case ListDebtRequestReceivedCommand.COMMAND_WORD:
            return new ListDebtRequestReceivedCommandParser().parse(arguments);

        // Other Additional Makan Book Commands

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
