package seedu.superta.logic.parser;

import static seedu.superta.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_STUDENT_ID;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_TUTORIAL_GROUP_ID;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_SESSION_NAME;

import java.util.Set;

import seedu.superta.logic.commands.MarkAttendanceCommand;
import seedu.superta.logic.parser.exceptions.ParseException;
import seedu.superta.model.attendance.Session;
import seedu.superta.model.student.StudentId;

// @@author triger15
/**
 * Parser for the mark-attendance command.
 */
public class MarkAttendanceCommandParser implements Parser<MarkAttendanceCommand> {
    @Override
    public MarkAttendanceCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args,
               PREFIX_GENERAL_TUTORIAL_GROUP_ID,
               PREFIX_SESSION_NAME,
               PREFIX_GENERAL_STUDENT_ID);
        if (!ParserUtil.arePrefixesPresent(argumentMultimap,
                PREFIX_GENERAL_TUTORIAL_GROUP_ID,
                PREFIX_SESSION_NAME,
                PREFIX_GENERAL_STUDENT_ID)
            || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                   MarkAttendanceCommand.MESSAGE_USAGE));
        }

        String tgId = ParserUtil.parseTutorialGroupId(
            argumentMultimap.getValue(PREFIX_GENERAL_TUTORIAL_GROUP_ID).get()
        );

        Session session = ParserUtil.parseSession(
            argumentMultimap.getValue(PREFIX_SESSION_NAME).get()
        );

        Set<StudentId> stIds = ParserUtil.parseStudentIds(argumentMultimap.getAllValues(PREFIX_GENERAL_STUDENT_ID));

        return new MarkAttendanceCommand(tgId, session, stIds);
    }

}
