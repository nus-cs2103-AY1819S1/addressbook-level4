package seedu.address.logic.parser;

import seedu.address.logic.SignupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.user.Username;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class SignupCommandParser implements Parser<SignupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LoginCommand
     * and returns an LoginCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public SignupCommand parse(String userInput) throws ParseException {
        try {
            Username username = new Username(userInput.trim());
            return new SignupCommand(username);
        } catch (IllegalArgumentException iae) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SignupCommand.MESSAGE_USAGE));
        }
    }
}
