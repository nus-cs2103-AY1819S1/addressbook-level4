package seedu.souschef.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.souschef.model.planner.Day;

/**
 * An UI component that displays information of a {@code Recipe}.
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

        if (!day.getMeal("breakfast").isEmpty()) {
            breakfast.setText("Breakfast: " + day.getMeal("breakfast").getRecipe().getName().toString());
        } else {
            breakfast.setText("Breakfast: No recipe");
        }

        if (!day.getMeal("lunch").isEmpty()) {
            lunch.setText("Lunch: " + day.getMeal("lunch").getRecipe().getName().toString());
        } else {
            lunch.setText("Lunch: No recipe");
        }

        if (!day.getMeal("dinner").isEmpty()) {
            dinner.setText("Dinner: " + day.getMeal("dinner").getRecipe().getName().toString());
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
