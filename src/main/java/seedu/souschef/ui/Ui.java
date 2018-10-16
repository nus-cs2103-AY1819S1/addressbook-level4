package seedu.souschef.ui;

import javafx.stage.Stage;

/**
 * API of UI component
 */
public interface Ui {

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage);

    /** Stops the UI. */
    void stop();

    /** Switches to RecipeListPanel */
    void switchToRecipeListPanel();

    /** Switches to RecipeListPanel */
    void switchToIngredientListPanel();

    /** Switches to MealPlanListPanel */
    void switchToMealPlanListPanel();

    // To be implemented:


    /** void switchToIngredientListPanel() */

    /** void switchToHealthPlanListPanel() */

}
