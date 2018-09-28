package seedu.address.model.planner;

import seedu.address.model.recipe.Recipe;
import java.util.Optional;

/**
 * Represents a meal slot (breakfast, lunch, dinner) of a day.
 * Contains a recipe for the meal, as well as a predefined integer index value.
 */
public enum Meal {

  /**
   * BREAKFAST index = 0;
   * LUNCH index     = 1;
   * DINNER index    = 2;
   */
  BREAKFAST(0), LUNCH(1), DINNER(2);

  // Attributes
  public final int index;
  private Optional<Recipe> recipe;

  Meal(int index) {
    this.index = index;
    this.recipe = Optional.empty();
  }

  public Recipe getRecipe() {
    return this.recipe.get();
  }

  public void setRecipe(Recipe recipe) {
    this.recipe = Optional.ofNullable(recipe);
  }

  /**
   * Checks if a recipe is present or not.
   * @return true if no recipe is present, false if a recipe is present.
   */
  public boolean isEmpty() {
    if (!this.recipe.isPresent()) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Converts a string command token to to its Enum counterpart.
   * @param s Command string token
   * @return Meal
   */
  public Meal stringToMealEnum(String s) {
    if (s.equalsIgnoreCase("breakfast")) {
      return BREAKFAST;
    } else if (s.equalsIgnoreCase("lunch")) {
      return LUNCH;
    } else {
      return DINNER;
    }
  }
}
