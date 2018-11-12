package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.commands.SignUpCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.common.Name;
import seedu.address.model.common.Password;
import seedu.address.model.common.Username;
import seedu.address.model.user.User;

/**
 * Parses input arguments and creates a new SignUpCommand object
 */
public class SignUpCommandParser implements Parser<SignUpCommand> {

    @Override
    public SignUpCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_USERNAME, PREFIX_PASSWORD);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_USERNAME, PREFIX_PASSWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SignUpCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Username username = ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME).get());
        Password password = ParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD).get());

        User user = new User(name, username, password);

        return new SignUpCommand(user);
    }
}
