package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.expenses.ExpenseType;
import seedu.address.testutil.Assert;

public class XmlAdaptedExpenseTypeTest {

    @Test
    public void toModelType_validArguments_returnsExpenseTypeObject() {
        String number = "1";
        double cost = 0;
        String name = "-";
        XmlAdaptedExpenseType testType = new XmlAdaptedExpenseType(number, name, cost);
        try {
            assertEquals(testType.toModelType(), new ExpenseType(number, name, cost));
        } catch (IllegalValueException e) {
            fail();
        }
    }

    @Test
    public void toModelType_emptyNumber_throwsIllegalValueException() {
        XmlAdaptedExpenseType testType = new XmlAdaptedExpenseType("", "-", 0.0);
        Assert.assertThrows(IllegalValueException.class, ExpenseType.MESSAGE_NUMBER_EMPTY, testType::toModelType);
    }

    @Test
    public void toModelType_emptyName_throwsIllegalValueException() {
        XmlAdaptedExpenseType testType = new XmlAdaptedExpenseType("1", "", 0.0);
        Assert.assertThrows(IllegalValueException.class, ExpenseType.MESSAGE_NAME_EMPTY, testType::toModelType);
    }

    @Test
    public void toModelType_nullNumber_throwsIllegalValueException() {
        XmlAdaptedExpenseType testType = new XmlAdaptedExpenseType(null, "-", 0.0);
        Assert.assertThrows(IllegalValueException.class,
                XmlAdaptedExpenseType.MESSAGE_NUMBER_MISSING, testType::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedExpenseType testType = new XmlAdaptedExpenseType("1", null, 0.0);
        Assert.assertThrows(IllegalValueException.class,
                XmlAdaptedExpenseType.MESSAGE_NAME_MISSING, testType::toModelType);
    }

    @Test
    public void toModelType_nullCost_throwsIllegalValueException() {
        XmlAdaptedExpenseType testType = new XmlAdaptedExpenseType("1", "-", null);
        Assert.assertThrows(IllegalValueException.class,
                XmlAdaptedExpenseType.MESSAGE_COST_MISSING, testType::toModelType);
    }

}
