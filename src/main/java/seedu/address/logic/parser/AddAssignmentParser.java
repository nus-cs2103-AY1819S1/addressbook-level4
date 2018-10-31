package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddAssignmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.leaveapplication.Description;
import seedu.address.model.person.Name;
import seedu.address.model.project.Assignment;
import seedu.address.model.project.ProjectName;

/**
 * Parses input arguments and creates a new AddAssignmentCommand object
 */
public class AddAssignmentParser implements Parser<AddAssignmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddAssignmentCommand
     * and returns an AddAssignmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAssignmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ASSIGNMENT_NAME, PREFIX_AUTHOR, PREFIX_ASSIGNMENT_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_ASSIGNMENT_NAME, PREFIX_AUTHOR, PREFIX_ASSIGNMENT_DESCRIPTION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAssignmentCommand.MESSAGE_USAGE));
        }

        ProjectName name = ParserUtil.parseProjectName(argMultimap.getValue(PREFIX_ASSIGNMENT_NAME).get());
        Name author = ParserUtil.parseName(argMultimap.getValue(PREFIX_AUTHOR).get());
        Description description =
                ParserUtil.parseDescription(argMultimap.getValue(PREFIX_ASSIGNMENT_DESCRIPTION).get());
        Assignment assignment = new Assignment(name, author, description);

        return new AddAssignmentCommand(assignment);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
