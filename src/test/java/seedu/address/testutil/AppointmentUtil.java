package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONSUMPTION_PER_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICINE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_NAME;

import java.time.format.DateTimeFormatter;

import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.commands.AddPrescriptionCommand;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Prescription;



/**
 * A utility class for Appointment
 */
public class AppointmentUtil {

    /**
     * Returns an add appointment command String for adding {@code appointment}
     */
    public static String getAddAppointmentCommand(Appointment appointment) {
        return AddAppointmentCommand.COMMAND_WORD + " " + getAppointmentDetails(appointment);
    }

    /**
     * Returns an add prescription command String for adding {@code appointment}
     */
    public static String getAddPrescriptionCommand(Prescription prescription) {
        return AddPrescriptionCommand.COMMAND_WORD + " " + getPrescriptionDetails(prescription);
    }

    /**
     * Returns the part of command String for the given {@code appointment}'s details.
     */
    private static String getAppointmentDetails(Appointment appointment) {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        sb.append(PREFIX_PATIENT_NAME).append(appointment.getPatient()).append(" ");
        sb.append(PREFIX_DOCTOR_NAME).append(appointment.getDoctor()).append(" ");
        sb.append(PREFIX_DATE_TIME).append(appointment.getDateTime().format(formatter)).append(" ");

        return sb.toString();
    }

    /**
     * Returns the part of command String for the given {@code prescription}'s details.
     */
    private static String getPrescriptionDetails(Prescription prescription) {
        StringBuilder sb = new StringBuilder();

        sb.append(prescription.getId()).append(" ");
        sb.append(PREFIX_MEDICINE_NAME).append(prescription.getMedicineName().toString()).append(" ");
        sb.append(PREFIX_DOSAGE).append(prescription.getDosage().toString()).append(" ");
        sb.append(PREFIX_CONSUMPTION_PER_DAY).append(prescription.getConsumptionPerDay().toString()).append(" ");

        return sb.toString();
    }

}
