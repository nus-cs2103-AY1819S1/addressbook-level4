package seedu.restaurant.logic.parser.account;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_ID;
import static seedu.restaurant.logic.parser.util.ParserUtil.arePrefixesPresent;

import seedu.restaurant.logic.commands.account.DeregisterCommand;
import seedu.restaurant.logic.parser.Parser;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.logic.parser.util.ArgumentMultimap;
import seedu.restaurant.logic.parser.util.ArgumentTokenizer;
import seedu.restaurant.model.account.Account;
import seedu.restaurant.model.account.Username;

//@@author AZhiKai
/**
 * Parses input arguments and creates a new {@code DeregisterCommand} object.
 */
public class DeregisterCommandParser implements Parser<DeregisterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeregisterCommand and returns a
     * DeregisterCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeregisterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_ID) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeregisterCommand.MESSAGE_USAGE));
        }

        Username username = AccountParserUtil.parseUsername(argMultimap.getValue(PREFIX_ID).get());

        // Password and name not required since it does not involve a password to deregister the account
        return new DeregisterCommand(new Account(username));
    }
}
