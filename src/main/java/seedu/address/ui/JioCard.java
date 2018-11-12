package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.jio.Jio;
/**
 * An UI component that displays information of a {@code Restaurant}.
 */
public class JioCard extends UiPart<Region> {

    private static final String FXML = "JioListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Jio jio;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label week;
    @FXML
    private Label day;
    @FXML
    private Label time;
    @FXML
    private Label address;
    @FXML
    private FlowPane tags;

    public JioCard(Jio jio, int displayedIndex) {
        super(FXML);
        this.jio = jio;
        id.setText(displayedIndex + ". ");
        name.setText(jio.getName().fullName);
        week.setText("Week " + jio.getDate().getWeek().toString());
        day.setText(jio.getDate().getDay().toString());
        time.setText(jio.getDate().getTime().toString());
        address.setText(jio.getAddress().value);
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
        JioCard card = (JioCard) other;
        return jio.equals(card.jio);
    }
}


