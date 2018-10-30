package seedu.thanepark.logic.parser;

import static seedu.thanepark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.thanepark.commons.util.StringUtil;
import seedu.thanepark.logic.commands.ViewStatusCommand;
import seedu.thanepark.logic.parser.exceptions.ParseException;
import seedu.thanepark.model.ride.RideStatusPredicate;
import seedu.thanepark.model.ride.Status;

/**
 * Parses input arguments and creates a new ViewStatusCommand Object
 */
public class ViewStatusCommandParser implements Parser<ViewStatusCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewStatusCommand
     * and returns an ViewStatusCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ViewStatusCommand parse(String args) throws ParseException {
        Status status;
        String[] trimmedArgs = args.trim().split(" ");
        String arg = trimmedArgs[0];
        if (!arg.isEmpty() && (trimmedArgs.length == 1)) {
            if (StringUtil.containsStringIgnoreCase(arg, "open")) {
                status = Status.OPEN;
            } else if (StringUtil.containsStringIgnoreCase(arg, "shutdown")) {
                status = Status.SHUTDOWN;
            } else if (StringUtil.containsStringIgnoreCase(arg, "maintenance")) {
                status = Status.MAINTENANCE;
            } else {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewStatusCommand.MESSAGE_USAGE));
            }
            return new ViewStatusCommand(new RideStatusPredicate(status));
        }
        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewStatusCommand.MESSAGE_USAGE));
    }
}
