package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.ENTIRE_CANVAS_MESSAGE;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.canvas.CanvasAutoResizeCommand;
import seedu.address.logic.commands.canvas.CanvasBgcolorCommand;
import seedu.address.logic.commands.canvas.CanvasCommand;
import seedu.address.logic.commands.canvas.CanvasSizeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author j-lum

/**
 * Parses input arguments and creates the corresponding Google type command object
 */
public class CanvasCommandParser {

    private static final Logger logger = LogsCenter.getLogger(CanvasCommandParser.class);

    private String subcommand;
    private String argument;

    /**
     * Parses the sub-command and arguments for the Canvas command
     *
     * @throws ParseException if the user input does not conform an expected format
     */
    public CanvasCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String[] argumentArray = args.trim().split(" ", 2);
        subcommand = argumentArray[0];
        argument = (argumentArray.length > 1) ? argumentArray[1] : null;

        switch (subcommand) {
        case "auto-resize": {
            if (argument == null) {
                throw new ParseException(CanvasAutoResizeCommand.MESSAGE_USAGE);
            }
            return new CanvasAutoResizeCommand(argument);
        }
        case "bgcolor": {
            return new CanvasBgcolorCommand(argument);
        }
        case "size": {
            logger.info(String.format("arg1 %s arg2 %s", subcommand, argument));
            return new CanvasSizeCommand(argument);
        }
        default:
            throw new ParseException(ENTIRE_CANVAS_MESSAGE);
        }
    }
}
