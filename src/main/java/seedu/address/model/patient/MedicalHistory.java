package seedu.address.model.patient;

import java.util.ArrayList;

/**
 * Represents a Medical History of a patient in the health book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class MedicalHistory {
    private ArrayList<String> allergies;
    private ArrayList<String> conditions;

    public MedicalHistory() {
        allergies = new ArrayList<>();
        conditions = new ArrayList<>();
    }

    public ArrayList<String> getAllergies() {
        return allergies;
    }

    public ArrayList<String> getConditions() {
        return conditions;
    }

    public void addAllergy(String allergy) {
        allergies.add(allergy);
    }

    public void addCondition(String condition) {
        conditions.add(condition);
    }
}
