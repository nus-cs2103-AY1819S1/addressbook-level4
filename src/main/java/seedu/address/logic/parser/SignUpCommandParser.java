package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SignUpCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.user.Username;

//@@author JasonChong96
/**
 * Parses input arguments and creates a new SignUpCommand object
 */
public class SignUpCommandParser implements Parser<SignUpCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LoginCommand
     * and returns an LoginCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public SignUpCommand parse(String userInput) throws ParseException {
        try {
            Username username = new Username(userInput.trim());
            return new SignUpCommand(username);
        } catch (IllegalArgumentException iae) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SignUpCommand.MESSAGE_USAGE));
        }
    }
}
