package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.ExpenseTracker;
import seedu.address.testutil.TypicalExpenses;

public class XmlSerializableExpenseTrackerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableExpenseTrackerTest");
    private static final Path TYPICAL_EXPENSES_FILE = TEST_DATA_FOLDER.resolve("typicalExpensesExpenseTracker.xml");
    private static final Path INVALID_EXPENSE_FILE = TEST_DATA_FOLDER.resolve("invalidExpenseExpenseTracker.xml");
    private static final Path DUPLICATE_EXPENSE_FILE = TEST_DATA_FOLDER.resolve("duplicateExpenseExpenseTracker.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalExpensesFile_success() throws Exception {
        XmlSerializableExpenseTracker dataFromFile = XmlUtil.getDataFromFile(TYPICAL_EXPENSES_FILE,
                XmlSerializableExpenseTracker.class);
        ExpenseTracker expenseTrackerFromFile = dataFromFile.toModelType();
        ExpenseTracker typicalExpensesExpenseTracker = TypicalExpenses.getTypicalExpenseTracker();
        System.out.println(expenseTrackerFromFile.getMaximumBudget().getNumberOfSecondsToRecurAgain() + " "
            + typicalExpensesExpenseTracker.getMaximumBudget().getNumberOfSecondsToRecurAgain());
        assertEquals(expenseTrackerFromFile, typicalExpensesExpenseTracker);
        assertEquals(expenseTrackerFromFile.getMaximumBudget(), typicalExpensesExpenseTracker.getMaximumBudget());
        assertEquals(expenseTrackerFromFile.getNotificationHandler(),
                typicalExpensesExpenseTracker.getNotificationHandler());
    }

    @Test
    public void toModelType_invalidExpenseFile_throwsIllegalValueException() throws Exception {
        XmlSerializableExpenseTracker dataFromFile = XmlUtil.getDataFromFile(INVALID_EXPENSE_FILE,
                XmlSerializableExpenseTracker.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateExpenses_throwsIllegalValueException() throws Exception {
        XmlSerializableExpenseTracker dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_EXPENSE_FILE,
                XmlSerializableExpenseTracker.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableExpenseTracker.MESSAGE_DUPLICATE_EXPENSE);
        dataFromFile.toModelType();
    }

}
