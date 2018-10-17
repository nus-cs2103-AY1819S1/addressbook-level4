package guitests.guihandles;

import java.net.URL;

import guitests.GuiRobot;
import javafx.stage.Stage;

/**
 * A handle to the {@code HelpWindow} of the application.
 */
public class LoginHandle extends StageHandle {

    public static final String LOGIN_BUTTON_ID = "#loginButton";

    public LoginHandle(Stage helpWindowStage) {
        super(helpWindowStage);
    }
}
