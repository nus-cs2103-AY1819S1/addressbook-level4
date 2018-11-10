package seedu.souschef.model.mealplanner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import org.junit.Test;

import seedu.souschef.model.planner.Breakfast;
import seedu.souschef.model.planner.Day;
import seedu.souschef.model.planner.Dinner;
import seedu.souschef.model.planner.Lunch;
import seedu.souschef.model.planner.exceptions.MealRecipeNotFoundException;
import seedu.souschef.model.recipe.CookTime;
import seedu.souschef.model.recipe.Difficulty;
import seedu.souschef.model.recipe.Instruction;
import seedu.souschef.model.recipe.Name;
import seedu.souschef.model.recipe.Recipe;
import seedu.souschef.model.tag.Tag;
import seedu.souschef.testutil.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

public class DayTest {
    Day testDay1 = new Day(LocalDate.parse("2020-01-01"));
    Day testDay2 = new Day(LocalDate.parse("2021-02-02"));
    Day testDay3 = new Day(LocalDate.parse("2020-01-01"));
    Recipe testRecipe1 = new Recipe(new Name("Chicken Burger"),
        new Difficulty(1), new CookTime("30M"), new ArrayList<Instruction>(), new HashSet<Tag>());
    Recipe testRecipe2 = new Recipe(new Name("Roti Prata"),
        new Difficulty(2), new CookTime("30M"), new ArrayList<Instruction>(), new HashSet<Tag>());


    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Day(null));
        Assert.assertThrows(NullPointerException.class, () -> new Day(null, null));
    }

    @Test
    public void getMeal_Slot_empty_throwsMealRecipeNotFoundException() {
        Assert.assertThrows(MealRecipeNotFoundException.class, () -> testDay1.getMeal(Breakfast.INDEX).getRecipe());
        Assert.assertThrows(MealRecipeNotFoundException.class, () -> testDay1.getMeal(Lunch.INDEX).getRecipe());
        Assert.assertThrows(MealRecipeNotFoundException.class, () -> testDay1.getMeal(Dinner.INDEX).getRecipe());
    }

    @Test
    public void getMeal_String_invalid_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> testDay1.getMeal("brkfast"));
        Assert.assertThrows(IllegalArgumentException.class, () -> testDay1.getMeal("lnch"));
        Assert.assertThrows(IllegalArgumentException.class, () -> testDay1.getMeal("dnner"));
    }

    @Test
    public void isEmptyTest() {
        assertTrue(testDay1.isEmpty());

        testDay1.getMeal(Breakfast.INDEX).setRecipe(testRecipe1);
        assertFalse(testDay1.isEmpty());

        testDay1.getMeal(Breakfast.INDEX).setRecipe(null);
        testDay1.getMeal(Lunch.INDEX).setRecipe(testRecipe1);
        assertFalse(testDay1.isEmpty());

        testDay1.getMeal(Lunch.INDEX).setRecipe(null);
        testDay1.getMeal(Dinner.INDEX).setRecipe(testRecipe1);
        assertFalse(testDay1.isEmpty());

        testDay1.getMeal(Breakfast.INDEX).setRecipe(testRecipe1);
        testDay1.getMeal(Lunch.INDEX).setRecipe(testRecipe1);
        assertFalse(testDay1.isEmpty());
    }

    @Test
    public void equalsTest() {
        assertFalse(testDay1.equals(testDay2));
        assertTrue(testDay1.equals(testDay3));

        // Day objects with same date but different meals should still be equal
        testDay1.getMeal(Breakfast.INDEX).setRecipe(testRecipe1);
        testDay3.getMeal(Breakfast.INDEX).setRecipe(testRecipe2);
        assertTrue(testDay1.equals(testDay3));
    }
}
