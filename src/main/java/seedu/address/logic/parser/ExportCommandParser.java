package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.nio.file.Path;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.ExportTxtCommand;
import seedu.address.logic.commands.ExportXmlCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parse arguments in export command.
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    @Override
    public ExportCommand parse(String args) throws ParseException {
        String [] tokens = args.trim().split(" ");
        if (tokens.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }

        Path exportedFilePath = ParserUtil.parseFilePath(tokens[1]);
        if (tokens[0].equals("--xml") && exportedFilePath.toString().endsWith(".xml")) {
            return new ExportXmlCommand(exportedFilePath);
        }

        if (tokens[0].equals("--txt") && exportedFilePath.toString().endsWith(".txt")) {
            return new ExportTxtCommand(exportedFilePath);
        }
        throw new ParseException(String.format(ExportCommand.MESSAGE_FILE_TYPE_NOT_SUPPORTED_OR_TYPE_NOT_MATCH));
    }
}
