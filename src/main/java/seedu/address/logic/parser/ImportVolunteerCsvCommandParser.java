package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ImportVolunteerCsvCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;


public class ImportVolunteerCsvCommandParser implements Parser<ImportVolunteerCsvCommand>{

    /**
     * Parses the given {@code String} of arguments in the context of the {@code ImportVolunteerCsvCommand}
     * and returns a {@code ImportVolunteerCsvCommand} object for execution.
     * @param userInput as a {@code String}
     * @return ImportVolunteerCsvCommand object
     * @throws ParseException if the user input does not abide by the expected format
     */
    public ImportVolunteerCsvCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        try {
            FileReader csvFile = new FileReader(userInput);
            return new ImportVolunteerCsvCommand(csvFile);
        } catch (FileNotFoundException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportVolunteerCsvCommand.FILE_ERROR));
        }
    }
}
