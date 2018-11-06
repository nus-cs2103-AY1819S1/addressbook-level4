package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ExportVolunteerCsvCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new {@code ExportVolunteerCsvCommand} object
 */
public class ExportVolunteerCsvCommandParser implements Parser<ExportVolunteerCsvCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code ExportVolunteerCsvCommand}
     * and returns a {@code ExportVolunteerCsvCommand} object for execution.
     * @param userInput as a {@code String}
     * @return ExportVolunteerCsvCommand object
     * @throws ParseException if the user input does not abide by the expected format
     */
    public ExportVolunteerCsvCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        ArrayList<Index> index = new ArrayList<Index>();
        try {
            String[] inputs = userInput.split(" ");
            for ( String i : inputs ) {
                index.add(ParserUtil.parseIndex(i));
            }
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ExportVolunteerCsvCommand.MESSAGE_USAGE), pe);
        }

        return new ExportVolunteerCsvCommand(index);
    }
}
