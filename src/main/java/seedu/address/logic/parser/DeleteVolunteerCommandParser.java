package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteVolunteerCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteVolunteerCommand object
 */
public class DeleteVolunteerCommandParser implements Parser<DeleteVolunteerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteVolunteerCommand
     * and returns an DeleteVolunteerCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteVolunteerCommand parse(String args) throws ParseException {
        try {
            Index index = ParserVolunteerUtil.parseIndex(args);
            return new DeleteVolunteerCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteVolunteerCommand.MESSAGE_USAGE), pe);
        }
    }

}
