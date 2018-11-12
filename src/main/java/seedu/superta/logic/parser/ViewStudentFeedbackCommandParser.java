package seedu.superta.logic.parser;

import static seedu.superta.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_STUDENT_ID;

import seedu.superta.logic.commands.ViewStudentFeedbackCommand;
import seedu.superta.logic.parser.exceptions.ParseException;
import seedu.superta.model.student.StudentId;

/**
 * Parses input arguments and creates a new ViewStudentFeedbackCommand object.
 */
public class ViewStudentFeedbackCommandParser implements Parser<ViewStudentFeedbackCommand> {

    /**
     * Parses the given {@code String} argument in the context of the ViewStudentFeedbackCommand
     * and returns an ViewStudentFeedbackCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewStudentFeedbackCommand parse(String arg) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(arg,
                PREFIX_STUDENT_ID);
        if (!ParserUtil.arePrefixesPresent(argumentMultimap,
                PREFIX_STUDENT_ID)
                || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ViewStudentFeedbackCommand.MESSAGE_USAGE));
        }
        StudentId stId = ParserUtil.parseStudentId(argumentMultimap.getValue(PREFIX_STUDENT_ID).get());

        return new ViewStudentFeedbackCommand(stId);
    }
}
