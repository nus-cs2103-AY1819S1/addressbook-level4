package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONDITION;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPatientsAndDoctors.getTypicalAddressBookWithPatientAndDoctor;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;
import seedu.address.model.tag.TagContainsPatientPredicate;
import seedu.address.testutil.PatientBuilder;

public class DeleteMedicalHistoryCommandTest {
    private Model model = new ModelManager(getTypicalAddressBookWithPatientAndDoctor(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_deleteMedicalHistory_success(){
        final TagContainsPatientPredicate predicate = new TagContainsPatientPredicate();
        model.updateFilteredPersonList(predicate);
        Patient firstPatient = (Patient) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPatient =
                new PatientBuilder(firstPatient).withMedicalHistory(VALID_ALLERGY, VALID_CONDITION).build();
    }
}
