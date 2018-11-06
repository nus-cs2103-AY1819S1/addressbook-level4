package seedu.address.model.document;

import seedu.address.model.person.ServedPatient;

/**
 * Represents the referral letter for the served patients. This class is responsible for extracting information that is
 * relevant to the referral letter.
 */
public class ReferralLetter extends Document {
    public static final String FILE_TYPE = "Referral Letter";

    private String referralContent;
    private String noteContent;

    /**
     * Creates a ReferralLetter object for the specified servedPatient.
     * @param servedPatient the patient who has already consulted the doctor.
     */
    public ReferralLetter(ServedPatient servedPatient) {
        setFileType(FILE_TYPE);
        setName(servedPatient.getName());
        setIcNumber(servedPatient.getIcNumber());
        referralContent = servedPatient.getReferralContent();
        noteContent = servedPatient.getNoteContent();
    }

    /**
     * Formats all the relevant information of the referral letter in HTML for the served patient.
     * Other than the main bulk of the text that is for completeness, this information includes the location
     * the patient is being referred to, as well as the reason for the referral.
     */
    public String formatInformation() {
        String referralContent = getReferralContent();
        String noteContent = getNoteContent();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("This is to certify that the above-named patient has been referred to: ")
                .append("<b>" + referralContent.toUpperCase() + "</b>" + "<br><br>")
                .append("Dear Specialist, please assist the above-named patient in the following matter:<br>")
                .append(noteContent + "<br><br>")
                .append("Kindly do accept him under your care. Thank you very much.<br><br>")
                .append("<b>Issuing Doctor:</b> Dr Chester Sng" + "<br>");
        return stringBuilder.toString();
    }

    public String getReferralContent() {
        return referralContent;
    }

    public String getNoteContent() {
        return noteContent;
    }
}
