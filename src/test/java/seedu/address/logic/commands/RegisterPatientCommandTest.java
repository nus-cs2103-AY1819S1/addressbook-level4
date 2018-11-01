package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;
import seedu.address.testutil.GoogleCalendarStub;
import seedu.address.testutil.PatientBuilder;

class RegisterPatientCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final GoogleCalendarStub GOOGLE_CALENDAR_STUB = new GoogleCalendarStub();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPatient_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RegisterPatientCommand(null);
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        RegisterPatientCommandTest.ModelStubAcceptingPatientAdded modelStub =
                new RegisterPatientCommandTest.ModelStubAcceptingPatientAdded();
        Patient validPatient = new PatientBuilder().build();

        CommandResult commandResult = new RegisterPatientCommand(validPatient).execute(modelStub, commandHistory,
                GOOGLE_CALENDAR_STUB);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPatient), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validPatient), modelStub.patientsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        Patient validPatient = new PatientBuilder().build();
        RegisterPatientCommand registerPatientCommand = new RegisterPatientCommand(validPatient);
        RegisterPatientCommandTest.ModelStub modelStub =
                new RegisterPatientCommandTest.ModelStubWithPatient(validPatient);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);
        registerPatientCommand.execute(modelStub, commandHistory, GOOGLE_CALENDAR_STUB);
    }

    @Test
    public void equals() {
        Patient alice = new PatientBuilder().withName("Alice").build();
        Patient bob = new PatientBuilder().withName("Bob").build();
        RegisterPatientCommand registerPatientAliceCommand = new RegisterPatientCommand(alice);
        RegisterPatientCommand registerPatientBobCommand = new RegisterPatientCommand(bob);

        // same object -> returns true
        assertTrue(registerPatientAliceCommand.equals(registerPatientAliceCommand));

        // same values -> returns true
        RegisterPatientCommand registerPatientAliceCommandCopy = new RegisterPatientCommand(alice);
        assertTrue(registerPatientAliceCommand.equals(registerPatientAliceCommandCopy));

        // different types -> returns false
        assertFalse(registerPatientAliceCommand.equals(1));

        // null -> returns false
        assertFalse(registerPatientAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(registerPatientAliceCommand.equals(registerPatientBobCommand));
    }



    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPatient(Patient patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDoctor(Doctor doctor) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAppointment(Appointment appointment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAppointment(Appointment appointment, Patient patient, Doctor doctor) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void completeAppointment(Appointment appointment, Patient patient, Doctor doctor) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateAppointment(Appointment target, Appointment editedAppointment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Appointment> getFilteredAppointmentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredAppointmentList(Predicate<Appointment> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getAppointmentCounter() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void incrementAppointmentCounter() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single patient.
     */
    private class ModelStubWithPatient extends RegisterPatientCommandTest.ModelStub {
        private final Patient patient;

        ModelStubWithPatient(Patient patient) {
            requireNonNull(patient);
            this.patient = patient;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.patient.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the patient being added.
     */
    private class ModelStubAcceptingPatientAdded extends RegisterPatientCommandTest.ModelStub {
        final ArrayList<Patient> patientsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return patientsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPatient(Patient patient) {
            requireNonNull(patient);
            patientsAdded.add(patient);
        }

        @Override
        public void commitAddressBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}

