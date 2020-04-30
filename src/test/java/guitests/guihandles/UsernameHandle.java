package guitests.guihandles;

import javafx.scene.control.Label;

/**
 * A handler for the {@code UsernameDisplay} of the UI
 */
public class UsernameHandle extends NodeHandle<Label> {

    public static final String USERNAME_ID = "#accountStatus";

    public UsernameHandle(Label usernameDisplayNode) {
        super(usernameDisplayNode);
    }

    /**
     * Returns the text in the result display.
     */
    public String getText() {
        return getRootNode().getText();
    }
}
