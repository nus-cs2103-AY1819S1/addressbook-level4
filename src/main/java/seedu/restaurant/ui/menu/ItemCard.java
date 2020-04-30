package seedu.restaurant.ui.menu;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.restaurant.model.menu.Item;
import seedu.restaurant.ui.UiPart;

/**
 * An UI component that displays information of a {@code Item}.
 */
public class ItemCard extends UiPart<Region> {

    private static final String FXML = "ItemListCard.fxml";

    /**
     * The colours of the tags take on Enum values
     */
    private enum TagColourStyle {
        TEAL, RED, YELLOW, BLUE, ORANGE, BROWN, GREEN, PINK, BLACK, GREY;

        private String getColourStyle() {
            return this.toString().toLowerCase();
        }
    }

    private static final TagColourStyle[] TAG_COLOR_STYLES = TagColourStyle.values();

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Item item;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label price;
    @FXML
    private Label percent;
    @FXML
    private FlowPane tags;

    public ItemCard(Item item, int displayedIndex) {
        super(FXML);
        this.item = item;
        id.setText(displayedIndex + ". ");
        name.setText(item.getName().toString());
        price.setText("$" + item.getPrice().toString());
        percent.setText("Price displayed with " + String.format("%.0f", item.getPercent()) + "% discount");
        initTags(item);
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
        if (!(other instanceof ItemCard)) {
            return false;
        }

        // state check
        ItemCard card = (ItemCard) other;
        return id.getText().equals(card.id.getText())
                && item.equals(card.item);
    }
}
