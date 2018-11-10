package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.calendar.GoogleCalendar;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;

/**
 * edits remark of index of person in addressbook
 */
public class RemarkCommand extends Command {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits remark of the person. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_PHONE + "PHONE" + "] "
            + PREFIX_REMARK + "REMARK "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98371931 "
            + PREFIX_REMARK + "Has chronic heart disease ";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Deleted remark of %1$s";
    public static final String MESSAGE_INVALID_PERSON_FAILURE = "This person does not exist in HealthBook";
    public static final String MESSAGE_DUPLICATE_PERSON =
            "There are multiple persons with this name. Please enter person's phone to identify the unique person";


    private final Name name;
    private final Phone phone;
    private final Remark remark;

    /**
     * @param name   of the person in the filtered person list to edit remark
     * @param phone  of the person in the filtered person list to edit remark
     * @param remark to be updated
     */

    public RemarkCommand(Name name, Phone phone, Remark remark) {
        requireAllNonNull(name, remark);

        this.name = name;
        this.phone = phone;
        this.remark = remark;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history, GoogleCalendar googleCalendar)
            throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        Person personToEdit = null;

        for (Person person : lastShownList) {
            if (person.getName().equals(name)) {
                if (phone != null) {
                    if (person.getPhone().equals(phone)) {
                        personToEdit = person; // name and phone is unique
                    }
                } else {
                    if (personToEdit != null) { // duplicate found but no phone provided
                        throw new CommandException(MESSAGE_DUPLICATE_PERSON);
                    } else {
                        personToEdit = person;
                    }
                }
            }
        }

        if (personToEdit == null) {
            throw new CommandException(MESSAGE_INVALID_PERSON_FAILURE);
        }

        if (personToEdit instanceof Patient) {
            Patient patientToEdit = (Patient) personToEdit;
            Patient editedPatient = new Patient(patientToEdit.getName(), patientToEdit.getPhone(),
                    patientToEdit.getEmail(), patientToEdit.getAddress(), remark, patientToEdit.getTags(),
                    patientToEdit.getTelegramId(), patientToEdit.getUpcomingAppointments(),
                    patientToEdit.getPastAppointments(), patientToEdit.getMedicalHistory());
            model.updatePerson(patientToEdit, editedPatient);
        }

        if (personToEdit instanceof Doctor) {
            Doctor doctorToEdit = (Doctor) personToEdit;
            Doctor editedDoctor = new Doctor(doctorToEdit.getName(), doctorToEdit.getPhone(),
                    doctorToEdit.getEmail(), doctorToEdit.getAddress(), remark, doctorToEdit.getTags(),
                    doctorToEdit.getUpcomingAppointments());
            model.updatePerson(doctorToEdit, editedDoctor);
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();

        /**
         * Remark is deleted if input field is empty
         */
        return !(remark.value.isEmpty()) ? new CommandResult(String.format(MESSAGE_ADD_REMARK_SUCCESS,
                personToEdit.getName()))
                : new CommandResult(String.format(MESSAGE_DELETE_REMARK_SUCCESS, personToEdit.getName()));
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) { //if same object
            return true;
        } else if (!(o instanceof RemarkCommand)) {
            return false;
        } else {
            RemarkCommand r = (RemarkCommand) o;
            boolean optionalPhoneIsEquals = (phone != null) ? phone.equals(r.phone) : true;
            return name.equals(r.name)
                    && optionalPhoneIsEquals
                    && remark.equals(r.remark);
        }

    }
}
