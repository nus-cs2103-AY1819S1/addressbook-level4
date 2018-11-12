package seedu.superta.logic.parser;

import static seedu.superta.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.stream.Stream;

import seedu.superta.logic.commands.CreateTutorialGroupCommand;
import seedu.superta.logic.parser.exceptions.ParseException;
import seedu.superta.model.tutorialgroup.TutorialGroup;

/**
 * Parser for the create-tutorial-group command.
 */
public class CreateTutorialGroupCommandParser implements Parser<CreateTutorialGroupCommand> {
    @Override
    public CreateTutorialGroupCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap =
            ArgumentTokenizer.tokenize(args,
                                       CliSyntax.PREFIX_TUTORIAL_GROUP_NAME,
                                       CliSyntax.PREFIX_TUTORIAL_GROUP_ID);
        if (!arePrefixesPresent(argumentMultimap,
                                CliSyntax.PREFIX_TUTORIAL_GROUP_NAME,
                                CliSyntax.PREFIX_TUTORIAL_GROUP_ID)
            || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                   CreateTutorialGroupCommand.MESSAGE_USAGE));
        }

        String name = ParserUtil.parseTutorialGroupName(
            argumentMultimap.getValue(CliSyntax.PREFIX_TUTORIAL_GROUP_NAME).get());
        String id = ParserUtil.parseTutorialGroupId(
            argumentMultimap.getValue(CliSyntax.PREFIX_TUTORIAL_GROUP_ID).get()
        );

        TutorialGroup tg = new TutorialGroup(id, name);
        return new CreateTutorialGroupCommand(tg);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
