package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_AMOUNT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.amount.Amount;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MoveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MoveCommand object.
 */
public class MoveCommandParser implements Parser<MoveCommand> {

    public static final int UNUSED_FUNDS_INDEX = 0;
    public static final String MESSAGE_INVALID_SAME_INDEX = "Invalid! FROM and TO index cannot be the same.";

    /**
     * Parses the given {@code String} of arguments in the context of the MoveCommand
     * and returns a MoveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected FORMAT
     * @throws IllegalArgumentException if the user enters an invalid argument
     */
    @Override
    public MoveCommand parse(String args) throws ParseException, IllegalArgumentException {
        requireNonNull(args);

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MoveCommand.MESSAGE_USAGE));
        }

        String[] arguments = trimmedArgs.split(" ");

        if (arguments.length != 3) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MoveCommand.MESSAGE_USAGE));
        }

        MoveCommand.MoveType moveCommandType;
        Index fromIndex;
        Index toIndex;

        //Cannot parse the same index for FROM and TO
        if (arguments[0].equals(arguments[1])) {
            throw new ParseException(MESSAGE_INVALID_SAME_INDEX);
        }

        Amount amount;
        try {
            amount = new Amount(arguments[2]);
        } catch (IllegalArgumentException iae) {
            throw new ParseException(String.format(MESSAGE_INVALID_AMOUNT, arguments[2]));
        }

        try {
            if (Integer.parseInt(arguments[0]) == UNUSED_FUNDS_INDEX) {
                fromIndex = null;
                toIndex = ParserUtil.parseIndex(arguments[1]);
                moveCommandType = MoveCommand.MoveType.WISH_FROM_UNUSED_FUNDS;
            } else if (Integer.parseInt(arguments[1]) == UNUSED_FUNDS_INDEX) {
                fromIndex = ParserUtil.parseIndex(arguments[0]);
                toIndex = null;
                moveCommandType = MoveCommand.MoveType.WISH_TO_UNUSED_FUNDS;
            } else {
                fromIndex = ParserUtil.parseIndex(arguments[0]);
                toIndex = ParserUtil.parseIndex(arguments[1]);
                moveCommandType = MoveCommand.MoveType.WISH_TO_WISH;
            }
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MoveCommand.MESSAGE_USAGE));
        }

        return new MoveCommand(fromIndex, toIndex, amount, moveCommandType);
    }
}
