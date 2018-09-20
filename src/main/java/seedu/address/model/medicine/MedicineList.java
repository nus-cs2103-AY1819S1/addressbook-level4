package seedu.address.model.medicine;

import java.util.ArrayList;
import java.util.List;

//@@author snajef
/**
 * Wrapper class to hold all medication for a given patient.
 *
 * @author Darien Chong
 *
 */
public class MedicineList {
    private List<Medicine> medicineList;

    public MedicineList() {
        medicineList = new ArrayList<Medicine>();
    }

    public MedicineList(List<Medicine> medicineList) {
        this.medicineList = new ArrayList<>(medicineList);
    }

    public MedicineList(MedicineList medicineList) {
        this.medicineList = new ArrayList<>(medicineList.medicineList);
    }

    /**
     * Adds a medication to the list of medications.
     *
     * @param med
     *            The medication to add.
     */
    public void add(Medicine med) {
        medicineList.add(med);
    }

    /**
     * Removes a medication from the list of medications.
     *
     * @param med
     *            The medication to remove.
     */
    public void remove(Medicine med) {
        medicineList.remove(med);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Medicine m : medicineList) {
            sb.append(m.toString()).append("\n");
        }

        return sb.toString().trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o instanceof MedicineList) {
            MedicineList ml = (MedicineList) o;
            return medicineList.containsAll(ml.medicineList);
        }

        return false;
    }
}
