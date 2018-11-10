package seedu.address.model.patient;

import java.util.ArrayList;

/**
 * Represents a Medical History of a patient in the health book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class MedicalHistory {
    private ArrayList<Allergy> allergies;
    private ArrayList<Condition> conditions;

    public MedicalHistory() {
        allergies = new ArrayList<>();
        conditions = new ArrayList<>();
    }
    public MedicalHistory(ArrayList<Allergy> allergies, ArrayList<Condition> conditions) {
        this.allergies = allergies;
        this.conditions = conditions;
    }
    public MedicalHistory(MedicalHistory medicalHistory) {
        this.allergies = new ArrayList<>(medicalHistory.getAllergies());
        this.conditions = new ArrayList<>(medicalHistory.getConditions());
    }

    public ArrayList<Allergy> getAllergies() {
        return allergies;
    }

    public ArrayList<Condition> getConditions() {
        return conditions;
    }

    public void addAllergy(Allergy allergy) {
        allergies.add(allergy);
    }

    public void addCondition(Condition condition) {
        conditions.add(condition);
    }

    public void addAllergy(String allergy) {
        allergies.add(new Allergy(allergy));
    }

    public void addCondition(String condition) {
        conditions.add(new Condition(condition));
    }

    public void setAllergies(ArrayList<Allergy> allergies) {
        this.allergies = allergies;
    }

    public void setConditions(ArrayList<Condition> conditions) {
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
