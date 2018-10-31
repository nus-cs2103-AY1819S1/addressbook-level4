package seedu.thanepark.logic.parser;

import static seedu.thanepark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.thanepark.logic.commands.AllCommandWords;
import seedu.thanepark.logic.commands.HelpCommand;
import seedu.thanepark.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new HelpCommand object.
 */
public class HelpCommandParser implements Parser<HelpCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the HelpCommand
     * and returns an HelpCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public HelpCommand parse(String args) throws ParseException {
        String[] options = args.trim().split(" ");

        if (!matchesExpectedFormat(options)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        return new HelpCommand(options);
    }

    /**
     * Returns true if matches "help", "help more" or "help [commandWord]"
     */
    private static boolean matchesExpectedFormat(String[] options) {
        return options.length == 1 && options[0].isEmpty()
                || options.length == 1 && options[0].equals(HelpCommand.MORE_INFO_FLAG)
                || options.length == 1 && AllCommandWords.isCommandWord(options[0]);
    }

}
