package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.ENTIRE_LAYER_MESSAGE;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

import seedu.address.logic.commands.layer.LayerAddCommand;
import seedu.address.logic.commands.layer.LayerCommand;
import seedu.address.logic.commands.layer.LayerDeleteCommand;
import seedu.address.logic.commands.layer.LayerPositionCommand;
import seedu.address.logic.commands.layer.LayerSelectCommand;
import seedu.address.logic.commands.layer.LayerSwapCommand;
import seedu.address.logic.parser.exceptions.ParseException;




//@@author j-lum

/**
 * Parses input arguments and creates the corresponding Google type command object
 */
public class LayerCommandParser {

    private static final Logger logger = LogsCenter.getLogger(CanvasCommandParser.class);

    private String subcommand;
    private String argument;

    /**
     * Parses the sub-command and arguments for the Canvas command
     *
     * @throws ParseException if the user input does not conform an expected format
     */
    public LayerCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String[] argumentArray = args.trim().split(" ", 2);
        subcommand = argumentArray[0];
        argument = (argumentArray.length > 1) ? argumentArray[1] : null;

        switch (subcommand) {
        case "add": {
            if (argument == null) {
                throw new ParseException(LayerAddCommand.MESSAGE_USAGE);
            }
            return new LayerAddCommand(argument);
        }
        case "delete": {
            if (argument == null) {
                throw new ParseException(LayerDeleteCommand.MESSAGE_USAGE);
            }
            return new LayerDeleteCommand(argument);
        }
        case "select": {
            if (argument == null) {
                throw new ParseException(LayerSelectCommand.MESSAGE_USAGE);
            }
            return new LayerSelectCommand(argument);
        }
        case "position": {
            return new LayerPositionCommand(argument);
        }
        case "swap": {
            if (argument == null) {
                throw new ParseException(LayerSwapCommand.MESSAGE_USAGE);
            }
            return new LayerSwapCommand(argument);
        }
        default:
            throw new ParseException(ENTIRE_LAYER_MESSAGE);
        }
    }
}
