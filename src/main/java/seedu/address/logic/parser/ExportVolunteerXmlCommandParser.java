package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ExportVolunteerXmlCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code ExportVolunteerXmlCommand} object
 */
public class ExportVolunteerXmlCommandParser implements Parser<ExportVolunteerXmlCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code ExportVolunteerXmlCommand}
     * and returns a {@code ExportVolunteerXmlCommand} object for execution.
     * @param userInput as a {@code String}
     * @return ExportVolunteerXmlCommand object
     * @throws ParseException if the user input does not abide by the expected format
     */
    public ExportVolunteerXmlCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        Index index;
        try {
            index = ParserUtil.parseIndex(userInput);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ExportVolunteerXmlCommand.MESSAGE_USAGE), pe);
        }

        return new ExportVolunteerXmlCommand(index);
    }
}
