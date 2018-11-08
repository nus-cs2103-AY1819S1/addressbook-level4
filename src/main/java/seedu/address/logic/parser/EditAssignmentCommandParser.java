package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditAssignmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditAssignmentCommand object
 */
public class EditAssignmentCommandParser implements Parser<EditAssignmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * EditAssignmentCommand and returns an EditAssignmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditAssignmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ASSIGNMENT_NAME, PREFIX_AUTHOR, PREFIX_ASSIGNMENT_DESCRIPTION);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditAssignmentCommand.MESSAGE_USAGE), pe);
        }

        EditAssignmentCommand.EditAssignmentDescriptor editAssignmentDescriptor =
                new EditAssignmentCommand.EditAssignmentDescriptor();
        if (argMultimap.getValue(PREFIX_ASSIGNMENT_NAME).isPresent()) {
            editAssignmentDescriptor.setAssignmentName(ParserUtil.parseProjectName(
                    argMultimap.getValue(PREFIX_ASSIGNMENT_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_AUTHOR).isPresent()) {
            editAssignmentDescriptor.setAuthor(ParserUtil.parseName(argMultimap.getValue(PREFIX_AUTHOR).get()));
        }
        if (argMultimap.getValue(PREFIX_ASSIGNMENT_DESCRIPTION).isPresent()) {
            editAssignmentDescriptor.setDescription(ParserUtil.parseDescription(argMultimap.getValue(
                    PREFIX_ASSIGNMENT_DESCRIPTION).get()));
        }

        if (!editAssignmentDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditAssignmentCommand.MESSAGE_NOT_EDITED);
        }

        return new EditAssignmentCommand(index, editAssignmentDescriptor);
    }
}
