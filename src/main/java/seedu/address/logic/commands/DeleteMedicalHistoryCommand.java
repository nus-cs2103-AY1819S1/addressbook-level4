package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONDITION;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * delete medical history for a patient specified by index in healthbook
 */
public class DeleteMedicalHistoryCommand extends Command {
    public static final String COMMAND_WORD = "delete-medical-history";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": delete medical history for a person to the address book. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ALLERGY + "ALLERGIES (separated by comma) "
            + PREFIX_CONDITION + "CONDITIONS (separated by comma) \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ALLERGY + "penicillin,milk "
            + PREFIX_CONDITION + "sub-healthy,hyperglycemia ";

    public static final String MESSAGE_DELETE_MEDICAL_HISTORY_SUCCESS = "Medical history deleted for: %1$s";
    public static final String MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_WRONG_TYPE = "This command is only for patients";
    public static final String MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_NO_ALLERGY = "No such allergy";
    public static final String MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_NO_CONDITION = "No such condition";
    public static final String MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_NO_INFO = "Please provide valid info";

    private final Index index;
    private String allergy;
    private String condition;

    /**
     * Creates an DeleteMedicalHistoryCommand for the specified {@code Person}
     */
    public DeleteMedicalHistoryCommand(Index index, String allergy, String condition) {
        requireNonNull(index);
        this.index = index;
        this.condition = condition;
        this.allergy = allergy;

    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToEdit = lastShownList.get(index.getZeroBased());

        if (!(personToEdit.getTags().contains(new Tag("Patient")))) {
            throw new CommandException(MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_WRONG_TYPE);
        }
        Patient patientToEdit = (Patient) lastShownList.get(index.getZeroBased());
        if (allergy.equals("") && condition.equals("")) {
            throw new CommandException(MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_NO_INFO);
        }
        if (!(allergy.equals(""))) {
            ArrayList<String> allergiesToDelete = new ArrayList<>(Arrays.asList(allergy.split(",")));
            for (int index = 0; index < allergiesToDelete.size(); index++) {
                if (patientToEdit.getMedicalHistory().getAllergies().contains(allergiesToDelete.get(index))) {
                    patientToEdit.getMedicalHistory().getAllergies().remove(allergiesToDelete.get(index));
                } else {
                    throw new CommandException(MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_NO_ALLERGY
                            + patientToEdit.getMedicalHistory().getAllergies().get(0));
                }
            }
        }
        if (!(condition.equals(""))) {
            ArrayList<String> conditionsToDelete = new ArrayList<>(Arrays.asList(condition.split(",")));
            for (int index = 0; index < conditionsToDelete.size(); index++) {
                if (patientToEdit.getMedicalHistory().getConditions().contains(conditionsToDelete.get(index))) {
                    patientToEdit.getMedicalHistory().getConditions().remove(conditionsToDelete.get(index));
                } else {
                    throw new CommandException(MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_NO_CONDITION
                            + patientToEdit.getMedicalHistory().getConditions().get(0));
                }
            }
        }
        Patient editedPatient = new Patient(patientToEdit.getName(), patientToEdit.getPhone(), patientToEdit.getEmail(),
                patientToEdit.getAddress(), patientToEdit.getRemark(), patientToEdit.getTags(),
                patientToEdit.getTelegramId(), patientToEdit.getUpcomingAppointments(),
                patientToEdit.getPastAppointments(), patientToEdit.getMedicalHistory());
        model.updatePerson(patientToEdit, editedPatient);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_MEDICAL_HISTORY_SUCCESS, patientToEdit));
    }
}
