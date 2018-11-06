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
    public MedicalHistory(ArrayList<String> allergies, ArrayList<String> conditions) {
        this.allergies = allergies;
        this.conditions = conditions;
    }
    public MedicalHistory(MedicalHistory medicalHistory) {
        this.allergies = new ArrayList<>(medicalHistory.getAllergies());
        this.conditions = new ArrayList<>(medicalHistory.getConditions());
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

    public void setAllergies(ArrayList<String> allergies) {
        this.allergies = allergies;
    }

    public void setConditions(ArrayList<String> conditions) {
        this.conditions = conditions;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) { //if same object
            return true;
        } else if (!(o instanceof MedicalHistory)) {
            return false;
        } else {
            MedicalHistory r = (MedicalHistory) o;
            return allergies.equals(r.getAllergies()) && conditions.equals(r.getConditions());
        }
    }
}
