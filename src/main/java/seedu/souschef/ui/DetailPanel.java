package seedu.souschef.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.souschef.model.recipe.Recipe;

/**
 * An UI component that displays information of a {@code Recipe}.
 */
public class DetailPanel extends UiPart<Region> {

    private static final String FXML = "DetailPanel.fxml";

    public final Recipe recipe;

    @FXML
    private Label name;
    @FXML
    private Label ingredients;
    @FXML
    private Label instructions;

    public DetailPanel(Recipe recipe) {
        super(FXML);
        this.recipe = recipe;
        name.setText(recipe.getName().fullName);
        ingredients.setText("test");
        instructions.setText("test");
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DetailPanel)) {
            return false;
        }

        // state check
        DetailPanel panel = (DetailPanel) other;
        return recipe.equals(panel.recipe);
    }
}
