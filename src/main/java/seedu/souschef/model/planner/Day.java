package seedu.souschef.model.planner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.souschef.model.UniqueType;
import seedu.souschef.model.recipe.Recipe;

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
        this.meals.add(Meal.BREAKFAST.ordinal(), Meal.BREAKFAST);
        this.meals.add(Meal.LUNCH.ordinal(), Meal.LUNCH);
        this.meals.add(Meal.DINNER.ordinal(), Meal.DINNER);
    }

    public Day(LocalDate date, ArrayList<Meal> meals) {
        this.date = date;
        this.meals = FXCollections.observableArrayList(meals);
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Meal getMeal(Meal meal) {
        return this.meals.get(meal.ordinal());
    }

    public Recipe getMealRecipe(Meal meal) {
        return this.getMeal(meal).getRecipe();
    }

    public void setMealRecipe(Meal meal, Recipe recipe) {
        getMeal(meal).setRecipe(recipe);
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
        return Objects.hash(this.date, this.meals);
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

}
