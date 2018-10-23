package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.logic.commands.AddApptCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;

import java.util.stream.Stream;


public class AddApptCommandParser implements Parser<AddApptCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddApptCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddApptCommand parse(String userInput) throws ParseException {
        /*ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_TIME, PREFIX_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_TIME, PREFIX_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddApptCommand.MESSAGE_USAGE));
        }

        Date date = new Date(1, 1, 2000);
        Time time = new Time(0, 0);
        Person patient = new Person()

        Appointment appt = new Appointment(date, time, patient);

        return new AddApptCommand(appt);*/
        return null;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
