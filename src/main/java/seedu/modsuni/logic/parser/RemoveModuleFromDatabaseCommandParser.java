package seedu.modsuni.logic.parser;

import static seedu.modsuni.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.modsuni.logic.commands.RemoveModuleFromDatabaseCommand;
import seedu.modsuni.logic.parser.exceptions.ParseException;
import seedu.modsuni.model.module.Code;

/**
 * Parses input arguments and creates a new RemoveModuleFromDatabaseCommand.
 */
public class RemoveModuleFromDatabaseCommandParser implements Parser<RemoveModuleFromDatabaseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveModuleFromDatabaseCommand
     * and returns a RemoveModuleFromDatabaseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveModuleFromDatabaseCommand parse(String args) throws ParseException {
        try {
            Code code = ParserUtil.parseModuleCode(args);
            return new RemoveModuleFromDatabaseCommand(code.toString());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemoveModuleFromDatabaseCommand.MESSAGE_USAGE));
        }
    }
}
