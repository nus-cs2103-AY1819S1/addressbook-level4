package seedu.superta.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.superta.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP_ID;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP_NAME;

import seedu.superta.logic.commands.UpdateTutorialGroupCommand;
import seedu.superta.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UpdateTutorialGroupCommand object
 */
public class UpdateTutorialGroupCommandParser implements Parser<UpdateTutorialGroupCommand> {

    /**
     * Parses the given {@Code String} of arguments in the context of the UpdateTutorialGroupCommand
     * and returns an UpdateTutorialGroupCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public UpdateTutorialGroupCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TUTORIAL_GROUP_ID,
                                                                       PREFIX_TUTORIAL_GROUP_NAME);

        UpdateTutorialGroupCommand.UpdateTutorialGroupDescriptor descriptor = new UpdateTutorialGroupCommand
            .UpdateTutorialGroupDescriptor();
        String id;
        if (argumentMultimap.getValue(PREFIX_TUTORIAL_GROUP_ID).isPresent()) {
            id = ParserUtil.parseString(argumentMultimap.getValue(PREFIX_TUTORIAL_GROUP_ID).get());
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateTutorialGroupCommand
                .MESSAGE_USAGE));
        }
        if (argumentMultimap.getValue(PREFIX_TUTORIAL_GROUP_NAME).isPresent()) {
            descriptor.setName(ParserUtil.parseString(argumentMultimap.getValue(PREFIX_TUTORIAL_GROUP_NAME).get()));
        }

        if (!descriptor.isAnyFieldEdited()) {
            throw new ParseException(UpdateTutorialGroupCommand.MESSAGE_NOT_EDITED);
        }

        return new UpdateTutorialGroupCommand(id, descriptor);
    }
}
