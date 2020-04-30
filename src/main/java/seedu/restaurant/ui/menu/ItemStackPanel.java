package seedu.restaurant.ui.menu;

import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.restaurant.model.ingredient.IngredientName;
import seedu.restaurant.model.menu.Item;
import seedu.restaurant.ui.UiPart;

/**
 * The Item Stack Panel of the App.
 */
public class ItemStackPanel extends UiPart<Region> {

    private static final String FXML = "ItemStackPanel.fxml";

    /**
     * The colours of the tags take on Enum values
     */
    private enum TagColourStyle {
        TEAL, RED, YELLOW, BLUE, ORANGE, BROWN, GREEN, PINK, BLACK, GREY;

        private String getColourStyle() {
            return this.toString().toLowerCase();
        }
    }

    private static final ItemStackPanel.TagColourStyle[] TAG_COLOR_STYLES = ItemStackPanel.TagColourStyle.values();

    public final Item item;

    @FXML
    private StackPane stackPane;
    @FXML
    private Label name;
    @FXML
    private Label price;
    @FXML
    private Label percent;
    @FXML
    private Label recipe;
    @FXML
    private Label requiredIngredients;
    @FXML
    private FlowPane tags;

    public ItemStackPanel(Item item) {
        super(FXML);
        this.item = item;
        name.setText(item.getName().toString());
        price.setText("$" + item.getPrice().toString());
        percent.setText("Price displayed with " + String.format("%.0f", item.getPercent()) + "% discount");
        recipe.setText("Recipe: " + item.getRecipe().toString());
        requiredIngredients.setText(getRequiredIngredientsFor(item));
        initTags(item);
    }

    /**
     * Returns the required ingredients for {@code item}
     */
    private String getRequiredIngredientsFor(Item item) {
        Map<IngredientName, Integer> map = item.getRequiredIngredients();
        StringBuilder str = new StringBuilder("Required ingredients:\n");
        for (Map.Entry<IngredientName, Integer> entry : map.entrySet()) {
            str.append("\u2022 " + entry.getValue().toString() + " unit of ");
            str.append(entry.getKey().toString() + "\n");
        }
        return str.toString();
    }

    /**
     * Returns color style for {@code tagName}'s label
     */
    private String getTagColorStyleFor(String tagName) {
        return TAG_COLOR_STYLES[Math.abs(tagName.hashCode()) % TAG_COLOR_STYLES.length].getColourStyle();
    }

    /**
     * Creates tag labels for {@code item}
     */
    private void initTags(Item item) {
        item.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(getTagColorStyleFor(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ItemStackPanel)) {
            return false;
        }

        // state check
        ItemStackPanel itemStackPanel = (ItemStackPanel) other;
        return item.equals(itemStackPanel.item)
                && recipe.getText().equals(itemStackPanel.recipe.getText())
                && requiredIngredients.getText().equals(itemStackPanel.requiredIngredients.getText());
    }
}
