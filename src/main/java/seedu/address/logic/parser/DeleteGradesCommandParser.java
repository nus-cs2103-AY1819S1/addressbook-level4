package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteGradeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteGradesCommand object
 */
public class DeleteGradesCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteGradesCommand
     * and returns an DeleteGradesCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteGradeCommand parse(String args) throws ParseException {
        Index index;

        String[] parsedArgs = args.trim().split("\\s+");

        if (parsedArgs.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteGradeCommand.MESSAGE_USAGE));
        }

        try {
            index = ParserUtil.parseIndex(parsedArgs[0]);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteGradeCommand.MESSAGE_USAGE), pe);
        }

        return new DeleteGradeCommand(index, parsedArgs[1]);
    }
}
