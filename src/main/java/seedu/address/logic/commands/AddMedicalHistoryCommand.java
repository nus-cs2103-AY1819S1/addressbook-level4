package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.calendar.GoogleCalendar;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.MedicalHistory;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * add medical history for a patient specified by index in healthbook
 */
public class AddMedicalHistoryCommand extends Command {
    public static final String COMMAND_WORD = "add-medical-history";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds medical history for a person to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_ALLERGY + "ALLERGIES (separated by comma) "
            + PREFIX_CONDITION + "CONDITIONS (separated by comma) \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_ALLERGY + "penicillin, milk "
            + PREFIX_CONDITION + "sub-healthy, hyperglycemia ";

    public static final String MESSAGE_ADD_MEDICAL_HISTORY_SUCCESS = "Medical history added for: %1$s";
    public static final String MESSAGE_INVALID_ADD_MEDICAL_HISTORY = "This command is only for patients";
    public static final String MESSAGE_INVALID_ADD_MEDICAL_HISTORY_DUPLICATE = ": already existed";
    public static final String MESSAGE_INVALID_ADD_MEDICAL_HISTORY_NO_INFO = "Please provide valid info";
    public static final String MESSAGE_INVALID_ADD_MEDICAL_HISTORY_NO_MATCH_NAME = " does not exist in the healthbook";

    private final Name name;
    private String allergy;
    private String condition;
    private MedicalHistory medicalHistory = new MedicalHistory();



    /**
     * Creates an AddMedicalHistoryCommand to add the specified {@code Person}
     */
    public AddMedicalHistoryCommand(Name name, String allergy, String condition) {
        requireNonNull(name);
        this.name = name;
        this.condition = condition;
        this.allergy = allergy;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history, GoogleCalendar googleCalendar)
            throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        List<Person> lastShownList = model.getFilteredPersonList();

        Person personToEdit = null;
        boolean personExist = false;
        for (Person person : lastShownList) {
            if (person.getName().equals(name)) {
                personToEdit = person;
                personExist = true;
                break;
            }
        }
        if (!personExist) {
            throw new CommandException(MESSAGE_INVALID_ADD_MEDICAL_HISTORY_NO_MATCH_NAME);
        }
        if (!(personToEdit.getTags().contains(new Tag("Patient")))) {
            throw new CommandException(MESSAGE_INVALID_ADD_MEDICAL_HISTORY);
        }
        Patient patientToEdit = (Patient) personToEdit;
        if (allergy.equals("") && condition.equals("")) {
            throw new CommandException(MESSAGE_INVALID_ADD_MEDICAL_HISTORY_NO_INFO);
        }

        ArrayList<String> newAllergies = new ArrayList<>();
        ArrayList<String> newConditions = new ArrayList<>();
        if (!(allergy.equals(""))) {
            newAllergies = new ArrayList<>(Arrays.asList(allergy.split(",")));
            if (!(patientToEdit.getMedicalHistory().getAllergies().equals(null))) {
                for (int i = 0; i < newAllergies.size(); i++) {
                    if (patientToEdit.getMedicalHistory().getAllergies().contains(newAllergies.get(i))) {
                        throw new CommandException(newAllergies.get(i) + MESSAGE_INVALID_ADD_MEDICAL_HISTORY_DUPLICATE);
                    }
                }
            }
        }
        if (!(condition.equals(""))) {
            newConditions = new ArrayList<>(Arrays.asList(condition.split(",")));
            if (!(patientToEdit.getMedicalHistory().getConditions().equals(null))) {
                for (int i = 0; i < newConditions.size(); i++) {
                    if (patientToEdit.getMedicalHistory().getConditions().contains(newConditions.get(i))) {
                        throw new CommandException(newConditions.get(i)
                                + MESSAGE_INVALID_ADD_MEDICAL_HISTORY_DUPLICATE);
                    }
                }
            }
        }


        ArrayList<String> allergies = new ArrayList<String>();
        ArrayList<String> conditions = new ArrayList<String>();


        if (!(patientToEdit.getMedicalHistory().getAllergies().equals(null))) {
            allergies.addAll(patientToEdit.getMedicalHistory().getAllergies());
        }
        if (!(allergy.equals(""))) {
            allergies.addAll(newAllergies);
        }
        if (!(patientToEdit.getMedicalHistory().getAllergies().equals(null))) {
            conditions.addAll(patientToEdit.getMedicalHistory().getConditions());
        }
        if (!(condition.equals(""))) {
            conditions.addAll(newConditions);
        }
        medicalHistory.setAllergies(allergies);
        medicalHistory.setConditions(conditions);

        Patient editedPatient = new Patient(patientToEdit.getName(), patientToEdit.getPhone(), patientToEdit.getEmail(),
                patientToEdit.getAddress(), patientToEdit.getRemark(), patientToEdit.getTags(),
                patientToEdit.getTelegramId(), patientToEdit.getUpcomingAppointments(),
                patientToEdit.getPastAppointments());
        editedPatient.setMedicalHistory(medicalHistory);
        model.updatePerson(patientToEdit, editedPatient);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();

        EventsCenter.getInstance().post(new PersonPanelSelectionChangedEvent(editedPatient));
        return new CommandResult(String.format(MESSAGE_ADD_MEDICAL_HISTORY_SUCCESS, patientToEdit));
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) { //if same object
            return true;
        } else if (!(o instanceof AddMedicalHistoryCommand)) {
            return false;
        } else {
            AddMedicalHistoryCommand r = (AddMedicalHistoryCommand) o;
            return name.equals(r.name) && allergy.equals(r.allergy) && condition.equals(r.condition);
        }

    }
}
