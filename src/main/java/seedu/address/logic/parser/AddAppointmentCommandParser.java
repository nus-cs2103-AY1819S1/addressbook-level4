package seedu.address.logic.parser;

import java.time.LocalDateTime;
import java.util.stream.Stream;
import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Name;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Parses input arguments and creates a new AddAppointmentCommand object
 */
public class AddAppointmentCommandParser implements Parser<AddAppointmentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddAppointmentCommand
     * and returns an AddAppointmentCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAppointmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PATIENT_NAME, PREFIX_DOCTOR_NAME, PREFIX_DATE_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_PATIENT_NAME, PREFIX_DOCTOR_NAME, PREFIX_DATE_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE));
        }

        Name patientName = ParserUtil.parseName(argMultimap.getValue(PREFIX_PATIENT_NAME).get());
        Name doctorName = ParserUtil.parseName(argMultimap.getValue(PREFIX_DOCTOR_NAME).get());
        LocalDateTime dateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATE_TIME).get());
        return new AddAppointmentCommand(patientName, doctorName, dateTime);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
