package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.card.Card;

/**
 * An UI component that displays information of a {@code Card}.
 */
public class CardView extends UiPart<Region> {

    private static final String FXML = "CardView.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Card card;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label question;
    @FXML
    private Label answer;
    @FXML
    private FlowPane tags;

    public CardView(Card card, int displayedIndex) {
        super(FXML);
        this.card = card;
        id.setText(displayedIndex + ". ");
        question.setText(card.getQuestion().value);
        answer.setText(card.getAnswer().value);
        card.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CardView)) {
            return false;
        }

        // state check
        CardView view = (CardView) other;
        return id.getText().equals(view.id.getText())
                && card.equals(view.card);
    }
}
