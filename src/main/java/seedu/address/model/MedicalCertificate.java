package seedu.address.model;

import seedu.address.model.person.IcNumber;
import seedu.address.model.person.Name;
import seedu.address.model.person.ServedPatient;

/**
 * Represents the medical certificate (MC) for the served patients.
 */
public class MedicalCertificate extends Document {
    private final Name name;
    private final IcNumber icNumber;
    private final String mcContent;

    /**
     * Creates an MC object for the specified servedPatient.
     * @param servedPatient the patient who has already consulted the doctor.
     */
    public MedicalCertificate(ServedPatient servedPatient) {
        this.name = servedPatient.getName();
        this.icNumber = servedPatient.getIcNumber();
        this.mcContent = servedPatient.getMcContent();
    }

    /**
     * Generates the content of the MC object generated for the specified servedPatient.
     *
     */
    @Override
    public String generate() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: " + name + "\n");
        sb.append("NRIC: " + icNumber + "\n");
        sb.append(mcContent + "\n");
        return sb.substring(0, sb.length() - 1);
    }
}