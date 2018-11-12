package seedu.superta.logic.parser;

import static seedu.superta.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_MARKS;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_ASSIGNMENT_TITLE;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_STUDENT_ID;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_TUTORIAL_GROUP_ID;
import static seedu.superta.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.superta.logic.commands.GradeAssignmentCommand;
import seedu.superta.logic.parser.exceptions.ParseException;
import seedu.superta.model.assignment.Grade;
import seedu.superta.model.assignment.Title;
import seedu.superta.model.student.StudentId;

/**
 * Parser for the grade-assignment command.
 */
public class GradeAssignmentCommandParser implements Parser<GradeAssignmentCommand> {

    @Override
    public GradeAssignmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMap = ArgumentTokenizer.tokenize(
            args,
            PREFIX_GENERAL_TUTORIAL_GROUP_ID,
            PREFIX_GENERAL_ASSIGNMENT_TITLE,
            PREFIX_GENERAL_STUDENT_ID,
            PREFIX_ASSIGNMENT_MARKS
        );

        if (!arePrefixesPresent(
            argMap,
            PREFIX_GENERAL_TUTORIAL_GROUP_ID,
            PREFIX_GENERAL_ASSIGNMENT_TITLE,
            PREFIX_GENERAL_STUDENT_ID,
            PREFIX_ASSIGNMENT_MARKS
        ) || !argMap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, GradeAssignmentCommand.MESSAGE_USAGE));
        }

        String tgId = ParserUtil.parseTutorialGroupId(
            argMap.getValue(PREFIX_GENERAL_TUTORIAL_GROUP_ID).get()
        );
        Title asId = ParserUtil.parseTitle(
            argMap.getValue(PREFIX_GENERAL_ASSIGNMENT_TITLE).get()
        );
        StudentId stId = ParserUtil.parseStudentId(
            argMap.getValue(PREFIX_GENERAL_STUDENT_ID).get()
        );
        Double marks = ParserUtil.parseGrade(
            argMap.getValue(PREFIX_ASSIGNMENT_MARKS).get()
        );

        Grade grade = new Grade(tgId, asId, stId, marks);
        return new GradeAssignmentCommand(grade);
    }
}
