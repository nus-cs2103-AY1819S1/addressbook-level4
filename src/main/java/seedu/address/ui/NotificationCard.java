package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import seedu.address.model.notification.Notification;

/**
 * An UI component that displays information of a {@code Expense}.
 */
public class NotificationCard extends UiPart<Region> {

    private static final String FXML = "NotificationCard.fxml";
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/CS2103-AY1819S1-T12-1/main/issues">The issue on ExpenseTracker</a>
     */

    public final Notification notification;
    @FXML
    private HBox notificationPane;
    @FXML
    private Label header;
    @FXML
    private Label body;

    public NotificationCard(Notification notification) {
        super(FXML);
        this.notification = notification;
        header.setText(notification.getHeader());
        body.setText(notification.getBody());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NotificationCard)) {
            return false;
        }

        // state check
        NotificationCard card = (NotificationCard) other;
        return header.getText().equals(card.header.getText())
                && body.getText().equals(card.body.getText());
    }

}
