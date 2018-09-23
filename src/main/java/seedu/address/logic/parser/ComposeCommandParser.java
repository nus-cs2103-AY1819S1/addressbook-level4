package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.ComposeCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new AddCommand object
 */
public class ComposeCommandParser implements Parser<ComposeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ComposeCommand
     * and returns an ComposeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ComposeCommand parse(String args) throws ParseException {
        return new ComposeCommand();
    }

}
