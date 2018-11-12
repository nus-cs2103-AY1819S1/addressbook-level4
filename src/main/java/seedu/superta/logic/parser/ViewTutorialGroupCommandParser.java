package seedu.superta.logic.parser;

import static seedu.superta.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP_ID;

import java.util.stream.Stream;

import seedu.superta.logic.commands.ViewTutorialGroupCommand;
import seedu.superta.logic.parser.exceptions.ParseException;

/**
 * Parser for the view-tutorial-group command.
 */
public class ViewTutorialGroupCommandParser implements Parser<ViewTutorialGroupCommand> {
    @Override
    public ViewTutorialGroupCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args,
                                                                       PREFIX_TUTORIAL_GROUP_ID);
        if (!arePrefixesPresent(argumentMultimap, PREFIX_TUTORIAL_GROUP_ID)
            || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                   ViewTutorialGroupCommand.MESSAGE_USAGE));
        }

        String id = argumentMultimap.getValue(PREFIX_TUTORIAL_GROUP_ID).get();
        return new ViewTutorialGroupCommand(id);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
