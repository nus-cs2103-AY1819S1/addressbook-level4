package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ManageVolunteerCommand;
import seedu.address.logic.commands.SelectVolunteerCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectVolunteerCommand object
 */
public class ManageVolunteerCommandParser implements Parser<ManageVolunteerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectVolunteerCommand
     * and returns an ManageVolunteerCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ManageVolunteerCommand parse(String args) throws ParseException {
        try {
            Index index = ParserVolunteerUtil.parseIndex(args);
            return new ManageVolunteerCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectVolunteerCommand.MESSAGE_USAGE), pe);
        }
    }
}
