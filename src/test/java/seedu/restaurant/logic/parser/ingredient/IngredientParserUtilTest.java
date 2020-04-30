package seedu.restaurant.logic.parser.ingredient;

import static org.junit.Assert.assertEquals;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.model.ingredient.IngredientName;
import seedu.restaurant.model.ingredient.IngredientPrice;
import seedu.restaurant.model.ingredient.IngredientUnit;
import seedu.restaurant.model.ingredient.MinimumUnit;
import seedu.restaurant.testutil.Assert;

//@@author rebstan97
public class IngredientParserUtilTest {

    private static final String INVALID_INGREDIENT_NAME = "Chicken Drums+ick";
    private static final String INVALID_INGREDIENT_UNIT = "+kilograms";
    private static final String INVALID_INGREDIENT_PRICE = "9.9099";
    private static final String INVALID_INGREDIENT_MINIMUM = "10.0";

    private static final String VALID_INGREDIENT_NAME = "Chicken Drumstick";
    private static final String VALID_INGREDIENT_UNIT = "5-kilogram bag";
    private static final String VALID_INGREDIENT_PRICE = "9.90";
    private static final String VALID_INGREDIENT_MINIMUM_STRING = "10";
    private static final int VALID_INGREDIENT_MINIMUM = 10;

    private static final String WHITESPACE = " \t\r\n";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIngredientName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> IngredientParserUtil.parseIngredientName(null));
    }

    @Test
    public void parseIngredientName_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> IngredientParserUtil
                .parseIngredientName(INVALID_INGREDIENT_NAME));
    }

    @Test
    public void parseIngredientName_validValueWithoutWhitespace_returnsName() throws Exception {
        IngredientName expectedName = new IngredientName(VALID_INGREDIENT_NAME);
        assertEquals(expectedName, IngredientParserUtil.parseIngredientName(VALID_INGREDIENT_NAME));
    }

    @Test
    public void parseIngredientName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_INGREDIENT_NAME + WHITESPACE;
        IngredientName expectedName = new IngredientName(VALID_INGREDIENT_NAME);
        assertEquals(expectedName, IngredientParserUtil.parseIngredientName(nameWithWhitespace));
    }

    @Test
    public void parseIngredientUnit_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> IngredientParserUtil.parseIngredientName(null));
    }

    @Test
    public void parseIngredientUnit_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> IngredientParserUtil
                .parseIngredientUnit(INVALID_INGREDIENT_UNIT));
    }

    @Test
    public void parseIngredientUnit_validValueWithoutWhitespace_returnsName() throws Exception {
        IngredientUnit expectedUnit = new IngredientUnit(VALID_INGREDIENT_UNIT);
        assertEquals(expectedUnit, IngredientParserUtil.parseIngredientUnit(VALID_INGREDIENT_UNIT));
    }

    @Test
    public void parseIngredientUnit_validValueWithWhitespace_returnsTrimmedUnit() throws Exception {
        String unitWithWhitespace = WHITESPACE + VALID_INGREDIENT_UNIT + WHITESPACE;
        IngredientUnit expectedUnit = new IngredientUnit(VALID_INGREDIENT_UNIT);
        assertEquals(expectedUnit, IngredientParserUtil.parseIngredientUnit(unitWithWhitespace));
    }

    @Test
    public void parseIngredientPrice_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> IngredientParserUtil.parseIngredientPrice(null));
    }

    @Test
    public void parseIngredientPrice_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> IngredientParserUtil
                .parseIngredientPrice(INVALID_INGREDIENT_PRICE));
    }

    @Test
    public void parseIngredientPrice_validValueWithoutWhitespace_returnsPrice() throws Exception {
        IngredientPrice expectedPrice = new IngredientPrice(VALID_INGREDIENT_PRICE);
        assertEquals(expectedPrice, IngredientParserUtil.parseIngredientPrice(VALID_INGREDIENT_PRICE));
    }

    @Test
    public void parseIngredientPrice_validValueWithWhitespace_returnsTrimmedPrice() throws Exception {
        String priceWithWhitespace = WHITESPACE + VALID_INGREDIENT_PRICE + WHITESPACE;
        IngredientPrice expectedPrice = new IngredientPrice(VALID_INGREDIENT_PRICE);
        assertEquals(expectedPrice, IngredientParserUtil.parseIngredientPrice(priceWithWhitespace));
    }

    @Test
    public void parseMinimumUnit_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> IngredientParserUtil.parseMinimumUnit(null));
    }

    @Test
    public void parseMinimumUnit_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> IngredientParserUtil
                .parseMinimumUnit(INVALID_INGREDIENT_MINIMUM));
    }

    @Test
    public void parseMinimumUnit_validValueWithoutWhitespace_returnsMin() throws Exception {
        MinimumUnit expectedMin = new MinimumUnit(VALID_INGREDIENT_MINIMUM);
        assertEquals(expectedMin, IngredientParserUtil.parseMinimumUnit(VALID_INGREDIENT_MINIMUM_STRING));
    }

    @Test
    public void parseMinimumUnit_validValueWithWhitespace_returnsMin() throws Exception {
        String minWithWhitespace = WHITESPACE + VALID_INGREDIENT_MINIMUM_STRING + WHITESPACE;
        MinimumUnit expectedMin = new MinimumUnit(VALID_INGREDIENT_MINIMUM);
        assertEquals(expectedMin, IngredientParserUtil.parseMinimumUnit(minWithWhitespace));
    }

    @Test
    public void parseIndexOrName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> IngredientParserUtil
                .parseIndexOrIngredientName(null));
    }

    @Test
    public void parseIndexOrName_invalidIndexValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> IngredientParserUtil
                .parseIndexOrIngredientName(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndexOrName_invalidNameValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> IngredientParserUtil
                .parseIndexOrIngredientName("Chicken Thigh+"));
    }

    @Test
    public void parseIndexOrName_validIndexValue_returnsIndex() throws Exception {
        assertEquals(INDEX_FIRST, IngredientParserUtil.parseIndexOrIngredientName("1"));
        assertEquals(INDEX_FIRST, IngredientParserUtil.parseIndexOrIngredientName("    1    "));
    }

    @Test
    public void parseIndexOrName_validValue_returnsTrimmedMin() throws Exception {
        String validName = VALID_INGREDIENT_NAME;
        IngredientName expectedName = new IngredientName(VALID_INGREDIENT_NAME);
        assertEquals(expectedName, IngredientParserUtil.parseIndexOrIngredientName(validName));
        String nameWithWhitespace = WHITESPACE + VALID_INGREDIENT_NAME + WHITESPACE;
        expectedName = new IngredientName(VALID_INGREDIENT_NAME);
        assertEquals(expectedName, IngredientParserUtil.parseIndexOrIngredientName(nameWithWhitespace));
    }
}
