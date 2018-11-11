package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.calendar.GoogleCalendar;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Allergy;
import seedu.address.model.patient.Condition;
import seedu.address.model.patient.MedicalHistory;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * delete medical history for a patient specified by index in healthbook
 */
public class DeleteMedicalHistoryCommand extends Command {
    public static final String COMMAND_WORD = "delete-medical-history";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes medical history for a patient in the health book. \n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_ALLERGY + "ALLERGIES (separated by comma)] "
            + "[" + PREFIX_CONDITION + "CONDITIONS (separated by comma)] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_ALLERGY + "penicillin,milk "
            + PREFIX_CONDITION + "sub-healthy,hyperglycemia ";

    public static final String MESSAGE_DELETE_MEDICAL_HISTORY_SUCCESS = "Medical history deleted for: %1$s";
    public static final String MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_WRONG_TYPE = "This command is only for patients";
    public static final String MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_NO_ALLERGY = "Non Exist Allergy: ";
    public static final String MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_NO_CONDITION = "Non Exist Condition: ";
    public static final String MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_NO_INFO = "Please provide valid info";
    public static final String MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_NO_MATCH_NAME =
            " does not exist in the healthbook";
    public static final String MESSAGE_DUPLICATE_PATIENT =
            "There is multiple patients with this name. Please enter patients's number to identify the unique patient";

    private final Name name;
    private final Phone phone;
    private final ArrayList<Allergy> allergies;
    private final ArrayList<Condition> conditions;

    /**
     * Creates an DeleteMedicalHistoryCommand for the specified {@code Person}
     */
    public DeleteMedicalHistoryCommand(Name name, Phone phone,
                                       ArrayList<Allergy> allergies, ArrayList<Condition> conditions) {
        requireNonNull(name);
        this.name = name;
        this.conditions = conditions;
        this.allergies = allergies;
        this.phone = phone;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, GoogleCalendar googleCalendar)
            throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Person personToEdit = null;
        boolean personExist = false;
        for (Person person : lastShownList) {
            if (person.getName().equals(name)) {
                if (phone != null) {
                    if (person.getPhone().equals(phone)) {
                        personToEdit = person;
                        personExist = true;
                    }
                } else {
                    if (personToEdit != null && personToEdit.getName().equals(name)) {
                        throw new CommandException(MESSAGE_DUPLICATE_PATIENT);
                    } else {
                        personToEdit = person;
                        personExist = true;
                    }
                }
            }
        }
        if (!personExist) {
            throw new CommandException(MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_NO_MATCH_NAME);
        }

        if (!(personToEdit.getTags().contains(new Tag("Patient")))) {
            throw new CommandException(MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_WRONG_TYPE);
        }
        Patient patientToEdit = (Patient) personToEdit;
        MedicalHistory editedMedicalHistory = new MedicalHistory(patientToEdit.getMedicalHistory());
        if (allergies.size() == 0 && conditions.size() == 0) {
            throw new CommandException(MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_NO_INFO);
        }
        if (allergies.size() != 0) {
            for (int index = 0; index < allergies.size(); index++) {
                if (editedMedicalHistory.getAllergies().contains(allergies.get(index))) {
                    editedMedicalHistory.getAllergies().remove(allergies.get(index));
                } else {
                    throw new CommandException(MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_NO_ALLERGY
                            + allergies.get(index));
                }
            }
        }
        if (conditions.size() != 0) {
            for (int index = 0; index < conditions.size(); index++) {
                if (editedMedicalHistory.getConditions().contains(conditions.get(index))) {
                    editedMedicalHistory.getConditions().remove(conditions.get(index));
                } else {
                    throw new CommandException(MESSAGE_INVALID_DELETE_MEDICAL_HISTORY_NO_CONDITION
                            + conditions.get(index));
                }
            }
        }
        Patient editedPatient = new Patient(patientToEdit.getName(), patientToEdit.getPhone(), patientToEdit.getEmail(),
                patientToEdit.getAddress(), patientToEdit.getRemark(), patientToEdit.getTags(),
                patientToEdit.getTelegramId(), patientToEdit.getUpcomingAppointments(),
                patientToEdit.getPastAppointments(), editedMedicalHistory);
        model.updatePerson(patientToEdit, editedPatient);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();

        EventsCenter.getInstance().post(new PersonPanelSelectionChangedEvent(editedPatient));
        return new CommandResult(String.format(MESSAGE_DELETE_MEDICAL_HISTORY_SUCCESS, patientToEdit));
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) { //if same object
            return true;
        } else if (!(o instanceof DeleteMedicalHistoryCommand)) {
            return false;
        } else {
            DeleteMedicalHistoryCommand r = (DeleteMedicalHistoryCommand) o;
            if (!name.equals(r.name)) {
                return false;
            }
            if (allergies.size() != r.allergies.size() || conditions.size() != r.conditions.size()) {
                return false;
            }
            for (int i = 0; i < allergies.size(); i++) {
                if (!(allergies.get(i).equals(r.allergies.get(i)))) {
                    return false;
                }
            }
            for (int i = 0; i < conditions.size(); i++) {
                if (!(conditions.get(i).equals(r.conditions.get(i)))) {
                    return false;
                }
            }

            return true;
        }

    }
}
