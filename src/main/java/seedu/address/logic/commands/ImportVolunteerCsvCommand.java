package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.volunteer.Address;
import seedu.address.model.volunteer.Birthday;
import seedu.address.model.volunteer.Email;
import seedu.address.model.volunteer.Gender;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.Phone;
import seedu.address.model.volunteer.Volunteer;



public class ImportVolunteerCsvCommand extends Command {
    public static final String FILE_ERROR = "Unable to locate the file directory given.\n"
            + "Please check your file format and try again";

    public static final String COMMAND_WORD = "importvolunteercsv";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports a CSV file of the volunteer "
            + "given the directory to the file.\n"
            + "The CSV must have the following format: \n"
            + " Name, Phone, Address, Email, Birthday, Gender, Tags, VolunteerID";

    private static final String MESSAGE_IMPORT_COMPLETED = "Volunteer(s) imported from CSV file "
            + "to your Desktop.";
    private static final String MESSAGE_IMPORT_VOLUNTEER_FAILED = "Volunteer(s) import failed, please try again.";
    private static final String MESSAGE_IMPORT_VOLUNTEER_LACK_INFO = "Volunteer information incomplete.\n"
            + "Please check format and try again";


    private final FileReader csvFile;

    /**
     * @param file to be read as a csv data
     */
    public ImportVolunteerCsvCommand(FileReader file) {
        csvFile = file;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        BufferedReader br = new BufferedReader(csvFile);
        try {
            // this is the first line and it should be the title row
            br.readLine();
        } catch (IOException e) {
            throw new CommandException(MESSAGE_IMPORT_VOLUNTEER_FAILED);
        }

        try {
            String currLine;
            while ((currLine = br.readLine()) != null) {
                String[] arrayLine = currLine.split(",");
                String args = "";
                for (String i : arrayLine) {
                    args += i + " ";
                }
                args = args.trim();
                ArgumentMultimap argMultimap =
                        ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GENDER,
                                PREFIX_BIRTHDAY, PREFIX_PHONE, PREFIX_EMAIL,
                                PREFIX_ADDRESS, PREFIX_TAG);

                if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_GENDER, PREFIX_BIRTHDAY,
                        PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
                        || !argMultimap.getPreamble().isEmpty()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                }

                Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
                Gender gender = ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get());
                Birthday birthday = ParserUtil.parseBirthday(argMultimap.getValue(PREFIX_BIRTHDAY).get());
                Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
                Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
                Address address = ParserUtil.parseAddress(
                        argMultimap.getValue(PREFIX_ADDRESS).get());
                Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

                Volunteer volunteer = new Volunteer(name, gender, birthday, phone, email, address, tagList);

                if (!model.hasVolunteer(volunteer)) {
                    model.addVolunteer(volunteer);
                    model.commitAddressBook();
                }
            }
        } catch (IOException e) {
            throw new CommandException(MESSAGE_IMPORT_VOLUNTEER_FAILED);
        } catch (ParseException e) {
            throw new CommandException(MESSAGE_IMPORT_VOLUNTEER_LACK_INFO);
        }

        return new CommandResult(MESSAGE_IMPORT_COMPLETED);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
