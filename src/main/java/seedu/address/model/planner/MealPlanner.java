package seedu.address.model.planner;

import java.time.LocalDate;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import seedu.address.model.recipe.Recipe;

/**
 * MealPlanner wraps a calendar containing days with their respective meals and recipes.
 * Calendar map only contains days which have planned meals, otherwise empty days are removed from the calendar.
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

    public void clearAll() { this.calendar = FXCollections.observableHashMap(); }

    public Day getDay(LocalDate date) {
        return this.calendar.get(date);
    }

    public Recipe getMealRecipe(LocalDate date, Meal meal) {
        return this.getDay(date).getMealRecipe(meal);
    }

    /**
     * Adds to the calendar a recipe to a meal slot on a day of the specified date.
     * If a recipe is already present at the meal slot, existing recipe is overwritten.
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

    /**
     * Clears all the recipes in a day by removing the day from the calendar.
     * @param date Date of day to be removed.
     */
    public void clearDay(LocalDate date) {
        if (!this.calendar.get(date).isEmpty()) {
            this.calendar.remove(date);
        }
    }

}
