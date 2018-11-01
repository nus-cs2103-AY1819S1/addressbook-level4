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
import seedu.address.testutil.DoctorBuilder;
import seedu.address.testutil.GoogleCalendarStub;

class RegisterDoctorCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final GoogleCalendarStub GOOGLE_CALENDAR_STUB = new GoogleCalendarStub();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullDoctor_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RegisterDoctorCommand(null);
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        RegisterDoctorCommandTest.ModelStubAcceptingDoctorAdded modelStub =
                new RegisterDoctorCommandTest.ModelStubAcceptingDoctorAdded();
        Doctor validDoctor = new DoctorBuilder().build();

        CommandResult commandResult = new RegisterDoctorCommand(validDoctor).execute(modelStub, commandHistory,
                GOOGLE_CALENDAR_STUB);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validDoctor), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validDoctor), modelStub.doctorsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        Doctor validDoctor = new DoctorBuilder().build();
        RegisterDoctorCommand registerDoctorCommand = new RegisterDoctorCommand(validDoctor);
        RegisterDoctorCommandTest.ModelStub modelStub = new RegisterDoctorCommandTest.ModelStubWithDoctor(validDoctor);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);
        registerDoctorCommand.execute(modelStub, commandHistory, GOOGLE_CALENDAR_STUB);
    }

    @Test
    public void equals() {
        Doctor alice = new DoctorBuilder().withName("Alice").build();
        Doctor bob = new DoctorBuilder().withName("Bob").build();
        RegisterDoctorCommand registerDoctorAliceCommand = new RegisterDoctorCommand(alice);
        RegisterDoctorCommand registerDoctorBobCommand = new RegisterDoctorCommand(bob);

        // same object -> returns true
        assertTrue(registerDoctorAliceCommand.equals(registerDoctorAliceCommand));

        // same values -> returns true
        RegisterDoctorCommand registerDoctorAliceCommandCopy = new RegisterDoctorCommand(alice);
        assertTrue(registerDoctorAliceCommand.equals(registerDoctorAliceCommandCopy));

        // different types -> returns false
        assertFalse(registerDoctorAliceCommand.equals(1));

        // null -> returns false
        assertFalse(registerDoctorAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(registerDoctorAliceCommand.equals(registerDoctorBobCommand));
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
     * A Model stub that contains a single person.
     */
    private class ModelStubWithDoctor extends RegisterDoctorCommandTest.ModelStub {
        private final Doctor doctor;

        ModelStubWithDoctor(Doctor doctor) {
            requireNonNull(doctor);
            this.doctor = doctor;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.doctor.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingDoctorAdded extends RegisterDoctorCommandTest.ModelStub {
        final ArrayList<Doctor> doctorsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return doctorsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addDoctor(Doctor doctor) {
            requireNonNull(doctor);
            doctorsAdded.add(doctor);
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

