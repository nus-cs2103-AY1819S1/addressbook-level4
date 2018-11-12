package seedu.superta.logic.parser;

import static seedu.superta.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_ASSIGNMENT_TITLE;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_TUTORIAL_GROUP_ID;
import static seedu.superta.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.superta.logic.commands.DeleteAssignmentCommand;
import seedu.superta.logic.parser.exceptions.ParseException;

/**
 * Parser for DeleteAssignmentCommand.
 */
public class DeleteAssignmentCommandParser implements Parser<DeleteAssignmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteAssignmentCommand
     * and returns an DeleteAssignmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteAssignmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(
                args,
                PREFIX_GENERAL_TUTORIAL_GROUP_ID,
                PREFIX_GENERAL_ASSIGNMENT_TITLE);
        if (!arePrefixesPresent(argumentMultimap,
                PREFIX_GENERAL_TUTORIAL_GROUP_ID,
                PREFIX_GENERAL_ASSIGNMENT_TITLE
        ) || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteAssignmentCommand.MESSAGE_USAGE));
        }

        String tgId = argumentMultimap.getValue(PREFIX_GENERAL_TUTORIAL_GROUP_ID).get();
        String ass = argumentMultimap.getValue(PREFIX_GENERAL_ASSIGNMENT_TITLE).get();

        return new DeleteAssignmentCommand(ass, tgId);
    }
}
