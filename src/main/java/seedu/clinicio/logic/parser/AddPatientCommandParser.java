package seedu.clinicio.logic.parser;

import static seedu.clinicio.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_IC;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICAL_PROBLEM;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICATION;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_PREFERRED_DOCTOR;

import java.util.Set;
import java.util.stream.Stream;

import seedu.clinicio.logic.commands.AddPatientCommand;
import seedu.clinicio.logic.parser.exceptions.ParseException;

import seedu.clinicio.model.patient.Allergy;
import seedu.clinicio.model.patient.MedicalProblem;
import seedu.clinicio.model.patient.Medication;
import seedu.clinicio.model.patient.Nric;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.person.Address;
import seedu.clinicio.model.person.Email;
import seedu.clinicio.model.person.Name;
import seedu.clinicio.model.person.Phone;
import seedu.clinicio.model.staff.Staff;

/**
 * Parses input arguments and creates a new AddPatientCommand object
 */
public class AddPatientCommandParser implements Parser<AddPatientCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPatientCommand
     * and returns an AddPatientCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddPatientCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_IC, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_MEDICAL_PROBLEM, PREFIX_MEDICATION, PREFIX_ALLERGY, PREFIX_PREFERRED_DOCTOR);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_IC, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPatientCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Nric nric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_IC).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<MedicalProblem> medicalProblems = ParserUtil.parseMedicalProblems(
                argMultimap.getAllValues(PREFIX_MEDICAL_PROBLEM));
        Set<Medication> medications = ParserUtil.parseMedications(
                argMultimap.getAllValues(PREFIX_MEDICATION));
        Set<Allergy> allergies = ParserUtil.parseAllergies(argMultimap.getAllValues(PREFIX_ALLERGY));
        Staff preferredDoctor = ParserUtil.parsePreferredDoctor(
                argMultimap.getValue(PREFIX_PREFERRED_DOCTOR).orElse(""));
        Patient patient = new Patient(name, nric, phone, email,
                address, medicalProblems, medications, allergies,
                preferredDoctor);

        return new AddPatientCommand(patient);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
