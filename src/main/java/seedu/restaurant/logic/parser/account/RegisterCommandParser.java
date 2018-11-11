package seedu.restaurant.logic.parser.account;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_ID;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_NAME;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_PASSWORD;
import static seedu.restaurant.logic.parser.util.ParserUtil.arePrefixesPresent;

import seedu.restaurant.logic.commands.account.RegisterCommand;
import seedu.restaurant.logic.parser.Parser;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.logic.parser.util.ArgumentMultimap;
import seedu.restaurant.logic.parser.util.ArgumentTokenizer;
import seedu.restaurant.model.account.Account;
import seedu.restaurant.model.account.Name;
import seedu.restaurant.model.account.Password;
import seedu.restaurant.model.account.Username;

//@@author AZhiKai

/**
 * Parses input arguments and creates a new {@code RegisterCommand} object.
 */
public class RegisterCommandParser implements Parser<RegisterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RegisterCommand and returns a RegisterCommand
     * object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RegisterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ID, PREFIX_PASSWORD, PREFIX_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_ID, PREFIX_PASSWORD, PREFIX_NAME) || !argMultimap.getPreamble()
                .isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RegisterCommand.MESSAGE_USAGE));
        }

        Username username = AccountParserUtil.parseUsername(argMultimap.getValue(PREFIX_ID).get());
        Password password = AccountParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD).get());
        Name name = AccountParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());

        Account account = new Account(username, password, name);

        return new RegisterCommand(account);
    }
}
