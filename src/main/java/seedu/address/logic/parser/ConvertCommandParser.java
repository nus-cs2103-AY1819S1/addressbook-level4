package seedu.address.logic.parser;

//@@author lancelotwilow
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.logic.commands.ConvertCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.transformation.Transformation;

/**
 * Parses input arguments and creates a new ExampleCommand object
 */
public class ConvertCommandParser implements Parser<ConvertCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ExampleCommand
     * and returns an ExampleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ConvertCommand parse(String args) throws ParseException {
        String[] all = Stream.of(args.split(" "))
                .filter(x -> !x.equals(""))
                .toArray(String[]::new);
        if (all.length < 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConvertCommand.MESSAGE_USAGE));
        }
        if (all[0].equals("raw")) {
            if (all.length < 2) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConvertCommand.MESSAGE_USAGE_RAW));
            }
            return new ConvertCommand(Arrays.copyOfRange(all, 1, all.length));
        }
        String operation = all[0];
        String[] cmds = Arrays.copyOfRange(all, 1, all.length);
        return new ConvertCommand(new Transformation(operation, cmds));
    }
}
