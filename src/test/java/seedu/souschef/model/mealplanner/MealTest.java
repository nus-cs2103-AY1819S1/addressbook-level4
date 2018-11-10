package seedu.souschef.model.mealplanner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import org.junit.Test;

import seedu.souschef.model.planner.Breakfast;
import seedu.souschef.model.planner.Dinner;
import seedu.souschef.model.planner.Lunch;
import seedu.souschef.model.planner.Meal;
import seedu.souschef.model.planner.exceptions.MealRecipeNotFoundException;
import seedu.souschef.model.recipe.CookTime;
import seedu.souschef.model.recipe.Difficulty;
import seedu.souschef.model.recipe.Instruction;
import seedu.souschef.model.recipe.Name;
import seedu.souschef.model.recipe.Recipe;
import seedu.souschef.model.tag.Tag;
import seedu.souschef.testutil.Assert;

import java.util.ArrayList;
import java.util.HashSet;

public class MealTest {
    Recipe testRecipe1 = new Recipe(new Name("Chicken Burger"),
        new Difficulty(1), new CookTime("30M"), new ArrayList<Instruction>(), new HashSet<Tag>());
    Recipe testRecipe2 = new Recipe(new Name("Roti Prata"),
        new Difficulty(2), new CookTime("30M"), new ArrayList<Instruction>(), new HashSet<Tag>());
    Breakfast testBreakfast1 = new Breakfast();
    Breakfast testBreakfast2 = new Breakfast();
    Lunch testLunch1 = new Lunch();

    @Test
    public void getRecipe_empty_throwsMealRecipeNotFoundException() {
        Assert.assertThrows(MealRecipeNotFoundException.class, () -> testBreakfast1.getRecipe());
    }

    @Test
    public void mealIndexTest() {
        assertTrue(new Breakfast().index == 0);
        assertTrue(new Lunch().index == 1);
        assertTrue(new Dinner().index == 2);
    }

    @Test
    public void mealSlotTest() {
        assertTrue(new Breakfast().slot.equals("breakfast"));
        assertTrue(new Lunch().slot.equals("lunch"));
        assertTrue(new Dinner().slot.equals("dinner"));
    }

    @Test
    public void stringToIntSlot_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> Meal.stringToIntSlot("breakfa"));
        Assert.assertThrows(IllegalArgumentException.class, () -> Meal.stringToIntSlot("lnch"));
        Assert.assertThrows(IllegalArgumentException.class, () -> Meal.stringToIntSlot("dinnner"));
    }

    @Test
    public void isEmptyTest() {
        assertFalse(new Breakfast(testRecipe1).isEmpty());
        assertTrue(new Breakfast().isEmpty());
    }

    @Test
    public void equalsTest() {
        assertTrue(testBreakfast1.equals(testBreakfast2));
        assertFalse(testBreakfast1.equals(testLunch1));

        testBreakfast1.setRecipe(testRecipe1);
        testBreakfast2.setRecipe(testRecipe1);
        testLunch1.setRecipe(testRecipe1);
        assertTrue(testBreakfast1.equals(testBreakfast2));
        assertFalse(testBreakfast1.equals(testLunch1));

        testBreakfast2.setRecipe(testRecipe2);
        assertFalse(testBreakfast1.equals(testBreakfast2));
    }
}
