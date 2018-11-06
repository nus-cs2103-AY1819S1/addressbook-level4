package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.CreateConvertCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.transformation.Transformation;

/**
 * .
 */
public class CreateConvertCommandParser implements Parser<CreateConvertCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ExampleCommand
     * and returns an ExampleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateConvertCommand parse(String args) throws ParseException {
        String[] all = args.split(" ");
        List<Transformation> transformations = new ArrayList<>();
        if (all.length < 3 || all[1].contains("|")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateConvertCommand.MESSAGE_USAGE));
        }
        for (int i = 2; i < all.length; i++) {
            String command = all[i];
            String[] cmds = command.split("\\|");
            String name = cmds[0];
            cmds = Arrays.copyOfRange(cmds, 1, cmds.length);
            transformations.add(new Transformation(name, cmds));
        }
        return new CreateConvertCommand(all[1], transformations);
    }
}
