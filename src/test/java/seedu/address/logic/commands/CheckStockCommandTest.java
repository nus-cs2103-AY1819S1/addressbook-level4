package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CheckStockCommand.MEDICINE_LOW_IN_SUPPLY_PREDICATE;
import static seedu.address.logic.commands.CheckStockCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalMedicines.ACETAMINOPHEN;
import static seedu.address.testutil.TypicalMedicines.BACITRACIN;
import static seedu.address.testutil.TypicalMedicines.CALEX;
import static seedu.address.testutil.TypicalMedicines.FABIOR;
import static seedu.address.testutil.TypicalMedicines.GELATO;
import static seedu.address.testutil.TypicalMedicines.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.medicine.MedicineNameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code CheckStockCommand}.
 */
public class CheckStockCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void emptyMedicineList() {
        expectedModel.resetData(new AddressBook());
        assertEquals(Collections.emptyList(), expectedModel.getFilteredMedicineList());
    }

    @Test
    public void noMedicineBelowMinimumStockQuantity() {
        AddressBook ab = new AddressBook();
        ab.addMedicine(ACETAMINOPHEN);
        ab.addMedicine(BACITRACIN);
        ab.addMedicine(CALEX);
        ab.addMedicine(FABIOR);
        ab.addMedicine(GELATO);
        expectedModel = new ModelManager(ab, new UserPrefs());
        expectedModel.updateFilteredMedicineList(MEDICINE_LOW_IN_SUPPLY_PREDICATE);
        assertEquals(Collections.emptyList(), expectedModel.getFilteredMedicineList());
    }

    @Test
    public void someMedicineBelowMinimumStockQuantity() {
        MedicineNameContainsKeywordsPredicate medicineWithInsufficientQuantity =
                new MedicineNameContainsKeywordsPredicate(
                        Arrays.asList("Dalmane", "ECOTRIN"));
        expectedModel.updateFilteredMedicineList(medicineWithInsufficientQuantity);
        CheckStockCommand command = new CheckStockCommand();
        assertCommandSuccess(command, model, commandHistory, MESSAGE_SUCCESS, expectedModel);
        assertEquals(expectedModel.getFilteredMedicineList(), model.getFilteredMedicineList());
    }
}
