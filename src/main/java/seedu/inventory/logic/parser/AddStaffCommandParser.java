package seedu.inventory.logic.parser;

import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;

import seedu.inventory.logic.commands.AddStaffCommand;
import seedu.inventory.logic.parser.exceptions.ParseException;
import seedu.inventory.model.staff.Password;
import seedu.inventory.model.staff.Staff;
import seedu.inventory.model.staff.StaffName;
import seedu.inventory.model.staff.Username;

/**
 * Parses input arguments and creates a new AddStaffCommand Object
 */
public class AddStaffCommandParser implements Parser<AddStaffCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddStaffCommand
     * and returns an AddStaffCommand object for execution.
     */
    @Override
    public AddStaffCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_PASSWORD, PREFIX_NAME, PREFIX_ROLE);

        if (!arePrefixesPresent(argumentMultimap, PREFIX_USERNAME, PREFIX_PASSWORD, PREFIX_NAME, PREFIX_ROLE)
                || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStaffCommand.MESSAGE_USAGE));
        }

        Username username = ParserUtil.parseUsername(argumentMultimap.getValue(PREFIX_NAME).get());
        Password password = ParserUtil.parsePassword(argumentMultimap.getValue(PREFIX_PASSWORD).get());
        StaffName staffName = ParserUtil.parseStaffName(argumentMultimap.getValue(PREFIX_NAME).get());
        Staff.Role role = ParserUtil.parseRole(argumentMultimap.getValue(PREFIX_ROLE).get());

        Staff staff = new Staff(username, password, staffName, role);

        return new AddStaffCommand(staff);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
