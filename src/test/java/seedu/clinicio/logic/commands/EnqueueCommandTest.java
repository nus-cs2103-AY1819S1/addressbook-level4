package seedu.clinicio.logic.commands;
//@@author iamjackslayer
import static org.junit.Assert.assertEquals;
import static seedu.clinicio.testutil.TypicalPersons.ALEX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.clinicio.commons.core.index.Index;
import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.model.ClinicIo;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.ReadOnlyClinicIo;
import seedu.clinicio.model.analytics.StatisticType;
import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.consultation.Consultation;
import seedu.clinicio.model.medicine.Medicine;
import seedu.clinicio.model.medicine.MedicineQuantity;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.model.staff.Staff;

public class EnqueueCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new EnqueueCommand(null);
    }

    @Test
    public void execute_indexAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingIndexAdded modelStub = new ModelStubAcceptingIndexAdded();
        Index index = Index.fromOneBased(1);

        CommandResult commandResult = new EnqueueCommand(index).execute(modelStub, commandHistory);

        assertEquals(Arrays.asList(ALEX), modelStub.personsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    /**
     * A Model stub that always accept the index of the patient being enqueued.
     */
    private class ModelStubAcceptingIndexAdded extends EnqueueCommandTest.ModelStub {
        final ArrayList<Patient> personsAdded = new ArrayList<>();

        @Override
        public void commitClinicIo() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyClinicIo getClinicIo() {
            return new ClinicIo();
        }

        @Override
        public void enqueue(Patient patient) {
            personsAdded.add(patient);
            patient.setIsQueuing();
        }

        @Override
        public void updateQueue(Predicate<Patient> predicate) {
            return;
        }

        @Override
        public ObservableList<Patient> getFilteredPatientList() {
            ObservableList<Patient> internalList = FXCollections.observableArrayList();
            internalList.add(ALEX);
            FilteredList<Patient> list = new FilteredList<>(internalList);

            return FXCollections.unmodifiableObservableList(list);
        }
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

        @Override
        public void switchTab(int index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateQueue(Predicate<Patient> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPatient(Patient patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addStaff(Staff staff) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean checkStaffCredentials(Staff staff) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyClinicIo newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyClinicIo getClinicIo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPatient(Patient patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasStaff(Staff staff) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePatient(Patient target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Patient> getFilteredPatientList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Patient> getAllPatientsInQueue() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Staff> getFilteredStaffList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPatientList(Predicate<Patient> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredStaffList(Predicate<Staff> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoClinicIo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoClinicIo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoClinicIo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoClinicIo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitClinicIo() {
            throw new AssertionError("This method should not be called.");
        }

        //@@author iamjackslayer
        @Override
        public void enqueue(Patient patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void dequeue(Patient patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void enqueueIntoMainQueue(Person patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void enqueueIntoPreferenceQueue(Person patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPatientInMainQueue() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPatientInPreferenceQueue() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPatientInPatientQueue() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasConsultation(Consultation consultation) {
            return false;
        }

        @Override
        public void deleteConsultation(Consultation target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addConsultation(Consultation consultation) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateConsultation(Consultation target, Consultation editedConsultation) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Consultation> getFilteredConsultationList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredConsultationList(Predicate<Consultation> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String exportPatients() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String exportPatientsAppointments() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String exportPatientsConsultations() {
            throw new AssertionError("This method should not be called.");
        }

        //@@author gingivitiss
        @Override
        public boolean hasAppointment(Appointment appt) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAppointmentClash(Appointment appt) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAppointment(Appointment appt) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void cancelAppointment(Appointment appt) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAppointment(Appointment appt) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateAppointment(Appointment appt, Appointment editedAppt) {
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
        public void requestAnalyticsDisplay(StatisticType statisticType) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasMedicine(Medicine medicine) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteMedicine(Medicine medicine) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMedicine(Medicine medicine) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateMedicineQuantity(Medicine medicine, MedicineQuantity newQuantity) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Medicine> getFilteredMedicineList() {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public void updateFilteredMedicineList(Predicate<Medicine> predicate) {
            throw new AssertionError("This method should not be called.");
        }

    }
}
