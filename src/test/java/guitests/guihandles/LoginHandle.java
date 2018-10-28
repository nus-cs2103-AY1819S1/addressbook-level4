package guitests.guihandles;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * A handle to the {@code LoginForm} of the application.
 */
public class LoginHandle extends NodeHandle<GridPane> {

    public static final String GRIDPANE_ID = "#loginFormGrid";
    public static final String LOGIN_BUTTON_ID = "#loginButton";
    public static final String USERNAME_FIELD_ID = "#usernameField";
    public static final String PASSWORD_FIELD_ID = "#passwordField";
    public static final String SIGNIN_RESULT_ID = "#signInResult";

    private TextField usernameField;
    private TextField passwordField;
    private Button loginButton;
    private Label signInResult;

    public LoginHandle(GridPane loginGrid) {
        super(loginGrid);

        usernameField = getChildNode(USERNAME_FIELD_ID);
        passwordField = getChildNode(PASSWORD_FIELD_ID);
        loginButton = getChildNode(LOGIN_BUTTON_ID);
        signInResult = getChildNode(SIGNIN_RESULT_ID);
    }

    /**
     * Attempts to login into the system
     * @param username The username to enter
     * @param password The password to enter
     */
    public void attemptLogIn(String username, String password) {
        usernameField.setText(username);
        passwordField.setText(password);
        guiRobot.pauseForHuman();

        guiRobot.clickOn(loginButton);
    }

    /**
     * Gets the output string shown to the user
     * @return The output string
     */
    public String getOutput() {
        return signInResult.getText();
    }
}
