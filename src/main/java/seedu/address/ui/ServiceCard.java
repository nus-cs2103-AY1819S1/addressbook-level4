package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.contact.Contact;

/**
 * An UI component that displays information of a service provided by a {@code Vendor}.
 */
public class ServiceCard extends UiPart<Region> {

    private static final String FXML = "ServiceListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Contact contact;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label price;

    public ServiceCard(Contact contact, String serviceType) {
        super(FXML);
        this.contact = contact;
        id.setText("#" + contact.getId() + ". ");
        name.setText(contact.getName().fullName);
        price.setText("Cost: $" + contact.getServices().get(serviceType).getCost().toPlainString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ServiceCard)) {
            return false;
        }

        // state check
        ServiceCard card = (ServiceCard) other;
        return id.getText().equals(card.id.getText())
                && contact.equals(card.contact);
    }
}
