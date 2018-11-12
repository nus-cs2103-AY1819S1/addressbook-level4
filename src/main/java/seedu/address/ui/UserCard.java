package seedu.address.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.user.User;

/**
 * An UI component that displays information of a {@code User}.
 */
public class UserCard extends UiPart<Region> {
    private static final String FXML = "UserListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final User user;

    @javafx.fxml.FXML
    private HBox cardPane;
    @javafx.fxml.FXML
    private Label id;
    @javafx.fxml.FXML
    private Label username;
    @javafx.fxml.FXML
    private Label name;
    @javafx.fxml.FXML
    private Label email;
    @javafx.fxml.FXML
    private Label phone;

    public UserCard(User user, int displayedIndex) {
        super(FXML);
        this.user = user;
        id.setText(displayedIndex + ". ");
        username.setText(user.getUsername().toString());
        name.setText(user.getName().toString());
        email.setText(user.getEmail().toString());
        phone.setText(user.getPhone().toString());
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UserCard)) {
            return false;
        }

        // state check
        UserCard card = (UserCard) other;
        return user.equals(card.user);
    }
}
