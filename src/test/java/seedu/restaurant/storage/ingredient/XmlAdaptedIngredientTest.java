package seedu.restaurant.storage.ingredient;

import static org.junit.Assert.assertEquals;
import static seedu.restaurant.storage.elements.XmlAdaptedIngredient.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.restaurant.testutil.ingredient.TypicalIngredients.AVOCADO;

import org.junit.Test;

import seedu.restaurant.commons.exceptions.IllegalValueException;
import seedu.restaurant.model.ingredient.IngredientName;
import seedu.restaurant.model.ingredient.IngredientPrice;
import seedu.restaurant.model.ingredient.IngredientUnit;
import seedu.restaurant.model.ingredient.MinimumUnit;
import seedu.restaurant.model.ingredient.NumUnits;
import seedu.restaurant.storage.elements.XmlAdaptedIngredient;

import seedu.restaurant.testutil.Assert;

//@@author rebstan97
public class XmlAdaptedIngredientTest {
    private static final String INVALID_NAME = "Apple+";
    private static final String INVALID_UNIT = "kilogram_";
    private static final String INVALID_PRICE = "2.000";
    private static final String INVALID_MINIMUM = "200.0";
    private static final String INVALID_NUM_UNITS = "123a";

    private static final String VALID_NAME = AVOCADO.getName().toString();
    private static final String VALID_UNIT = AVOCADO.getUnit().toString();
    private static final String VALID_PRICE = AVOCADO.getPrice().toString();
    private static final String VALID_MINIMUM = AVOCADO.getMinimum().toString();
    private static final String VALID_NUM_UNITS = "100";

    @Test
    public void toModelType_validIngredientDetails_returnsIngredient() throws Exception {
        XmlAdaptedIngredient ingredient = new XmlAdaptedIngredient(AVOCADO);
        assertEquals(AVOCADO, ingredient.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedIngredient ingredient = new XmlAdaptedIngredient(INVALID_NAME, VALID_UNIT, VALID_PRICE,
                VALID_MINIMUM, VALID_NUM_UNITS);
        String expectedMessage = IngredientName.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedIngredient ingredient = new XmlAdaptedIngredient(null, VALID_UNIT, VALID_PRICE,
                VALID_MINIMUM, VALID_NUM_UNITS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, IngredientName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_invalidUnit_throwsIllegalValueException() {
        XmlAdaptedIngredient ingredient = new XmlAdaptedIngredient(VALID_NAME, INVALID_UNIT, VALID_PRICE,
                VALID_MINIMUM, VALID_NUM_UNITS);
        String expectedMessage = IngredientUnit.MESSAGE_UNIT_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_nullUnit_throwsIllegalValueException() {
        XmlAdaptedIngredient ingredient = new XmlAdaptedIngredient(VALID_NAME, null, VALID_PRICE,
                VALID_MINIMUM, VALID_NUM_UNITS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, IngredientUnit.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        XmlAdaptedIngredient ingredient = new XmlAdaptedIngredient(VALID_NAME, VALID_UNIT, INVALID_PRICE,
                VALID_MINIMUM, VALID_NUM_UNITS);
        String expectedMessage = IngredientPrice.MESSAGE_PRICE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        XmlAdaptedIngredient ingredient = new XmlAdaptedIngredient(VALID_NAME, VALID_UNIT, null,
                VALID_MINIMUM, VALID_NUM_UNITS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, IngredientPrice.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_invalidMinimum_throwsIllegalValueException() {
        XmlAdaptedIngredient ingredient = new XmlAdaptedIngredient(VALID_NAME, VALID_UNIT, VALID_PRICE,
                INVALID_MINIMUM, VALID_NUM_UNITS);
        String expectedMessage = MinimumUnit.MESSAGE_MINIMUM_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_nullMinimum_throwsIllegalValueException() {
        XmlAdaptedIngredient ingredient = new XmlAdaptedIngredient(VALID_NAME, VALID_UNIT, VALID_PRICE,
                null, VALID_NUM_UNITS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, MinimumUnit.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_invalidNumUnits_throwsIllegalValueException() {
        XmlAdaptedIngredient ingredient = new XmlAdaptedIngredient(VALID_NAME, VALID_UNIT, VALID_PRICE,
                VALID_MINIMUM, INVALID_NUM_UNITS);
        String expectedMessage = NumUnits.MESSAGE_NUM_UNITS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_nullNumUnits_throwsIllegalValueException() {
        XmlAdaptedIngredient ingredient = new XmlAdaptedIngredient(VALID_NAME, VALID_UNIT, VALID_PRICE,
                VALID_MINIMUM, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, NumUnits.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }
}
