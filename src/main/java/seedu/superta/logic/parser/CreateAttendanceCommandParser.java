package seedu.superta.logic.parser;

import static seedu.superta.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_TUTORIAL_GROUP_ID;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_SESSION_NAME;

import seedu.superta.logic.commands.CreateAttendanceCommand;
import seedu.superta.logic.parser.exceptions.ParseException;
import seedu.superta.model.attendance.Session;

// @@author triger15
/**
 * Parser for the create-attendance command.
 */
public class CreateAttendanceCommandParser implements Parser<CreateAttendanceCommand> {
    @Override
    public CreateAttendanceCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args,
               PREFIX_GENERAL_TUTORIAL_GROUP_ID,
               PREFIX_SESSION_NAME);
        if (!ParserUtil.arePrefixesPresent(argumentMultimap,
                PREFIX_GENERAL_TUTORIAL_GROUP_ID,
                PREFIX_SESSION_NAME)
            || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                   CreateAttendanceCommand.MESSAGE_USAGE));
        }

        String tgId = ParserUtil.parseTutorialGroupId(
            argumentMultimap.getValue(PREFIX_GENERAL_TUTORIAL_GROUP_ID).get()
        );

        Session sessionName = ParserUtil.parseSession(
            argumentMultimap.getValue(PREFIX_SESSION_NAME).get()
        );

        return new CreateAttendanceCommand(tgId, sessionName);
    }

}
