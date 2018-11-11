package seedu.address.model.medicine;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.medicine.exceptions.DuplicateMedicineException;
import seedu.address.model.medicine.exceptions.InsufficientStockException;
import seedu.address.model.medicine.exceptions.MedicineNotFoundException;
import seedu.address.model.medicine.exceptions.SerialNumberInUseException;

/**
 * A list of medicines that enforces uniqueness between its elements and does not allow nulls.
 * A medicine is considered unique by comparing using {@code Medicine#isSameMedicine(Medicine)}.
 * As such, adding and updating of medicines uses Medicine#isSameMedicine(Medicine) for equality so as to ensure
 * that the medicine being added or updated is unique in terms of identity in the UniqueMedicineList.
 * However, the removal of a medicine uses Medicine#equals(Object) so as to ensure that the medicine
 * with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Medicine#isSameMedicine(Medicine)
 */
public class UniqueMedicineList implements Iterable<Medicine> {

    private final ObservableList<Medicine> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent medicine as the given argument.
     */
    public boolean contains(Medicine toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameMedicine);
    }

    /**
     * Returns true if the list contains an equivalent medicine as the given medicine name.
     */
    public boolean containsMedicineName(Medicine toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::hasSameMedicineName);
    }

    /**
     * Returns true if the list contains a serial number as the medicine given argument.
     */
    public boolean containsSerialNumber(Medicine toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::hasSameSerialNumber);
    }

    /**
     * Adds a medicine to the list.
     * The medicine must not already exist in the list.
     * Serial number must not be in use.
     */
    public void add(Medicine toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateMedicineException();
        }
        if (contains(toAdd)) {
            throw new SerialNumberInUseException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the medicine {@code target} in the list with {@code editedMedicine}.
     * {@code target} must exist in the list.
     * The medicine identity of {@code editedMedicine} must not be the same as another existing medicine in the list.
     */
    public void setMedicine(Medicine target, Medicine editedMedicine) {
        requireAllNonNull(target, editedMedicine);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new MedicineNotFoundException();
        }

        if (!target.isSameMedicine(editedMedicine) && contains(editedMedicine)) {
            throw new DuplicateMedicineException();
        }

        internalList.set(index, editedMedicine);
    }

    /**
     * Removes the equivalent medicine from the list.
     * The medicine must exist in the list.
     */
    public void remove(Medicine toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new MedicineNotFoundException();
        }
    }

    public void setMedicines(UniqueMedicineList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code patients}.
     * {@code patients} must not contain duplicate patients.
     */
    public void setMedicines(List<Medicine> medicines) {
        requireAllNonNull(medicines);
        if (!medicinesAreUnique(medicines)) {
            throw new DuplicateMedicineException();
        }
        internalList.setAll(medicines);
    }

    /**
     * Updates the stock level after dispensing the medicine to the patient
     * @param medicine The medicine to dispense.
     * @param quantityToDispense The amount to dispense to patient, also to deduct from stock.
     */
    public Medicine dispenseMedicine(Medicine medicine, QuantityToDispense quantityToDispense) {
        requireAllNonNull(medicine, quantityToDispense);
        OptionalInt toDispense = IntStream.range(0, internalList.size())
                .filter(i -> internalList.get(i).equals(medicine))
                .findFirst();
        toDispense.ifPresent(i -> {
            Medicine updatedMedicine = internalList.remove(i);
            Medicine clonedMedicine = updatedMedicine.clone();
            try {
                // Try to dispense medicine
                clonedMedicine.dispense(quantityToDispense);
            } catch (InsufficientStockException ise) {
                // Catch exception if insufficient stock, but we want to throw it back up to the calling method.
                throw ise;
            } finally {
                // This executes whether there is an exception thrown or not.
                // This line prevents the Medicine from disappearing from the list if there is insufficient stock.
                internalList.add(i, clonedMedicine);
            }
        });
        return internalList.get(toDispense.getAsInt());
    }

    /**
     * Refill the stock level of the medicine.
     * @param medicine The medicine to refill.
     * @param quantityToRefill The amount to refill.
     */
    public void refillMedicine(Medicine medicine, QuantityToDispense quantityToRefill) {
        requireAllNonNull(medicine, quantityToRefill);
        Optional<Medicine> toDispense = internalList
                .stream()
                .filter(med -> med.equals(medicine))
                .findFirst();
        toDispense.ifPresent(med -> med.refill(quantityToRefill.getValue()));
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Medicine> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Medicine> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueMedicineList // instanceof handles nulls
                && internalList.equals(((UniqueMedicineList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code patients} contains only unique patients.
     */
    private boolean medicinesAreUnique(List<Medicine> medicines) {
        for (int i = 0; i < medicines.size() - 1; i++) {
            for (int j = i + 1; j < medicines.size(); j++) {
                if (medicines.get(i).isSameMedicine(medicines.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
