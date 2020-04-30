package guitests.guihandles.accounts;

import guitests.guihandles.NodeHandle;
import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.restaurant.model.account.Account;

/**
 * Provides a handle to an item card in the item list panel.
 */
public class AccountCardHandle extends NodeHandle<Node> {

    private static final String ID_FIELD_ID = "#id";
    private static final String USERNAME_FIELD_ID = "#username";

    private final Label idLabel;
    private final Label usernameLabel;

    public AccountCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        usernameLabel = getChildNode(USERNAME_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getUsername() {
        return usernameLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code item}.
     */
    public boolean equals(Account account) {
        return getUsername().equals(account.getUsername().toString());
    }
}
