package seedu.inventory.logic.parser;

import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;

import seedu.inventory.logic.commands.LoginCommand;
import seedu.inventory.logic.parser.exceptions.ParseException;
import seedu.inventory.model.staff.Password;
import seedu.inventory.model.staff.Staff;
import seedu.inventory.model.staff.StaffName;
import seedu.inventory.model.staff.Username;

/**
 * Parses input arguments and creates a new LoginCommand object
 */
public class LoginCommandParser implements Parser<LoginCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LoginCommand
     * and returns and LoginCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoginCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_PASSWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        }

        Username username = ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME).get());
        Password password = ParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD).get());
        StaffName staffName = new StaffName("dummy");
        Staff.Role role = ParserUtil.parseRole("user");

        Staff staff = new Staff(username, password, staffName, role);

        return new LoginCommand(staff);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
