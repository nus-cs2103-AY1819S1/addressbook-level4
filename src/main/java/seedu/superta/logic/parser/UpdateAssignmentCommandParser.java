package seedu.superta.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.superta.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NEW_MAX_MARKS;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_ASSIGNMENT_TITLE;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_NEW_ASSIGNMENT_TITLE;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_TUTORIAL_GROUP_ID;
import static seedu.superta.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.superta.logic.commands.UpdateAssignmentCommand;
import seedu.superta.logic.commands.UpdateAssignmentCommand.UpdateAssignmentDescriptor;
import seedu.superta.logic.parser.exceptions.ParseException;
import seedu.superta.model.assignment.Title;

/**
 * Parses input arguments and creates an UpdateAssignmentCommand object.
 */
public class UpdateAssignmentCommandParser implements Parser<UpdateAssignmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateAssignmentCommand
     * and returns an UpdateAssignmentCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public UpdateAssignmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(
                args,
                PREFIX_GENERAL_TUTORIAL_GROUP_ID,
                PREFIX_GENERAL_ASSIGNMENT_TITLE,
                PREFIX_GENERAL_NEW_ASSIGNMENT_TITLE,
                PREFIX_ASSIGNMENT_NEW_MAX_MARKS);

        if (!arePrefixesPresent(argumentMultimap,
                PREFIX_GENERAL_TUTORIAL_GROUP_ID,
                PREFIX_GENERAL_ASSIGNMENT_TITLE
        ) || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UpdateAssignmentCommand.MESSAGE_USAGE));
        }

        String tgId = ParserUtil.parseTutorialGroupId(
                argumentMultimap.getValue(PREFIX_GENERAL_TUTORIAL_GROUP_ID).get()
        );

        Title assignmentTitle = ParserUtil.parseTitle(
                argumentMultimap.getValue(PREFIX_GENERAL_ASSIGNMENT_TITLE).get()
        );

        UpdateAssignmentDescriptor updateAssignmentDescriptor = new UpdateAssignmentDescriptor();
        if (argumentMultimap.getValue(PREFIX_GENERAL_NEW_ASSIGNMENT_TITLE).isPresent()) {
            updateAssignmentDescriptor.setAssignmentTitle(ParserUtil.parseTitle(
                    argumentMultimap.getValue(PREFIX_GENERAL_NEW_ASSIGNMENT_TITLE).get()));
        }
        if (argumentMultimap.getValue(PREFIX_ASSIGNMENT_NEW_MAX_MARKS).isPresent()) {
            updateAssignmentDescriptor.setMaxMarks(ParserUtil.parseMaxMarks(
                    argumentMultimap.getValue(PREFIX_ASSIGNMENT_NEW_MAX_MARKS).get()));
        }

        return new UpdateAssignmentCommand(tgId, assignmentTitle, updateAssignmentDescriptor);
    }
}
