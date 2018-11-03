package seedu.restaurant.logic.parser.account;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_ID;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_PASSWORD;
import static seedu.restaurant.logic.parser.util.ParserUtil.arePrefixesPresent;

import seedu.restaurant.logic.commands.account.LoginCommand;
import seedu.restaurant.logic.parser.Parser;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.logic.parser.util.ArgumentMultimap;
import seedu.restaurant.logic.parser.util.ArgumentTokenizer;
import seedu.restaurant.model.account.Account;
import seedu.restaurant.model.account.Password;
import seedu.restaurant.model.account.Username;

//@@author AZhiKai
/**
 * Parses input arguments and creates a new {@code LoginCommand} object.
 */
public class LoginCommandParser implements Parser<LoginCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LoginCommand and returns a LoginCommand object
     * for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoginCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ID, PREFIX_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_ID, PREFIX_PASSWORD) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        }

        Username username = AccountParserUtil.parseUsername(argMultimap.getValue(PREFIX_ID).get());
        Password password = AccountParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD).get());

        Account account = new Account(username, password);

        return new LoginCommand(account);
    }
}
