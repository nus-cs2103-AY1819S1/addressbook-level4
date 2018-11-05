package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ExportEventXmlCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new {@code ExportEventXmlCommand} object
 */
public class ExportEventXmlCommandParser implements Parser<ExportEventXmlCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code ExportEventXmlCommand}
     * and returns a {@code ExportEventXmlCommand} object for execution.
     * @param userInput as a {@code String}
     * @return ExportEventXmlCommand object
     * @throws ParseException if the user input does not abide by the expected format
     */
    public ExportEventXmlCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        Index index;
        try {
            index = ParserUtil.parseIndex(userInput);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ExportEventXmlCommand.MESSAGE_USAGE), pe);
        }

        return new ExportEventXmlCommand(index);
    }


}
