package guitests.guihandles;

import javafx.stage.Stage;

/**
 * A handle to the {@code HelpWindow} of the application.
 */
public class LoginHandle extends StageHandle {

    public static final String LOGIN_BUTTON_ID = "#loginButton";
    public static final String USERNAME_FIELD_ID = "#usernameField";
    public static final String PASSWORD_FIELD_ID = "#passwordField";

    public LoginHandle(Stage helpWindowStage) {
        super(helpWindowStage);
    }
}
