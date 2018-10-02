package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.restaurant.Restaurant;

/**
 * An UI component that displays information of a {@code Restaurant}.
 */
public class RestaurantCard extends UiPart<Region> {

    private static final String FXML = "RestaurantListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Restaurant restaurant;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private FlowPane tags;

    public RestaurantCard(Restaurant restaurant, int displayedIndex) {
        super(FXML);
        this.restaurant = restaurant;
        id.setText(displayedIndex + ". ");
        name.setText(restaurant.getName().fullName);
        phone.setText(restaurant.getPhone().value);
        address.setText(restaurant.getAddress().value);
        restaurant.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RestaurantCard)) {
            return false;
        }

        // state check
        RestaurantCard card = (RestaurantCard) other;
        return id.getText().equals(card.id.getText())
                && restaurant.equals(card.restaurant);
    }
}
