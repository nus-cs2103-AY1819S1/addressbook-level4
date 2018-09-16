package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIESEASE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRUGALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ICNUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Patient;
import seedu.address.model.person.medicalrecord.MedicalRecord;

/**
 * Add MedicalRecord or its components to specified Patient.
 */
public class AddMedicalRecordCommand extends Command {

    public static final String COMMAND_WORD = "addMedicalRecord";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new medical record, or adds data to existing "
            + "medical record of specified patient to the address book. "
            + "Parameters: "
            + PREFIX_BLOODTYPE + "BLOODTYPE "
            + "[" + PREFIX_DIESEASE + "PAST DISEASE]... "
            + "[" + PREFIX_DRUGALLERGY + "DRUG ALLERGY]... "
            + "[" + PREFIX_NOTE + "NOTE]... "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_BLOODTYPE + "A+ "
            + PREFIX_DIESEASE + "Breast Cancer "
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

        Patient patientToAddMedicalRecord = lastShownList.get(index.getZeroBased());
        Patient editedPatient = createNewPatientWithUpdatedMedicalRecord(patientToAddMedicalRecord, toAdd);

        model.updatePerson(patientToAddMedicalRecord, editedPatient);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    private static Patient createNewPatientWithUpdatedMedicalRecord(Patient patientToAddMedicalRecord, MedicalRecord medicalRecordToAdd) {
        return new Patient(patientToAddMedicalRecord, medicalRecordToAdd);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddMedicalRecordCommand // instanceof handles nulls
                && toAdd.equals(((AddMedicalRecordCommand) other).toAdd));
    }
}
