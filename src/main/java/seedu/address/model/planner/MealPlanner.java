package seedu.address.model.planner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import seedu.address.model.recipe.Recipe;

import java.time.LocalDate;
import java.util.HashMap;

/**
 * MealPlanner wraps a calendar containing days with their respective meals and recipes.
 */
public class MealPlanner {

    private ObservableMap<LocalDate, Day> calendar;

    public MealPlanner() {
        this.calendar = FXCollections.observableHashMap();
    }

    public MealPlanner(HashMap<LocalDate, Day> map) {
        this.calendar = FXCollections.observableMap(map);
    }

    public ObservableMap<LocalDate, Day> getCalendar() {
        return this.calendar;
    }

    /**
     * Adds to the calendar a recipe to a meal slot on a day of the specified date.
     * Creates a new day if no such day is present in the calendar.
     *
     * @param recipe Recipe to be added.
     * @param date   Date of the day.
     * @param meal   Meal slot which recipe is to be added to.
     */
    public void addMealRecipe(Recipe recipe, LocalDate date, Meal meal) {
        if (!this.calendar.containsKey(date)) {
            Day newDay = new Day(date);
            newDay.setMealRecipe(meal, recipe);
            calendar.put(date, newDay);
        } else {
            calendar.get(date).setMealRecipe(meal, recipe);
        }
    }

    /**
     * Removes a recipe from a specified meal slot of a specified day.
     * Removes the day from the calendar if the day is empty (no meal recipes).
     *
     * @param date Date of the day.
     * @param meal Meal slot to be cleared.
     */
    public void removeMealRecipe(LocalDate date, Meal meal) {
        if (this.calendar.containsKey(date)) {
            this.calendar.get(date).setMealRecipe(meal, null);
        }

        if (calendar.get(date).isEmpty()) {
            calendar.remove(date);
        }
    }
}
