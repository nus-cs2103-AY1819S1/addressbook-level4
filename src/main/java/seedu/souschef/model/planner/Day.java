package seedu.souschef.model.planner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.souschef.model.UniqueType;

/**
 * Day encapsulates 1 date and 3 meals (breakfast, lunch and dinner).
 */
public class Day extends UniqueType {

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
        this.meals.add(Breakfast.INDEX, new Breakfast());
        this.meals.add(Lunch.INDEX, new Lunch());
        this.meals.add(Dinner.INDEX, new Dinner());
    }

    public Day(LocalDate date, ArrayList<Meal> meals) {
        this.date = date;
        this.meals = FXCollections.observableArrayList(meals);
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Meal getMeal(String slot) {
        int targetSlot = Meal.stringToIntSlot(slot);
        return meals.get(targetSlot);
    }

    public Meal getMeal(int index) {
        return meals.get(index);
    }

    public ObservableList<Meal> getMeals() {
        return meals;
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

    @Override
    public int hashCode() {
        return Objects.hash(date, meals);
    }

    @Override
    public boolean isSame(UniqueType uniqueType) {
        return this.getDate().equals(((Day) uniqueType).getDate());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Day)) {
            return false;
        }

        Day otherDay = (Day) other;
        return otherDay.getDate().equals(getDate());
    }

    @Override
    public String toString() {
        return date.toString();
    }
}
