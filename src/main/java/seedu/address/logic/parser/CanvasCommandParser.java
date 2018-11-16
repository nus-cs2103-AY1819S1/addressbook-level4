package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.ENTIRE_CANVAS_MESSAGE;

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
    /**
     * Parses the sub-command and arguments for the Canvas command
     *
     * @throws ParseException if the user input does not conform an expected format
     */
    public CanvasCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String[] argumentArray = args.trim().split(" ", 2);
        String subcommand = argumentArray[0];
        String argument = (argumentArray.length > 1) ? argumentArray[1] : null;

        switch (subcommand) {
        case "auto-resize": {
            if (argument == null) {
                throw new ParseException(CanvasAutoResizeCommand.MESSAGE_USAGE);
            }
            return new CanvasAutoResizeCommand(argument);
        }
        case "bgcolor": {
            if (argument == null) {
                throw new ParseException(CanvasBgcolorCommand.MESSAGE_USAGE);
            }
            return new CanvasBgcolorCommand(argument);
        }
        case "size": {
            return new CanvasSizeCommand(argument);
        }
        default:
            throw new ParseException(ENTIRE_CANVAS_MESSAGE);
        }
    }
}
