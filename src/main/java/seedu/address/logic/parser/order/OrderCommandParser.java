package seedu.address.logic.parser.order;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ORDER_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.order.AddCommand;
import seedu.address.logic.commands.order.ClearCommand;
import seedu.address.logic.commands.order.DeleteCommand;
import seedu.address.logic.commands.order.DoneCommand;
import seedu.address.logic.commands.order.EditCommand;
import seedu.address.logic.commands.order.FindCommand;
import seedu.address.logic.commands.order.ListCommand;
import seedu.address.logic.commands.order.OrderCommand;
import seedu.address.logic.commands.order.SelectCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses order input.
 */
public class OrderCommandParser implements Parser<OrderCommand> {
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the OrderCommand
     * and returns an OrderCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public OrderCommand parse(String args) throws ParseException {

        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_ORDER_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DoneCommand.COMMAND_WORD:
            return new DoneCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
