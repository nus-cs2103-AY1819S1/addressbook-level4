package seedu.address.model.analytics;

import java.util.Map;

import javafx.collections.ObservableList;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.doctor.Doctor;

//@@author arsalanc-v2

/**
 * Wrapper for all analytics functionality.
 * Makes use of the delegation design pattern.
 */
public class Analytics {

    private PatientStatistics patientStatistics;
    private AppointmentStatistics appointmentStatistics;
    private DoctorStatistics doctorStatistics;
    private MedicineStatistics medicineStatistics;

    public Analytics() {
        patientStatistics = new PatientStatistics();
        appointmentStatistics = new AppointmentStatistics();
        doctorStatistics = new DoctorStatistics();
        medicineStatistics = new MedicineStatistics();
    }

//    public void setPatients(ObservableList<Appointment> appointments) {
//        patientStatistics.setPatients(appointments);
//    }
//
    public void setAppointments(ObservableList<Appointment> appointments) {
        appointmentStatistics.setAppointments(appointments);
    }

    public void setDoctors(ObservableList<Doctor> doctors) {
        doctorStatistics.setDoctors(doctors);
    }

    /**
     * Sets consultations for all types of statistics that require it.
     */
    public void setConsultations(ObservableList<Consultation> consultations) {
        doctorStatistics.setConsultations(consultations);
    }

    /**
     *
     * @param type
     * @return
     */
    public Map getAllStatisticsOfType(StatisticType type) {
        switch (type) {
        case PATIENT:
            return patientStatistics.getAllStatistics();

        case APPOINTMENT:
            return appointmentStatistics.getAllStatistics();

        case DOCTOR:
            return doctorStatistics.getAllStatistics();

        case MEDICINE:
            return medicineStatistics.getAllStatistics();

        default:
            return appointmentStatistics.getAllStatistics();
        }
    }

//    public void setMedicines(ObservableList<Medicine> medicines) {
//        medicineStatistics.setMedicines(medicines);
//    }
//
//    public void getAllMedicineStatistics() {
//        medicineStatistics.getAllStatistics();
//    }
}
