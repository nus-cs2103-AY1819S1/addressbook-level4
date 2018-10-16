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
     * Creates a referral letter object for the specified servedPatient.
     * @param servedPatient the patient who has already consulted the doctor.
     */
    public ReferralLetter(ServedPatient servedPatient) {
        this.name = servedPatient.getName();
        this.icNumber = servedPatient.getIcNumber();
        this.referralContent = servedPatient.getReferralContent();
    }

    /**
     * Generates the content of the referral letter object generated for the specified servedPatient.
     *
     */
    @Override
    public String generate() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: " + name + "\n");
        sb.append("NRIC: " + icNumber + "\n");
        sb.append(referralContent + "\n");
        return sb.substring(0, sb.length() - 1);
    }
}