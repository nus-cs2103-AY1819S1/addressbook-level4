package seedu.superta.logic.parser;

import static seedu.superta.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_ASSIGNMENT_TITLE;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_TUTORIAL_GROUP_ID;
import static seedu.superta.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.superta.logic.commands.ViewAssignmentCommand;
import seedu.superta.logic.parser.exceptions.ParseException;
import seedu.superta.model.assignment.Title;

// @@author Caephler

/**
 * Parser for view-assignment command.
 */
public class ViewAssignmentCommandParser implements Parser<ViewAssignmentCommand> {

    @Override
    public ViewAssignmentCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMap = ArgumentTokenizer.tokenize(
            userInput,
            PREFIX_GENERAL_TUTORIAL_GROUP_ID,
            PREFIX_GENERAL_ASSIGNMENT_TITLE
        );

        if (!arePrefixesPresent(
            argMap,
            PREFIX_GENERAL_TUTORIAL_GROUP_ID,
            PREFIX_GENERAL_ASSIGNMENT_TITLE
        ) || !argMap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                MESSAGE_INVALID_COMMAND_FORMAT,
                ViewAssignmentCommand.MESSAGE_USAGE
            ));
        }

        String tutorialGroupId = ParserUtil.parseTutorialGroupId(
            argMap.getValue(PREFIX_GENERAL_TUTORIAL_GROUP_ID).get()
        );

        Title assignmentTitle = ParserUtil.parseTitle(
            argMap.getValue(PREFIX_GENERAL_ASSIGNMENT_TITLE).get()
        );

        return new ViewAssignmentCommand(tutorialGroupId, assignmentTitle);
    }
}
