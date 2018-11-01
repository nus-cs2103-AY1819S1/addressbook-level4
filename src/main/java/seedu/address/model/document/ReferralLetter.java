package seedu.address.model.document;

import seedu.address.model.person.ServedPatient;

/**
 * Represents the referral letter for the served patients. This class is responsible for extracting information that is
 * relevant to the referral letter.
 */
public class ReferralLetter extends Document {
    public static final String FILE_TYPE = "Referral Letter";

    /**
     * Creates a ReferralLetter object for the specified servedPatient.
     * @param servedPatient the patient who has already consulted the doctor.
     */
    public ReferralLetter(ServedPatient servedPatient) {
        setFileType(FILE_TYPE);
        setName(servedPatient.getName());
        setIcNumber(servedPatient.getIcNumber());
        setReferralContent(servedPatient.getReferralContent());
        setNoteContent(servedPatient.getNoteContent());
    }
}
