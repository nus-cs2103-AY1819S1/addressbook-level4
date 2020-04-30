package guitests.guihandles.accounts;

import guitests.guihandles.NodeHandle;
import javafx.scene.control.Label;
import seedu.restaurant.ui.account.UsernameDisplay;

/**
 * A handler for the {@link UsernameDisplay} of the UI
 */
public class UsernameDisplayHandle extends NodeHandle<Label> {

    public static final String USERNAME_DISPLAY_ID = "#usernameDisplay";

    public UsernameDisplayHandle(Label usernameDisplayNode) {
        super(usernameDisplayNode);
    }

    /**
     * Returns the text in the result display.
     */
    public String getText() {
        return getRootNode().getText();
    }
}
