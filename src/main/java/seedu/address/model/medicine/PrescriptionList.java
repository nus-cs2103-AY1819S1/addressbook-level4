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
public class PrescriptionList {
    private List<Prescription> medicineList;

    public PrescriptionList() {
        medicineList = new ArrayList<Prescription>();
    }

    public PrescriptionList(List<Prescription> medicineList) {
        this.medicineList = new ArrayList<>(medicineList);
    }

    public PrescriptionList(PrescriptionList prescriptionList) {
        this.medicineList = new ArrayList<>(prescriptionList.medicineList);
    }

    /**
     * Adds a medication to the list of medications.
     *
     * @param med
     *            The medication to add.
     */
    public void add(Prescription med) {
        medicineList.add(med);
    }

    /**
     * Removes a medication from the list of medications.
     *
     * @param med
     *            The medication to remove.
     */
    public void remove(Prescription med) {
        medicineList.remove(med);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Prescription m : medicineList) {
            sb.append(m.toString()).append("\n");
        }

        return sb.toString().trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o instanceof PrescriptionList) {
            PrescriptionList ml = (PrescriptionList) o;
            return medicineList.containsAll(ml.medicineList);
        }

        return false;
    }
}
