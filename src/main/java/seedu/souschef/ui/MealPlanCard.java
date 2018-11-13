package seedu.souschef.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.souschef.model.planner.Breakfast;
import seedu.souschef.model.planner.Day;
import seedu.souschef.model.planner.Dinner;
import seedu.souschef.model.planner.Lunch;

/**
 * An UI component that displays information of a {@code Day}.
 */
public class MealPlanCard extends GenericCard<Day> {

    private static final String FXML = "MealPlanListCard.fxml";

    public final Day day;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label breakfast;
    @FXML
    private Label lunch;
    @FXML
    private Label dinner;


    public MealPlanCard(Day day, int displayedIndex) {
        super(FXML);
        this.day = day;
        date.setText(day.getDate().toString());
        id.setText(displayedIndex + ". ");

        if (!day.getMeal(Breakfast.INDEX).isEmpty()) {
            breakfast.setText("Breakfast: " + day.getMeal(Breakfast.INDEX).getRecipe().getName().toString());
        } else {
            breakfast.setText("Breakfast: No recipe");
        }

        if (!day.getMeal(Lunch.INDEX).isEmpty()) {
            lunch.setText("Lunch: " + day.getMeal(Lunch.INDEX).getRecipe().getName().toString());
        } else {
            lunch.setText("Lunch: No recipe");
        }

        if (!day.getMeal(Dinner.INDEX).isEmpty()) {
            dinner.setText("Dinner: " + day.getMeal(Dinner.INDEX).getRecipe().getName().toString());
        } else {
            dinner.setText("Dinner: No recipe");
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MealPlanCard)) {
            return false;
        }

        // state check
        MealPlanCard card = (MealPlanCard) other;
        return id.getText().equals(card.id.getText())
            && day.equals(card.day);
    }
}
