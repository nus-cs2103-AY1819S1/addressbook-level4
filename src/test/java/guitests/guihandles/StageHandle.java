package guitests.guihandles;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertFalse;

import java.util.Optional;
import java.util.logging.Logger;

import guitests.GuiRobot;
import guitests.guihandles.exceptions.NodeNotFoundException;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Provides access to a stage in a JavaFx application for GUI testing purposes.
 */
public abstract class StageHandle {
    protected final GuiRobot guiRobot = new GuiRobot();
    private final Logger logger = LogsCenter.getLogger(getClass());

    private final Stage stage;

    public StageHandle(Stage stage) {
        this.stage = requireNonNull(stage);
    }

    /**
     * Closes {@code stage}.
     */
    public void close() {
        guiRobot.interact(stage::close);
        assertFalse(stage.isShowing());
    }

    /**
     * Focuses on this {@code stage}.
     */
    public void focus() {
        String windowTitle = stage.getTitle();
        logger.info("Focusing on" + windowTitle);
        guiRobot.interact(stage::requestFocus);
        logger.info("Finishing focus on" + windowTitle);
    }

    /**
     * Returns true if currently focusing on this stage.
     */
    public boolean isFocused() {
        return stage.isFocused();
    }

    /**
     * Retrieves the {@code query} node owned by the {@code stage}.
     *
     * @param query name of the CSS selector for the node to retrieve.
     * @throws NodeNotFoundException if no such node exists.
     */
    protected <T extends Node> T getChildNode(String query) {
        Optional<T> node = guiRobot.from(stage.getScene().getRoot()).lookup(query).tryQuery();
        return node.orElseThrow(NodeNotFoundException::new);
    }

    /**
     * Attempts to log in to the application, allowing tests to start after logging in have been successful.
     */
    protected void attemptLogIn() {
        Optional<? extends Node> loginNode = guiRobot.from(stage.getScene().getRoot())
                                                    .lookup(LoginHandle.LOGIN_BUTTON_ID).tryQuery();
        Optional<? extends Node> usernameNode = guiRobot.from(stage.getScene().getRoot())
                                                    .lookup(LoginHandle.USERNAME_FIELD_ID).tryQuery();
        Optional<? extends Node> passwordNode = guiRobot.from(stage.getScene().getRoot())
                                                    .lookup(LoginHandle.PASSWORD_FIELD_ID).tryQuery();
        TextField usernameField = (TextField) usernameNode.orElseThrow(NodeNotFoundException::new);
        usernameField.setText("Admin");
        PasswordField passwordField = (PasswordField) passwordNode.orElseThrow(NodeNotFoundException::new);
        passwordField.setText("Pa55w0rd");
        Button loginButton = (Button) loginNode.orElseThrow(NodeNotFoundException::new);
        loginButton.fire();
    }
}
