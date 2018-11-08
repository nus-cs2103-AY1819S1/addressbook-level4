package seedu.address.logic.parser;

import seedu.address.logic.commands.ModeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.game.GameManager;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new ModeCommand object
 */
public class ModeCommandParser implements Parser<ModeCommand>{

    /**
     * Parses the given {@code String} of argument in the context of the ModeCommand
     * and returns an ModeCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ModeCommand parse(String args) throws ParseException {
        String trimmedArg = args.trim();
        if (!isValidModeCommandArg(trimmedArg)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModeCommand.MESSAGE_USAGE));
        }

        return new ModeCommand(trimmedArg);
    }

    private boolean isValidModeCommandArg(String arg) {
        return !arg.isEmpty()
                && GameManager.isValidGameMode(arg);
    }
}
