package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISEASE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRUGALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddMedicalRecordCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.medicine.MedicineName;
import seedu.address.model.person.medicalrecord.BloodType;
import seedu.address.model.person.medicalrecord.Disease;
import seedu.address.model.person.medicalrecord.DrugAllergy;
import seedu.address.model.person.medicalrecord.MedicalRecord;
import seedu.address.model.person.medicalrecord.Message;
import seedu.address.model.person.medicalrecord.Note;
import seedu.address.model.person.medicalrecord.Quantity;

/**
 * Parses input arguments and creates a new AddMedicalRecordCommand object
 */
public class AddMedicalRecordCommandParser implements Parser<AddMedicalRecordCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddMedicalRecordCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_BLOODTYPE, PREFIX_DRUGALLERGY, PREFIX_DISEASE, PREFIX_NOTE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddMedicalRecordCommand.MESSAGE_USAGE), pe);
        }

        BloodType bloodType = new BloodType("");
        if (argMultimap.getValue(PREFIX_BLOODTYPE).isPresent()) {
            try {
                bloodType = new BloodType(argMultimap.getValue(PREFIX_BLOODTYPE).get());
            } catch (IllegalArgumentException iae) {
                throw new ParseException(BloodType.MESSAGE_BLOODTYPE_CONSTRAINTS);
            }
        }

        List<Disease> diseaseList = new ArrayList<>();
        if (argMultimap.getValue(PREFIX_DISEASE).isPresent()) {
            String diseaseRawString = argMultimap.getValue(PREFIX_DISEASE).get();
            List<Disease> diseasesToAdd = parseDiseaseForAddMedicalCommand(diseaseRawString);
            diseaseList.addAll(diseasesToAdd);
        }

        List<DrugAllergy> drugAllergyList = new ArrayList<>();
        if (argMultimap.getValue(PREFIX_DRUGALLERGY).isPresent()) {
            String drugAllergyRawString = argMultimap.getValue(PREFIX_DRUGALLERGY).get();
            List<DrugAllergy> drugAllergiesToAdd = parseDrugAllergiesForAddMedicalCommand(drugAllergyRawString);
            drugAllergyList.addAll(drugAllergiesToAdd);
        }

        List<Note> noteList = new ArrayList<>();
        if (argMultimap.getValue(PREFIX_NOTE).isPresent()) {
            String noteRawString = argMultimap.getValue(PREFIX_NOTE).get();

            Map<MedicineName, Quantity> test = new HashMap<>();

            Note note = new Note(new Message(noteRawString), test);
            noteList.add(note);
        }

        MedicalRecord medicalRecord = new MedicalRecord(bloodType, drugAllergyList, diseaseList, noteList);

        return new AddMedicalRecordCommand(index, medicalRecord);
    }

    /**
     * Parses list of Disease objects from raw string.
     * @param diseaseRawString
     * @return list of Diseases
     */
    private static List<Disease> parseDiseaseForAddMedicalCommand(String diseaseRawString) {
        List<String> diseasesAsString = new ArrayList<>(Arrays.asList(diseaseRawString.split("\\s*,\\s*")));

        List<Disease> diseases = new ArrayList<>();
        for (String diseaseString: diseasesAsString) {
            Disease disease = new Disease(diseaseString);
            diseases.add(disease);
        }

        return diseases;
    }

    /**
     * Parses list of DrugAllergy objects from raw string.
     * @param drugAllergyRawString
     * @return list of DrugAllergies
     */
    private static List<DrugAllergy> parseDrugAllergiesForAddMedicalCommand(String drugAllergyRawString) {
        List<String> drugAllergiesAsString = new ArrayList<>(Arrays.asList(drugAllergyRawString.split("\\s*,\\s*")));

        List<DrugAllergy> drugAllergies = new ArrayList<>();
        for (String drugAllergyString: drugAllergiesAsString) {
            DrugAllergy drugAllergy = new DrugAllergy(drugAllergyString);
            drugAllergies.add(drugAllergy);
        }

        return drugAllergies;
    }





}
