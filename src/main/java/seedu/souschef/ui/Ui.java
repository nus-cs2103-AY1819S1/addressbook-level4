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

    /** Switches to CrossRecipeListPanel */
    void switchToCrossRecipeListPanel();

    /** Switches to MealPlanListPanel */
    void switchToMealPlanListPanel();

    /** Switches to FavouritesListPanel */
    void switchToFavouritesListPanel();

    /** void switchToIngredientListPanel() */
    void switchToHealthPlanListPanel();

    /**method to show the meal plan list to add*/
    void showMealPlanListPanel();

    void hideBrowserSidePanel();


    void showHealthPlanDetails(int index);
}
