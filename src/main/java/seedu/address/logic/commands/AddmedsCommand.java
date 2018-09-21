package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOSES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOSE_UNIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRUGNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.medicine.Prescription;
import seedu.address.model.medicine.PrescriptionList;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Adds a person to the address book.
 */
public class AddmedsCommand extends Command {
    public static final String COMMAND_WORD = "addmeds";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds medication for a patient. " + "Parameters: "
            + PREFIX_NRIC + "NRIC, followed by drug details as follows: " + PREFIX_DRUGNAME + "DRUG_NAME "
            + PREFIX_QUANTITY + "QUANTITY_PER_DOSE " + PREFIX_DOSE_UNIT + "DOSAGE_UNIT " + PREFIX_DOSES
            + "DOSES_PER_DAY " + PREFIX_DURATION + "DURATION_IN_DAYS\n" + "Example: " + COMMAND_WORD + " " + PREFIX_NRIC
            + "S1234567A\n" + PREFIX_DRUGNAME + "Paracetamol " + PREFIX_QUANTITY + "2 " + PREFIX_DOSE_UNIT + "tablets "
            + PREFIX_DOSES + "4 " + PREFIX_DURATION + "14";

    public static final String MESSAGE_SUCCESS = "Medication added for patient: %1$s";
    public static final String MESSAGE_NO_SUCH_PATIENT = "No such patient exists.";

    private final Prescription med;
    private final Nric patientNric;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddmedsCommand(Nric patientNric, Prescription med) {
        this.patientNric = patientNric;
        this.med = med;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        ObservableList<Person> filteredByNric = model.getFilteredPersonList()
                .filtered(p -> p.getNric().equals(patientNric));

        if (filteredByNric.size() < 1) {
            throw new CommandException(MESSAGE_NO_SUCH_PATIENT);
        }

        Person patientToUpdate = filteredByNric.get(0);
        Person updatedPatient = addMedicineForPerson(patientToUpdate, med);

        model.updatePerson(patientToUpdate, updatedPatient);

        return new CommandResult(String.format(MESSAGE_SUCCESS, patientNric));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddmedsCommand // instanceof handles nulls
                        && patientNric.equals(((AddmedsCommand) other).patientNric)
                        && med.equals(((AddmedsCommand) other).med));
    }

    /**
     * Updates a patient with new medicine by recreating the person and the medicine list.
     *
     * @param personToEdit The patient to update.
     * @param m The medicine to update with.
     * @return An updated patient with the appropriate medicine added.
     */
    private static Person addMedicineForPerson(Person personToEdit, Prescription m) {
        assert personToEdit != null;

        PrescriptionList updatedMedicineList = new PrescriptionList(personToEdit.getMedicineList());
        updatedMedicineList.add(m);

        Nric nric = personToEdit.getNric();
        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Set<Tag> tags = personToEdit.getTags();

        return new Person(nric, name, phone, email, address, tags, updatedMedicineList);
    }
}
