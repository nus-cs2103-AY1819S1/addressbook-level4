package seedu.address.model;

import seedu.address.model.person.IcNumber;
import seedu.address.model.person.Name;
import seedu.address.model.person.ServedPatient;

/**
 * Represents the medical certificate for the served patients.
 */
public class MedicalCertificate extends Document {
    private final Name name;
    private final IcNumber icNumber;
    private final String mcContent;

    /**
     * Creates a MedicalCertificate object for the specified servedPatient.
     * @param servedPatient the patient who has already consulted the doctor.
     */
    public MedicalCertificate(ServedPatient servedPatient) {
        this.name = servedPatient.getName();
        this.icNumber = servedPatient.getIcNumber();
        this.mcContent = servedPatient.getMcContent();
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
