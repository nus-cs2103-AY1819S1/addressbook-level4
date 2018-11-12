package seedu.modsuni.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_STUDENT_ENROLLMENT_DATE;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_STUDENT_MAJOR;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_STUDENT_MINOR;

import seedu.modsuni.logic.commands.EditStudentCommand;
import seedu.modsuni.logic.commands.EditStudentCommand.EditStudentDescriptor;
import seedu.modsuni.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditStudentCommand object
 */
public class EditStudentCommandParser implements Parser<EditStudentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * EditStudentCommand and returns an EditStudentCommand object for
     * execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public EditStudentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME,
                PREFIX_STUDENT_ENROLLMENT_DATE, PREFIX_STUDENT_MAJOR,
                PREFIX_STUDENT_MINOR);
        EditStudentDescriptor editStudentDescriptor = new EditStudentDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editStudentDescriptor.setName(
                ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }

        if (argMultimap.getValue(PREFIX_STUDENT_ENROLLMENT_DATE).isPresent()) {
            editStudentDescriptor.setEnrollmentDate(
                ParserUtil.parseEnrollmentDate(argMultimap.getValue(PREFIX_STUDENT_ENROLLMENT_DATE).get()));
        }

        //TODO ParseUtil.parseMajors
        if (argMultimap.getValue(PREFIX_STUDENT_MAJOR).isPresent()) {
            editStudentDescriptor.setMajors(argMultimap.getAllValues(PREFIX_STUDENT_MAJOR));
        }

        //TODO ParseUtil.parseMinors
        if (argMultimap.getValue(PREFIX_STUDENT_MINOR).isPresent()) {
            editStudentDescriptor.setMinors(argMultimap.getAllValues(PREFIX_STUDENT_MINOR));
        }

        if (!editStudentDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditStudentCommand.MESSAGE_NOT_EDITED);
        }

        return new EditStudentCommand(editStudentDescriptor);
    }
}
