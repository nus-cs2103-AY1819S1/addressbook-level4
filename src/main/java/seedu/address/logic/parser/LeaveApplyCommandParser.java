package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DESCRIPTION;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.LeaveApplyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.leaveapplication.Description;
import seedu.address.model.leaveapplication.LeaveApplication;
import seedu.address.model.leaveapplication.LeaveStatus;

/**
 * Parses input arguments and creates a new LeaveApplyCommand object
 */
public class LeaveApplyCommandParser implements Parser<LeaveApplyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LeaveApplyCommand
     * and returns an LeaveApplyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LeaveApplyCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_LEAVE_DESCRIPTION, PREFIX_LEAVE_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_LEAVE_DESCRIPTION, PREFIX_LEAVE_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LeaveApplyCommand.MESSAGE_USAGE));
        }

        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_LEAVE_DESCRIPTION).get());
        LeaveStatus leaveStatus = ParserUtil.parseLeaveStatus("PENDING");
        List<LocalDate> dateList = ParserUtil.parseDates(argMultimap.getAllValues(PREFIX_LEAVE_DATE));

        LeaveApplication leaveApplication = new LeaveApplication(description, leaveStatus, dateList);

        return new LeaveApplyCommand(leaveApplication);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
