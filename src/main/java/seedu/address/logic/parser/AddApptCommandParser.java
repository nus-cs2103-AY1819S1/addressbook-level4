package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.logic.commands.AddApptCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.Time;
import seedu.address.model.person.Person;
import seedu.address.model.person.Name;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Address;
import seedu.address.model.tag.Tag;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;


public class AddApptCommandParser implements Parser<AddApptCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddApptCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddApptCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_TIME, PREFIX_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_TIME, PREFIX_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddApptCommand.MESSAGE_USAGE));
        }

        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        Time time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());
        //Patient patient = ParserUtil.parseId(argMultimap.getValue(PREFIX_ID).get);

        //to replace with patient id
        Name name = new Name("asdf");
        Email email = new Email("e@e.com");
        Phone phone = new Phone("222");
        Address address = new Address("a");
        Set<Tag> tags = new HashSet<Tag>();
        Person person = new Person(name, phone, email, address, tags);

        Appointment appt = new Appointment(date, time, person);

        return new AddApptCommand(appt);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
