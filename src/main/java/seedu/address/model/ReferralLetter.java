package seedu.address.model;

import seedu.address.model.person.IcNumber;
import seedu.address.model.person.Name;
import seedu.address.model.person.ServedPatient;

/**
 * Represents the referral letter for the served patients.
 */
public class ReferralLetter extends Document {
    private final Name name;
    private final IcNumber icNumber;
    private final String referralContent;

    /**
     * Creates a ReferralLetter object for the specified servedPatient.
     * @param servedPatient the patient who has already consulted the doctor.
     */
    public ReferralLetter(ServedPatient servedPatient) {
        this.name = servedPatient.getName();
        this.icNumber = servedPatient.getIcNumber();
        this.referralContent = servedPatient.getReferralContent();
    }

    /**
     * Generates the content of the MedicalCertificate object generated for the specified servedPatient.
     *
     */
    @Override
    public String generate() {
        //dissect contents of note to extract medicines dispensed
        StringBuilder sb = new StringBuilder();
        sb.append(super.generateHeaders());
        sb.append(super.tabFormat("Name: " + name + "\n"));
        sb.append(super.tabFormat("NRIC: " + icNumber + "\n"));
        return sb.toString();
    }
}