package seedu.superta.logic.parser;

import static seedu.superta.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_STUDENT_ID;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_TUTORIAL_GROUP_ID;
import static seedu.superta.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.superta.logic.commands.RemoveStudentFromTutorialGroupCommand;
import seedu.superta.logic.parser.exceptions.ParseException;
import seedu.superta.model.student.StudentId;

/**
 * Parser for the remove-from-tutorial-group command.
 */
public class RemoveStudentFromTutorialGroupCommandParser implements Parser<RemoveStudentFromTutorialGroupCommand> {
    @Override
    public RemoveStudentFromTutorialGroupCommand parse(String args) throws ParseException {
        ArgumentMultimap argMap = ArgumentTokenizer.tokenize(
            args,
            PREFIX_GENERAL_TUTORIAL_GROUP_ID,
            PREFIX_GENERAL_STUDENT_ID
        );
        if (!arePrefixesPresent(
            argMap,
            PREFIX_GENERAL_TUTORIAL_GROUP_ID,
            PREFIX_GENERAL_STUDENT_ID
        ) || !argMap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveStudentFromTutorialGroupCommand.MESSAGE_USAGE));
        }

        String tutorialGroupId = ParserUtil.parseTutorialGroupId(
            argMap.getValue(PREFIX_GENERAL_TUTORIAL_GROUP_ID).get()
        );

        StudentId studentId = ParserUtil.parseStudentId(
            argMap.getValue(PREFIX_GENERAL_STUDENT_ID).get()
        );

        return new RemoveStudentFromTutorialGroupCommand(tutorialGroupId, studentId);
    }
}
