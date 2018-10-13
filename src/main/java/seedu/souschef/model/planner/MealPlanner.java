package seedu.souschef.model.planner;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.souschef.model.planner.exceptions.DayNotFoundException;
import seedu.souschef.model.planner.exceptions.DuplicateDayException;
import seedu.souschef.model.recipe.Recipe;

/**
 * MealPlanner wraps a calendar containing days with their respective meals and recipes.
 * Calendar map only contains days which have planned meals, otherwise empty days are removed from the calendar.
 */
public class MealPlanner implements Iterable<Day> {
    private final ObservableList<Day> internalList = FXCollections.observableArrayList();

    /**
     * Adds a day.
     * @param toAdd Day to be added.
     */
    public void add(Day toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateDayException("Day " + toAdd.getDate() + " already exists.");
        }
        internalList.add(toAdd);
    }

    /**
     * Removes specified day.
     * @param toRemove Day to be removed.
     */
    public void remove(Day toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new DayNotFoundException("Day " + toRemove.getDate().toString() + " not found.");
        }
    }

    public boolean contains(Day toCheck) {
        return internalList.stream().anyMatch(toCheck::isSame);
    }

    @Override
    public Iterator<Day> iterator() {
        return internalList.iterator();
    }


    public Recipe getMealRecipe(LocalDate date, Meal meal) {
        return getDay(date).getMealRecipe(meal);
    }

    /**
     * set a recipe to a meal slot of a day.
     * @param date Date of day.
     * @param meal Meal slot for recipe to be added to.
     */
    public void setMealRecipe(LocalDate date, Meal meal, Recipe recipe) {
        requireNonNull(date);
        requireNonNull(meal);

        getDay(date).setMealRecipe(meal, recipe);
    }

    /**
     * Returns a day with a specific date. If such a day exists in internalList, that day is returned.
     * Else, a new day with the specified date is instantiated, added to internalList and returned.
     * @param date
     * @return
     */
    public Day getDay(LocalDate date) {
        Day newDay = new Day(date);
        if (contains(newDay)) {
            return internalList.get(internalList.indexOf(newDay));
        } else {
            internalList.add(newDay);
            return newDay;
        }
    }

    /**
     * Clears the internalList.
     */
    public void clearList() {
        internalList.remove(0, internalList.size() - 1);
    }
}
