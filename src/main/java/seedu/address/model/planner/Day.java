package seedu.address.model.planner;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.recipe.Recipe;

/**
 * Day encapsulates 1 date and 3 meals (breakfast, lunch and dinner).
 */
public class Day {

    // Attributes
    private LocalDate date;
    private ObservableList<Meal> meals;

    /**
     * Sets the date and initialises the ObservableList to have 3 indexes for 3 meals.
     *
     * @param date Date of the day.
     */
    public Day(LocalDate date) {
        this.date = date;
        this.meals = FXCollections.observableArrayList();
        this.meals.add(Meal.BREAKFAST.index, Meal.BREAKFAST);
        this.meals.add(Meal.LUNCH.index, Meal.LUNCH);
        this.meals.add(Meal.DINNER.index, Meal.DINNER);
    }

    public Day(LocalDate date, ArrayList<Meal> meals) {
        this.date = date;
        this.meals = FXCollections.observableArrayList(meals);
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Meal getMeal(Meal meal) {
        return this.meals.get(meal.index);
    }

    public Recipe getMealRecipe(Meal meal) {
        return this.getMeal(meal).getRecipe();
    }

    public void setMealRecipe(Meal meal, Recipe recipe) {
        this.getMeal(meal).setRecipe(recipe);
    }

    /**
     * Checks if the day has any meal recipes.
     *
     * @return true if the day has at least one meal recipe, false otherwise.
     */
    public boolean isEmpty() {
        for (Meal m : meals) {
            if (!m.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
