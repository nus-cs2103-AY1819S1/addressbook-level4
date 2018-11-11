package seedu.souschef.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import seedu.souschef.model.recipe.Recipe;

/**
 * An UI component that displays information of a {@code Recipe}.
 */
public class RecipeCard extends GenericCard<Recipe> {

    private static final String FXML = "RecipeListCard.fxml";

    public final Recipe recipe;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label duration;
    @FXML
    private Label difficulty;
    @FXML
    private FlowPane tags;

    public RecipeCard(Recipe recipe, int displayedIndex) {
        super(FXML);
        this.recipe = recipe;
        id.setText(displayedIndex + ". ");
        name.setText(recipe.getName().fullName);
        duration.setText("Duration: " + recipe.getCookTime().toString());
        difficulty.setText("Difficulty: " + recipe.getDifficulty().toString());
        recipe.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RecipeCard)) {
            return false;
        }

        // state check
        RecipeCard card = (RecipeCard) other;
        return id.getText().equals(card.id.getText())
                && recipe.equals(card.recipe);
    }
}
