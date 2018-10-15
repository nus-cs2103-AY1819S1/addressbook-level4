package seedu.address.model.medicine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICINE_NAME_ZYRTEC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MINIMUM_STOCK_QUANTITY_ZYRTEC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_PER_UNIT_ZYRTEC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_ZYRTEC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STOCK_ZYRTEC;
import static seedu.address.testutil.TypicalMedicines.PANADOL;
import static seedu.address.testutil.TypicalMedicines.ZYRTEC;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.MedicineBuilder;

public class MedicineTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameMedicine() {
        // same object -> returns true
        assertTrue(PANADOL.isSameMedicine(PANADOL));

        // null -> returns false
        assertFalse(PANADOL.isSameMedicine(null));

        // different minimumStockQuantity and pricePerUnit and stock -> returns false
        Medicine editedPanadol = new MedicineBuilder(PANADOL)
                .withMinimumStockQuantity(VALID_MINIMUM_STOCK_QUANTITY_ZYRTEC)
                .withPricePerUnit(VALID_PRICE_PER_UNIT_ZYRTEC)
                .withStock(VALID_STOCK_ZYRTEC).build();
        assertFalse(PANADOL.isSameMedicine(editedPanadol));

        // different medicineName -> returns false
        editedPanadol = new MedicineBuilder(PANADOL).withMedicineName(VALID_MEDICINE_NAME_ZYRTEC).build();
        assertFalse(PANADOL.isSameMedicine(editedPanadol));

        // same medicineName, same serialNumber, same pricePerUnit, different attributes -> returns true
        editedPanadol = new MedicineBuilder(PANADOL)
                .withMinimumStockQuantity(VALID_MINIMUM_STOCK_QUANTITY_ZYRTEC)
                .withStock(VALID_STOCK_ZYRTEC).build();
        assertTrue(PANADOL.isSameMedicine(editedPanadol));

        // same medicineName, same minimumStockQuantity, same pricePerUnit, different attributes -> returns false
        editedPanadol = new MedicineBuilder(PANADOL).withSerialNumber(VALID_SERIAL_NUMBER_ZYRTEC)
                .withStock(VALID_STOCK_ZYRTEC).build();
        assertFalse(PANADOL.isSameMedicine(editedPanadol));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Medicine aliceCopy = new MedicineBuilder(PANADOL).build();
        assertTrue(PANADOL.equals(aliceCopy));

        // same object -> returns true
        assertTrue(PANADOL.equals(PANADOL));

        // null -> returns false
        assertFalse(PANADOL.equals(null));

        // different type -> returns false
        assertFalse(PANADOL.equals(5));

        // different medicine -> returns false
        assertFalse(PANADOL.equals(ZYRTEC));

        // different medicineName -> returns false
        Medicine editedPanadol = new MedicineBuilder(PANADOL).withMedicineName(VALID_MEDICINE_NAME_ZYRTEC).build();
        assertFalse(PANADOL.equals(editedPanadol));

        // different minimumStockQuantity -> returns false
        editedPanadol = new MedicineBuilder(PANADOL)
                .withMinimumStockQuantity(VALID_MINIMUM_STOCK_QUANTITY_ZYRTEC).build();
        assertFalse(PANADOL.equals(editedPanadol));

        // different pricePerUnit -> returns false
        editedPanadol = new MedicineBuilder(PANADOL).withPricePerUnit(VALID_PRICE_PER_UNIT_ZYRTEC).build();
        assertFalse(PANADOL.equals(editedPanadol));

        // different serialNumber -> returns false
        editedPanadol = new MedicineBuilder(PANADOL).withSerialNumber(VALID_SERIAL_NUMBER_ZYRTEC).build();
        assertFalse(PANADOL.equals(editedPanadol));

        // different stock -> returns false
        editedPanadol = new MedicineBuilder(PANADOL).withStock(VALID_STOCK_ZYRTEC).build();
        assertFalse(PANADOL.equals(editedPanadol));
    }
}
