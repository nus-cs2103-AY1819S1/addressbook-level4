package seedu.address.model.medicine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICINE_NAME_PANADOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MINIMUM_STOCK_QUANTITY_PANADOL;
import static seedu.address.testutil.TypicalMedicines.PANADOL;
import static seedu.address.testutil.TypicalMedicines.ACETAMINOPHEN;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.medicine.exceptions.DuplicateMedicineException;
import seedu.address.model.medicine.exceptions.MedicineNotFoundException;
import seedu.address.testutil.MedicineBuilder;

public class UniqueMedicineListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueMedicineList uniqueMedicineList = new UniqueMedicineList();

    @Test
    public void contains_nullMedicine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMedicineList.contains(null);
    }

    @Test
    public void contains_medicineNotInList_returnsFalse() {
        assertFalse(uniqueMedicineList.contains(ACETAMINOPHEN));
    }

    @Test
    public void contains_MedicineInList_returnsTrue() {
        uniqueMedicineList.add(ACETAMINOPHEN);
        assertTrue(uniqueMedicineList.contains(ACETAMINOPHEN));
    }

    @Test
    public void contains_MedicineWithSameIdentityFieldsInList_returnsTrue() {
        uniqueMedicineList.add(ACETAMINOPHEN);
        Medicine editedAcetaminophen = new MedicineBuilder(ACETAMINOPHEN)
                .withMedicineName(VALID_MEDICINE_NAME_PANADOL)
                .withMinimumStockQuantity(VALID_MINIMUM_STOCK_QUANTITY_PANADOL)
                .build();
        assertTrue(uniqueMedicineList.contains(editedAcetaminophen));
    }

    @Test
    public void add_nullMedicine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMedicineList.add(null);
    }

    @Test
    public void add_duplicateMedicine_throwsDuplicateMedicineException() {
        uniqueMedicineList.add(ACETAMINOPHEN);
        thrown.expect(DuplicateMedicineException.class);
        uniqueMedicineList.add(ACETAMINOPHEN);
    }

    @Test
    public void setMedicine_nullTargetMedicine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMedicineList.setMedicine(null, ACETAMINOPHEN);
    }

    @Test
    public void setMedicine_nullEditedMedicine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMedicineList.setMedicine(ACETAMINOPHEN, null);
    }

    @Test
    public void setMedicine_targetMedicineNotInList_throwsMedicineNotFoundException() {
        thrown.expect(MedicineNotFoundException.class);
        uniqueMedicineList.setMedicine(ACETAMINOPHEN, ACETAMINOPHEN);
    }

    @Test
    public void setMedicine_editedMedicineIsSameMedicine_success() {
        uniqueMedicineList.add(ACETAMINOPHEN);
        uniqueMedicineList.setMedicine(ACETAMINOPHEN, ACETAMINOPHEN);
        UniqueMedicineList expectedUniqueMedicineList = new UniqueMedicineList();
        expectedUniqueMedicineList.add(ACETAMINOPHEN);
        assertEquals(expectedUniqueMedicineList, uniqueMedicineList);
    }

    @Test
    public void setMedicine_editedMedicineHasSameIdentity_success() {
        uniqueMedicineList.add(ACETAMINOPHEN);
        Medicine editedAcetaminophen = new MedicineBuilder(ACETAMINOPHEN)
                .withMedicineName(VALID_MEDICINE_NAME_PANADOL)
                .withMinimumStockQuantity(VALID_MINIMUM_STOCK_QUANTITY_PANADOL)
                .build();
        uniqueMedicineList.setMedicine(ACETAMINOPHEN, editedAcetaminophen);
        UniqueMedicineList expectedUniqueMedicineList = new UniqueMedicineList();
        expectedUniqueMedicineList.add(editedAcetaminophen);
        assertEquals(expectedUniqueMedicineList, uniqueMedicineList);
    }

    @Test
    public void setMedicine_editedMedicineHasDifferentIdentity_success() {
        uniqueMedicineList.add(ACETAMINOPHEN);
        uniqueMedicineList.setMedicine(ACETAMINOPHEN, PANADOL);
        UniqueMedicineList expectedUniqueMedicineList = new UniqueMedicineList();
        expectedUniqueMedicineList.add(PANADOL);
        assertEquals(expectedUniqueMedicineList, uniqueMedicineList);
    }

    @Test
    public void setMedicine_editedMedicineHasNonUniqueIdentity_throwsDuplicateMedicineException() {
        uniqueMedicineList.add(ACETAMINOPHEN);
        uniqueMedicineList.add(PANADOL);
        thrown.expect(DuplicateMedicineException.class);
        uniqueMedicineList.setMedicine(ACETAMINOPHEN, PANADOL);
    }

    @Test
    public void remove_nullMedicine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMedicineList.remove(null);
    }

    @Test
    public void remove_MedicineDoesNotExist_throwsMedicineNotFoundException() {
        thrown.expect(MedicineNotFoundException.class);
        uniqueMedicineList.remove(ACETAMINOPHEN);
    }

    @Test
    public void remove_existingMedicine_removesMedicine() {
        uniqueMedicineList.add(ACETAMINOPHEN);
        uniqueMedicineList.remove(ACETAMINOPHEN);
        UniqueMedicineList expectedUniqueMedicineList = new UniqueMedicineList();
        assertEquals(expectedUniqueMedicineList, uniqueMedicineList);
    }

    @Test
    public void setMedicines_nullUniqueMedicineList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMedicineList.setMedicines((UniqueMedicineList) null);
    }

    @Test
    public void setMedicines_uniqueMedicineList_replacesOwnListWithProvidedUniqueMedicineList() {
        uniqueMedicineList.add(ACETAMINOPHEN);
        UniqueMedicineList expectedUniqueMedicineList = new UniqueMedicineList();
        expectedUniqueMedicineList.add(PANADOL);
        uniqueMedicineList.setMedicines(expectedUniqueMedicineList);
        assertEquals(expectedUniqueMedicineList, uniqueMedicineList);
    }

    @Test
    public void setMedicines_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMedicineList.setMedicines((List<Medicine>) null);
    }

    @Test
    public void setMedicines_list_replacesOwnListWithProvidedList() {
        uniqueMedicineList.add(ACETAMINOPHEN);
        List<Medicine> MedicineList = Collections.singletonList(PANADOL);
        uniqueMedicineList.setMedicines(MedicineList);
        UniqueMedicineList expectedUniqueMedicineList = new UniqueMedicineList();
        expectedUniqueMedicineList.add(PANADOL);
        assertEquals(expectedUniqueMedicineList, uniqueMedicineList);
    }

    @Test
    public void setMedicines_listWithDuplicateMedicines_throwsDuplicateMedicineException() {
        List<Medicine> listWithDuplicateMedicines = Arrays.asList(ACETAMINOPHEN, ACETAMINOPHEN);
        thrown.expect(DuplicateMedicineException.class);
        uniqueMedicineList.setMedicines(listWithDuplicateMedicines);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueMedicineList.asUnmodifiableObservableList().remove(0);
    }
}
