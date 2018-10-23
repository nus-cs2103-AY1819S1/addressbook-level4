package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACADEMICYEAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULETITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCASION_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCASION_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.address.commons.util.TypeUtil;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddModuleCommand;
import seedu.address.logic.commands.AddOccasionCommand;
import seedu.address.logic.commands.AddPersonCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.AcademicYear;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleTitle;
import seedu.address.model.module.Semester;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.occasion.OccasionDate;
import seedu.address.model.occasion.OccasionLocation;
import seedu.address.model.occasion.OccasionName;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<Command> {
    /**
     * Used for initial separation of type and args.
     */
    private static final Pattern ADD_COMMAND_FORMAT = Pattern.compile("(?<type>\\S+)(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parse(String args) throws ParseException {
        final Matcher matcher = ADD_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String type = matcher.group("type").toUpperCase();
        final String arguments = matcher.group("arguments");

        switch (TypeUtil.valueOf(type)) {
        case MODULE:
            return new AddModuleCommand(parseModuleAddCommand(arguments));
        case PERSON:
            return new AddPersonCommand(parsePersonAddCommand(arguments));
        case OCCASION:
            return new AddOccasionCommand(parseOccasionAddCommand(arguments));
        default:
            break;
        }
        return null;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns a new Person object of the given parameters.
     * @throws ParseException if the user input does not conform the expected format
     */
    public Person parsePersonAddCommand(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Person person = new Person(name, phone, email, address, tagList);
        return person;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns a new Module object of the given parameters.
     * @throws ParseException if the user input does not conform the expected format
     */
    public Module parseModuleAddCommand(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULECODE, PREFIX_MODULETITLE, PREFIX_ACADEMICYEAR,
                        PREFIX_SEMESTER, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULECODE, PREFIX_MODULETITLE, PREFIX_ACADEMICYEAR,
                PREFIX_SEMESTER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULECODE).get());
        ModuleTitle moduleTitle = ParserUtil.parseModuleTitle(argMultimap.getValue(PREFIX_MODULETITLE).get());
        AcademicYear academicYear = ParserUtil.parseAcademicYear(argMultimap.getValue(PREFIX_ACADEMICYEAR).get());
        Semester semester = ParserUtil.parseSemester(argMultimap.getValue(PREFIX_SEMESTER).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Module module = new Module(moduleCode, moduleTitle, academicYear, semester,
                new UniquePersonList(), tagList, TypeUtil.OCCASION);
        return module;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns a new Occasion object of the given parameters.
     * @throws ParseException if the user input does not conform the expected format
     */
    public Occasion parseOccasionAddCommand(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_OCCASION_NAME, PREFIX_OCCASION_DATE, PREFIX_LOCATION,
                        PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_OCCASION_NAME, PREFIX_OCCASION_DATE, PREFIX_LOCATION,
                PREFIX_SEMESTER) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        OccasionName occasionName = ParserUtil.parseOccasionName(argMultimap.getValue(PREFIX_OCCASION_NAME).get());
        OccasionDate occasionDate = ParserUtil.parseOccasionDate(argMultimap.getValue(PREFIX_OCCASION_DATE).get());
        OccasionLocation occasionLocation = ParserUtil.parseOccasionLocation(
                argMultimap.getValue(PREFIX_LOCATION).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Occasion occasion = new Occasion(occasionName, occasionDate, occasionLocation, tagList, TypeUtil.OCCASION);
        return occasion;
    }


    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
