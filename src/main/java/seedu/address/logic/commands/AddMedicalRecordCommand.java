package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISEASE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRUGALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ShowPatientListEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Patient;
import seedu.address.model.person.exceptions.DifferentBloodTypeException;
import seedu.address.model.person.medicalrecord.MedicalRecord;

/**
 * Add MedicalRecord or its components to specified Patient.
 */
public class AddMedicalRecordCommand extends Command {

    public static final String COMMAND_WORD = "addmedicalrecord";
    public static final String COMMAND_ALIAS = "amr";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates a new medical record, or adds data to existing "
            + "medical record of specified patient to the address book. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_BLOODTYPE + "BLOODTYPE] "
            + "[" + PREFIX_DISEASE + "PAST DISEASE]... "
            + "[" + PREFIX_DRUGALLERGY + "DRUG ALLERGY]... "
            + "[" + PREFIX_NOTE + "NOTE]... "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_BLOODTYPE + "A+ "
            + PREFIX_DISEASE + "Breast Cancer "
            + PREFIX_DRUGALLERGY + "Everything "
            + PREFIX_NOTE + "This guy is allergic to every damn thing! ";

    public static final String MESSAGE_SUCCESS = "New medical record added: %1$s";

    private final Index index;
    private final MedicalRecord toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Patient}
     */
    public AddMedicalRecordCommand(Index index, MedicalRecord medicalRecord) {
        requireNonNull(index);
        requireNonNull(medicalRecord);

        this.index = index;
        this.toAdd = medicalRecord;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Patient> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        try {
            Patient patientToAddMedicalRecord = lastShownList.get(index.getZeroBased());
            if (patientToAddMedicalRecord.isInQueue()) {
                throw new CommandException(Messages.MESSAGE_PERSON_IN_QUEUE);
            }
            Patient editedPatient = createNewPatientWithUpdatedMedicalRecord(patientToAddMedicalRecord, toAdd);

            model.updatePerson(patientToAddMedicalRecord, editedPatient);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            model.commitAddressBook();

            EventsCenter.getInstance().post(new PersonPanelSelectionChangedEvent(editedPatient));
            EventsCenter.getInstance().post(new ShowPatientListEvent());

        } catch (DifferentBloodTypeException dbte) {
            throw new CommandException(Messages.MESSAGE_DIFFERENT_BLOOD_TYPE);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    private static Patient createNewPatientWithUpdatedMedicalRecord(
            Patient patientToAddMedicalRecord, MedicalRecord medicalRecordToAdd) throws DifferentBloodTypeException {
        return new Patient(patientToAddMedicalRecord, medicalRecordToAdd);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddMedicalRecordCommand // instanceof handles nulls
                && toAdd.equals(((AddMedicalRecordCommand) other).toAdd)
                && index.equals(((AddMedicalRecordCommand) other).index));
    }
}
