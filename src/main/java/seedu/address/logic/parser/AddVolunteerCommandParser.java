package seedu.address.logic.parser;


import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddVolunteerCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.volunteer.VolunteerAddress;
import seedu.address.model.volunteer.VolunteerEmail;
import seedu.address.model.volunteer.VolunteerName;
import seedu.address.model.volunteer.Gender;
import seedu.address.model.volunteer.Birthday;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.model.volunteer.VolunteerPhone;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddVolunteerCommand object
 */
public class AddVolunteerCommandParser implements Parser<AddVolunteerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddVolunteerCommand
     * and returns an AddVolunteerCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddVolunteerCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_VOLUNTEER_NAME, PREFIX_VOLUNTEER_GENDER, PREFIX_VOLUNTEER_BIRTHDAY,
                        PREFIX_VOLUNTEER_PHONE, PREFIX_VOLUNTEER_EMAIL, PREFIX_VOLUNTEER_ADDRESS, PREFIX_VOLUNTEER_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_VOLUNTEER_NAME, PREFIX_VOLUNTEER_GENDER, PREFIX_VOLUNTEER_BIRTHDAY,
                PREFIX_VOLUNTEER_ADDRESS, PREFIX_VOLUNTEER_PHONE, PREFIX_VOLUNTEER_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddVolunteerCommand.MESSAGE_USAGE));
        }

        VolunteerName name = ParserVolunteerUtil.parseName(argMultimap.getValue(PREFIX_VOLUNTEER_NAME).get());
        Gender gender = ParserVolunteerUtil.parseGender(argMultimap.getValue(PREFIX_VOLUNTEER_GENDER).get());
        Birthday birthday = ParserVolunteerUtil.parseBirthday(argMultimap.getValue(PREFIX_VOLUNTEER_BIRTHDAY).get());
        VolunteerPhone phone = ParserVolunteerUtil.parsePhone(argMultimap.getValue(PREFIX_VOLUNTEER_PHONE).get());
        VolunteerEmail email = ParserVolunteerUtil.parseEmail(argMultimap.getValue(PREFIX_VOLUNTEER_EMAIL).get());
        VolunteerAddress address = ParserVolunteerUtil.parseAddress(argMultimap.getValue(PREFIX_VOLUNTEER_ADDRESS).get());
        Set<Tag> tagList = ParserVolunteerUtil.parseTags(argMultimap.getAllValues(PREFIX_VOLUNTEER_TAG));

        Volunteer volunteer = new Volunteer(name, gender, birthday, phone, email, address, tagList);

        return new AddVolunteerCommand(volunteer);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
