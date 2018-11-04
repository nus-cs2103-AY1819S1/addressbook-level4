package seedu.restaurant.model.ingredient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_MINIMUM_BROCCOLI;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_NAME_BROCCOLI;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_PRICE_BROCCOLI;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_UNIT_BROCCOLI;
import static seedu.restaurant.testutil.ingredient.TypicalIngredients.APPLE;
import static seedu.restaurant.testutil.ingredient.TypicalIngredients.BROCCOLI;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.restaurant.testutil.ingredient.IngredientBuilder;

public class IngredientTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameIngredient() {
        // same object -> returns true
        assertTrue(APPLE.isSameIngredient(APPLE));

        // null -> returns false
        assertFalse(APPLE.isSameIngredient(null));

        // name in upper case
        Ingredient editedApple = new IngredientBuilder(APPLE).withName("GRANNY SMITH APPLE").build();
        assertTrue(APPLE.isSameIngredient(editedApple));

        // name in different combination of upper and lower case
        editedApple = new IngredientBuilder(APPLE).withName("GraNNY sMItH aPPle").build();
        assertTrue(APPLE.isSameIngredient(editedApple));

        // name in lower case
        editedApple = new IngredientBuilder(APPLE).withName("granny smith apple").build();
        assertTrue(APPLE.isSameIngredient(editedApple));

        // same name, different unit and price -> returns false
        editedApple = new IngredientBuilder(APPLE).withUnit(VALID_UNIT_BROCCOLI)
                .withPrice(VALID_PRICE_BROCCOLI).build();
        assertTrue(APPLE.isSameIngredient(editedApple));

        // different name -> returns false
        editedApple = new IngredientBuilder(APPLE).withName(VALID_NAME_BROCCOLI).build();
        assertFalse(APPLE.isSameIngredient(editedApple));

        // same name, same unit, different attributes -> returns true
        editedApple = new IngredientBuilder(APPLE).withPrice(VALID_PRICE_BROCCOLI).withMinimum(VALID_MINIMUM_BROCCOLI)
                .build();
        assertTrue(APPLE.isSameIngredient(editedApple));

        // same name, same price, different attributes -> returns true
        editedApple = new IngredientBuilder(APPLE).withUnit(VALID_UNIT_BROCCOLI).withMinimum(VALID_MINIMUM_BROCCOLI)
                .build();
        assertTrue(APPLE.isSameIngredient(editedApple));

        // same name, same unit, same price, different attributes -> returns true
        editedApple = new IngredientBuilder(APPLE).withMinimum(VALID_MINIMUM_BROCCOLI).build();
        assertTrue(APPLE.isSameIngredient(editedApple));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Ingredient aliceCopy = new IngredientBuilder(APPLE).build();
        assertTrue(APPLE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(APPLE.equals(APPLE));

        // null -> returns false
        assertFalse(APPLE.equals(null));

        // different type -> returns false
        assertFalse(APPLE.equals(5));

        // different ingredient -> returns false
        assertFalse(APPLE.equals(BROCCOLI));

        // different name -> returns false
        Ingredient editedApple = new IngredientBuilder(APPLE).withName(VALID_NAME_BROCCOLI).build();
        assertFalse(APPLE.equals(editedApple));

        // different unit -> returns false
        editedApple = new IngredientBuilder(APPLE).withUnit(VALID_UNIT_BROCCOLI).build();
        assertFalse(APPLE.equals(editedApple));

        // different price -> returns false
        editedApple = new IngredientBuilder(APPLE).withPrice(VALID_PRICE_BROCCOLI).build();
        assertFalse(APPLE.equals(editedApple));

        // different minimum -> returns false
        editedApple = new IngredientBuilder(APPLE).withMinimum(VALID_MINIMUM_BROCCOLI).build();
        assertFalse(APPLE.equals(editedApple));

    }
}
