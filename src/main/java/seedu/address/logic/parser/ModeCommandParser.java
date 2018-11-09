package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ModeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.game.GameManager;

/**
 * Parses input arguments and creates a new ModeCommand object
 */
public class ModeCommandParser implements Parser<ModeCommand> {

    /**
     * Parses the given {@code String} of argument in the context of the ModeCommand
     * and returns an ModeCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ModeCommand parse(String args) throws ParseException {
        String trimmedArg = args.trim();
        if (isValidOneArgumentModeCommand(trimmedArg)) {
            return new ModeCommand(trimmedArg);
        }

        String[] splitArgs = trimmedArg.split("\\s+");
        if (isValidTwoArgumentModeCommand(splitArgs)) {
            return new ModeCommand(splitArgs[0], splitArgs[1]);
        }

        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModeCommand.MESSAGE_USAGE));
    }

    private boolean isValidOneArgumentModeCommand(String arg) {
        return !arg.isEmpty()
                && GameManager.isValidGameMode(arg);
    }

    private boolean isValidTwoArgumentModeCommand(String[] args) {
        if (args.length != 2) {
            return false;
        }

        return GameManager.isValidGameMode(args[0]) &&
                GameManager.isValidGameDifficulty(args[1]);

    }
}
