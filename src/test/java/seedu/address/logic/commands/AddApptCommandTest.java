package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.appointment.Appointment;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

/**
 * Test driver class for AddApptCommand
 */
public class AddApptCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private String validNric;
    private String type;
    private String procedure;
    private String dateTime;
    private String duplicateDateTime;
    private String doctor;
    private Appointment appt;
    private Person patient;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        validNric = "S8888888A";
        patient = new Person(new Nric(validNric), new Name("AddAppt Test"), new Phone("91234567"),
                new Email("addappttest@gmail.com"), new Address("12 Addappt Ave, #01-01"), new HashSet<Tag>());
        type = "SRG";
        procedure = "Heart Bypass";
        dateTime = "12-12-2022 10:30";
        duplicateDateTime = "12-12-2022 10:30";
        doctor = "Dr. Pepper";
        appt = new Appointment(type, procedure, dateTime, doctor);
    }

    @Test(expected = NullPointerException.class)
    public void constructor_nullNric_throwsNullPointerEception() {
        Command command = new AddApptCommand(null, appt);
    }

    @Test
    public void execute_addapptToExistingPatient_addapptSuccessful() throws CommandException {
        ModelStubAcceptingAddappt modelStub = new ModelStubAcceptingAddappt(patient);
        CommandResult commandResult = new AddApptCommand(patient.getNric(), appt).execute(modelStub,
                commandHistory);
        assertEquals(String.format(AddApptCommand.MESSAGE_SUCCESS, patient.getNric()), commandResult.feedbackToUser);
    }

    @Test
    public void execute_addapptToNonexistentPatient_throwsCommandException() throws Exception {
        Person patientNotInModel = new PersonBuilder().build();
        AddApptCommand addApptCommand = new AddApptCommand(patientNotInModel.getNric(), appt);
        CommandTestUtil.ModelStub modelStub = new ModelStubAcceptingAddappt(patient);
        thrown.expect(CommandException.class);
        thrown.expectMessage(CommandUtil.MESSAGE_NO_SUCH_PATIENT);
        addApptCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_addapptWithDuplicateDateTime_throwsCommandException() throws Exception {
        appt = new Appointment(type, procedure, dateTime, doctor);
        AddApptCommand addApptCommand = new AddApptCommand(patient.getNric(), appt);
        appt = new Appointment(type, procedure, duplicateDateTime, doctor);
        AddApptCommand addDuplicateDateTime = new AddApptCommand(patient.getNric(), appt);
        CommandTestUtil.ModelStub modelStub = new ModelStubAcceptingAddappt(patient);
        thrown.expect(CommandException.class);
        thrown.expectMessage(AddApptCommand.MESSAGE_DUPLICATE_DATE_TIME);
        addApptCommand.execute(modelStub, commandHistory);
        addDuplicateDateTime.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withNric("S2345678B").withName("Bob").build();
        Appointment diffAppt = new Appointment(type, "Heat Bypass", dateTime, doctor);

        AddApptCommand addApptAliceApptCommand = new AddApptCommand(alice.getNric(), appt);
        AddApptCommand addApptBobApptCommand = new AddApptCommand(bob.getNric(), appt);
        AddApptCommand addApptAliceDiffApptCommand = new AddApptCommand(alice.getNric(), diffAppt);

        // same object -> returns true
        assertTrue(addApptAliceApptCommand.equals(addApptAliceApptCommand));

        // same values -> returns true
        AddApptCommand addapptAliceApptCommandCopy = new AddApptCommand(alice.getNric(), appt);
        assertTrue(addapptAliceApptCommandCopy.equals(addapptAliceApptCommandCopy));

        // different types -> returns false
        assertFalse(addApptAliceApptCommand.equals(1));

        // null -> returns false
        assertFalse(addApptAliceApptCommand.equals(null));

        // different person, same prescription -> returns false
        assertFalse(addApptAliceApptCommand.equals(addApptBobApptCommand));

        // same patient, different prescription -> returns false
        assertFalse(addApptAliceApptCommand.equals(addApptAliceDiffApptCommand));
    }

    /**
     * A Model stub that always accepts addappt commands for a single person.
     */
    private class ModelStubAcceptingAddappt extends CommandTestUtil.ModelStub {
        private Person patient;

        public ModelStubAcceptingAddappt(Person patient) {
            this.patient = patient;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.patient.isSamePerson(person);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            ObservableList<Person> patients = FXCollections.observableArrayList();
            patients.add(patient);

            FilteredList<Person> filteredPatients = new FilteredList<>(patients);
            return FXCollections.unmodifiableObservableList(filteredPatients);
        }

        @Override
        public ObservableList<Person> getFilteredCheckedOutPersonList() {
            ObservableList<Person> checkedOutPatients = FXCollections.observableArrayList();

            FilteredList<Person> filteredCheckedOutPatients = new FilteredList<>(checkedOutPatients);
            return FXCollections.unmodifiableObservableList(filteredCheckedOutPatients);
        }

        @Override
        public void updatePerson(Person personToUpdate, Person updatedPerson) {
            requireAllNonNull(personToUpdate, updatedPerson);
            assertTrue(personToUpdate.isSamePerson(updatedPerson));
            patient = updatedPerson;
        }
    }
}
