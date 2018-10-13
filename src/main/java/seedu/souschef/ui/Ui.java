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

    // To be implemented:

    /** void switchToDayListPanel() */

    /** void switchToIngredientListPanel() */

    /** void switchToHealthPlanListPanel() */

}
