package seedu.address.model.document;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seedu.address.model.person.ServedPatient;

/**
 * Represents the medical certificate for the served patients. This class is responsible for extracting information that
 * is relevant to the medical certificate.
 */
public class MedicalCertificate extends Document {
    public static final String FILE_TYPE = "Medical Certificate";

    private int mcDays;

    /**
     * Creates a MedicalCertificate object for the specified servedPatient.
     * @param servedPatient the patient who has already consulted the doctor.
     */
    public MedicalCertificate(ServedPatient servedPatient) {
        setFileType(FILE_TYPE);
        setName(servedPatient.getName());
        setIcNumber(servedPatient.getIcNumber());
        mcDays = Integer.parseInt(servedPatient.getMcContent());
    }

    /**
     * Formats all the relevant information of the MC in HTML for the served patient. Other than the main bulk
     * of the text that is for completeness, this information includes the number of days of medical leave
     * granted and the period which the medical leave will last.
     */
    public String formatInformation() {
        int numMcDays = getMcDays();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("This is to certify that the above-named patient is unfit for duty for a period of ")
                .append("<b>" + numMcDays + "</b>")
                .append(" <b>day(s)</b>, from ")
                .append("<b>" + LocalDate.now().format(formatter) + "</b>")
                .append(" <b>to</b> ")
                .append("<b>" + LocalDate.now().plusDays(numMcDays - 1).format(formatter) + "</b>")
                .append(" <b>inclusive.</b><br><br>")
                .append("This certificate is not valid for absence from court attendance.<br><br>")
                .append("<b>Issuing Doctor:</b> Dr Chester Sng" + "<br>");
        return stringBuilder.toString();
    }

    public int getMcDays() {
        return mcDays;
    }
}
