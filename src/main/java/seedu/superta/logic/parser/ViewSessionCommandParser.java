package seedu.superta.logic.parser;

import static seedu.superta.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_TUTORIAL_GROUP_ID;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_SESSION_NAME;
import static seedu.superta.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.superta.logic.commands.ViewSessionCommand;
import seedu.superta.logic.parser.exceptions.ParseException;

// @@author Caephler
/**
 * Parses input for the view-session command.
 */
public class ViewSessionCommandParser implements Parser<ViewSessionCommand> {
    @Override
    public ViewSessionCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_GENERAL_TUTORIAL_GROUP_ID,
                PREFIX_SESSION_NAME);
        if (!arePrefixesPresent(argumentMultimap, PREFIX_GENERAL_TUTORIAL_GROUP_ID, PREFIX_SESSION_NAME)
            || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewSessionCommand.MESSAGE_USAGE));
        }

        String tutorialGroupId = argumentMultimap.getValue(PREFIX_GENERAL_TUTORIAL_GROUP_ID).get();
        String sessionId = argumentMultimap.getValue(PREFIX_SESSION_NAME).get();

        return new ViewSessionCommand(tutorialGroupId, sessionId);
    }
}
