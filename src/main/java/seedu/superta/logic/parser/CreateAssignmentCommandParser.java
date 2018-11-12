package seedu.superta.logic.parser;

import static seedu.superta.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_MAX_MARKS;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_TITLE;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_TUTORIAL_GROUP_ID;

import seedu.superta.logic.commands.CreateAssignmentCommand;
import seedu.superta.logic.parser.exceptions.ParseException;
import seedu.superta.model.assignment.Assignment;
import seedu.superta.model.assignment.Title;

/**
 * Parser for the create-assignment command.
 */
public class CreateAssignmentCommandParser implements Parser<CreateAssignmentCommand> {
    @Override
    public CreateAssignmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args,
               PREFIX_GENERAL_TUTORIAL_GROUP_ID,
               PREFIX_ASSIGNMENT_TITLE,
               PREFIX_ASSIGNMENT_MAX_MARKS);
        if (!ParserUtil.arePrefixesPresent(argumentMultimap,
                PREFIX_GENERAL_TUTORIAL_GROUP_ID,
                PREFIX_ASSIGNMENT_TITLE,
                PREFIX_ASSIGNMENT_MAX_MARKS)
            || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                   CreateAssignmentCommand.MESSAGE_USAGE));
        }

        String tgId = ParserUtil.parseTutorialGroupId(
            argumentMultimap.getValue(PREFIX_GENERAL_TUTORIAL_GROUP_ID).get()
        );

        Title assignmentName = ParserUtil.parseTitle(
            argumentMultimap.getValue(PREFIX_ASSIGNMENT_TITLE).get()
        );

        Double maxMarks = ParserUtil.parseMaxMarks(
            argumentMultimap.getValue(PREFIX_ASSIGNMENT_MAX_MARKS).get()
        );

        Assignment assignment = new Assignment(assignmentName, maxMarks);
        return new CreateAssignmentCommand(tgId, assignment);
    }

}
